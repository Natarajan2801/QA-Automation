package WebScrapper;

import java.io.IOException;
import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import java.nio.file.Files;
import java.nio.file.Paths;

import com.fasterxml.jackson.databind.ObjectMapper;

import io.github.bonigarcia.wdm.WebDriverManager;

import java.io.File;

public class WebScrapper {

	private WebDriver driver;

	@BeforeClass
	public void setUp() throws Exception {
		WebDriverManager.chromedriver().setup();
		driver = new ChromeDriver();

		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(40));
	}

	@Test
	public void WebscrapeTeam() throws Exception {
		driver.get("https://www.scrapethissite.com/pages/");
		driver.findElement(By.xpath("//a[text()='Hockey Teams: Forms, Searching and Pagination']")).click();
		Thread.sleep(2000);
		List<HashMap> datafull = new ArrayList<>();
		for (int j = 0; j < 4; j++) {
			List<WebElement> TeamName = driver.findElements(By.xpath("//tr[@class='team']/td[@class='name']"));
			List<WebElement> year = driver.findElements(By.xpath("//tr[@class='team']/td[@class='year']"));
			List<WebElement> win = driver.findElements(
					By.xpath("//tr[@class='team']/td[@class='pct text-danger' or @class='pct text-success']"));
			for (int i = 0; i < 5; i++) {
				HashMap<String, Object> data = new HashMap<>();
				data.put("Team Name", TeamName.get(i).getText());
				data.put("year", year.get(i).getText());
				data.put("win%", win.get(i).getText());
				data.put("EpochTime", Instant.now().getEpochSecond());
				datafull.add(data);
			}
			driver.findElement(By.xpath("//a[@aria-label='Next']")).click();
			Thread.sleep(3000);
		}
		String filePath =  System.getProperty("user.dir") + "\\src\\test\\resources\\hockey-team-data.json.json";
		GenerateJsonFile(filePath,datafull);
		checkJsonFile(filePath);

	}

	public static void checkJsonFile(String filePath) throws IOException {
		File file = new File(filePath);

		// Check if the file exists
		if (!file.exists()) {
			System.out.println("The file does not exist.");
			return;
		}
		// Check if the file is empty
		String content = new String(Files.readAllBytes(Paths.get(filePath)));

		if (content.trim().isEmpty()) {
			System.out.println("The file is empty.");
		} else {
			System.out.println("The file is present and is not empty.");
		}
	}
	
	public static void GenerateJsonFile(String filePath,List<HashMap> datafull) throws IOException {
		try {
			ObjectMapper objectMapper = new ObjectMapper();
			objectMapper.writeValue(new File(filePath), datafull);
			System.out.println("JSON file generated successfully.");
		} catch (IOException e) {
			System.out.println("An error occurred while writing to the file.");
			e.printStackTrace();
		}
	}
	@Test
	public void WebscraperFlim() throws Exception {
		driver.navigate().to("https://www.scrapethissite.com/pages/");
		driver.findElement(By.xpath("//a[text()='Oscar Winning Films: AJAX and Javascript']")).click();
		Thread.sleep(2000);
		List<HashMap> datafull = new ArrayList<>();
		String[] years = { "2015", "2014", "2013", "2012", "2011", "2010" };
		for (int j = 0; j < years.length; j++) {
			String xpath = String.format("//a[@id='%s']", years[j]);
			driver.findElement(By.xpath(xpath)).click();
			Thread.sleep(4000);
			List<WebElement> title = driver.findElements(By.xpath("//td[@class='film-title']"));
			List<WebElement> nomination = driver.findElements(By.xpath("//td[@class='film-nominations']"));
			List<WebElement> awards = driver.findElements(By.xpath("//td[@class='film-awards']"));
			for (int i = 0; i < title.size(); i++) {
				HashMap<String, Object> data = new HashMap<>();
				data.put("title", title.get(i).getText());
				data.put("nomination", nomination.get(i).getText());
				data.put("awards", awards.get(i).getText());
				data.put("EpochTime",Instant.now().getEpochSecond());
				data.put("isWinner", i == 0);
				data.put("year",years[j]);
				datafull.add(data);
			}
			
		}
		
		String filePath =  System.getProperty("user.dir") + "\\src\\test\\resources\\oscar-winner-data.json";
		GenerateJsonFile(filePath,datafull);
		checkJsonFile(filePath);

	}

	@AfterClass
	public void tearDown() {
		if (driver != null) {
			driver.quit();
		}
	}
}
