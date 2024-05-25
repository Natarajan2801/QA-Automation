package AutomateGoogleForm;

import java.time.Duration;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import io.github.bonigarcia.wdm.WebDriverManager;

public class GoogleForm {
	private WebDriver driver;

	@BeforeClass
	public void setUp() throws Exception {
		WebDriverManager.chromedriver().setup();
		driver = new ChromeDriver();
		driver.get("https://forms.gle/wjPkzeSEk1CM7KgGA");
		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(40));
	}

	@Test
	public void googleForm() throws Exception {

		Thread.sleep(2000);
		WebElement nameInput = driver.findElement(By.xpath("//span[text()='Name']/../../../..//input"));
		nameInput.click();
		nameInput.sendKeys("Natarajan M");

		String PracticeData = "I want to be the best QA Engineer!" + getCurrentEpoch();
		WebElement PracticeInput = driver.findElement(By.xpath("//textarea"));
		PracticeInput.click();
		PracticeInput.sendKeys(PracticeData);

		driver.findElement(By.xpath("(//div[@class='rseUEf nQOrEb'])[2]/..")).click();

		List<String> SkillsList = new ArrayList<>(List.of("Java", "Selenium", "TestNG"));

		for (int i = 0; i < SkillsList.size(); i++) {
			String xpath = "//span[text()='" + SkillsList.get(i)
					+ "']/../../../div[@class='uVccjd aiSeRd FXLARc wGQFbe BJHAP oLlshd']";
			driver.findElement(By.xpath(xpath)).click();

		}
		driver.findElement(By.xpath("//div[@tabindex='0' and @jsname='wQNmvb']")).click();
		Thread.sleep(1000);
		driver.findElement(By.xpath("(//span[text()='Mr'])[2]")).click();
		ArrayList<String> DateTimeList = getCurTimeDate();
		WebElement DateInput = driver.findElement(By.xpath("//input[@type='date']"));
		DateInput.click();
		DateInput.sendKeys(DateTimeList.get(0));
		String[] HrMM = DateTimeList.get(1).split("-");
		WebElement HoursInput = driver.findElement(By.xpath("//input[@aria-label='Hour']"));
		HoursInput.click();
		Thread.sleep(1000);

		HoursInput.sendKeys(HrMM[0]);

		WebElement MinutesInput = driver.findElement(By.xpath("//input[@aria-label='Minute']"));
		MinutesInput.click();
		MinutesInput.sendKeys(HrMM[1]);
		driver.findElement(By.xpath("//span[text()='Submit']")).click();
		DateInput.sendKeys(DateTimeList.get(0));
		Thread.sleep(2000);
		driver.findElement(By.xpath("//span[text()='Submit']")).click();

		String outPutresponse = driver.findElement(By.className("vHW8K")).getText();
		System.out.println(outPutresponse);
	}

	public String getCurrentEpoch() {
		long epochMilli = System.currentTimeMillis();

		// Convert milliseconds to seconds
		long epochSeconds = epochMilli / 1000;

		// Format the results as strings
		// String epochMilliString = "Current Epoch Time in milliseconds: " +
		// epochMilli;
		String epochSecondsString = "" + epochSeconds;
		return epochSecondsString;

	}

	public ArrayList<String> getCurTimeDate() {
		// Define the IST time zone
		ZoneId istZoneId = ZoneId.of("Asia/Kolkata");

		// Get the current date and time in IST
		ZonedDateTime now = ZonedDateTime.now(istZoneId);

		// Subtract 7 days from the current date
		LocalDate sevenDaysAgo = now.toLocalDate().minusDays(7);

		// Define the date format as ddMMyyyy
		DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("ddMMyyyy");
		String formattedDate = sevenDaysAgo.format(dateFormatter);

		// Define the time format as HHmm
		DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH-mm");
		String formattedTime = now.format(timeFormatter);

		ArrayList<String> DateTimeList = new ArrayList<>(List.of(formattedDate, formattedTime));
		return DateTimeList;

	}

	@AfterClass
	public void tearDown() {
		if (driver != null) {
			driver.quit();
		}
	}

}
