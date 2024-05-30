package FlipKart;

import java.time.Duration;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import io.github.bonigarcia.wdm.WebDriverManager;

public class Flipkart {
	private WebDriver driver;

	@BeforeClass
	public void setUp() throws Exception {
		WebDriverManager.chromedriver().setup();
		driver = new ChromeDriver();
		driver.get("https://www.flipkart.com/");
		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(40));
	}

	@Test
	public void flipkart() throws Exception {

		WebElement inputbox = driver.findElement(By.xpath("//input[@title='Search for Products, Brands and More']"));
		EnterDataInput(inputbox,"Washing Machine");
		List<WebElement> ratingData = driver.findElements(By.cssSelector(".XQDdHH"));
		int count = 0;
		for (WebElement e : ratingData) {
			double c = Double.parseDouble(e.getText());
			if (c >= 4.0) {
				count++;
			}
		}

		System.out.println("count of items with rating less than or equal to 4 stars " + count);

		WebElement inputbox1 = driver.findElement(By.xpath("//input[@title='Search for products, brands and more']"));
		EnterDataInput(inputbox1,"iPhone");
		List<WebElement> DiscountData = driver.findElements(By.xpath("//div[@class='UkUFwK']/span"));
		for (WebElement e : DiscountData) {
			String r = e.getText();
			r = r.replaceAll("[^0-9]", "");
			int c = Integer.parseInt(r);
			if (c >= 17) {
				System.out.println(e.findElement(By.xpath("../../../../..//div[@class='KzDlHZ']")).getText());
			}
		}
		EnterDataInput(inputbox1,"Coffee Mug");
		JavascriptExecutor js = (JavascriptExecutor) driver;
		js.executeScript("arguments[0].click();", driver.findElement(By.xpath("//div[contains(@title,'4')]//input")));
		// driver.findElement(By.xpath("//div[contains(@title,'4')]//input")).click();
		Thread.sleep(3000);

		List<WebElement> ViewerData = driver.findElements(By.cssSelector(".Wphh3N"));
		List<Integer> reviewData = new ArrayList<>();
		for (int i = 0; i < ViewerData.size(); i++) {
			String data = ViewerData.get(i).getText();
			data = data.replaceAll("[^0-9]", "");
			int ct = Integer.parseInt(data);
			reviewData.add(ct);
		}
		Collections.sort(reviewData, Collections.reverseOrder());

		for (int i = 0; i < 5; i++) {
			String r = reviewData.get(i) + "";
			if (r.length() > 3) {
				StringBuilder sb = new StringBuilder();
				int countInt = 0;
				for (int j = r.length() - 1; j >= 0; j--) {
					sb.insert(0, r.charAt(j));
					countInt++;
					if (countInt % 3 == 0 && j != 0) {
						sb.insert(0, ",");
					}
				}
				String xpath = String.format("//span[text()='(%s)']/../../a[@class='wjcEIp']", sb.toString());
				WebElement titleEle = driver.findElement(By.xpath(xpath));
				System.out.println("Title: " + titleEle.getText());
				System.out.println("Image URL :" + titleEle.getAttribute("href"));

			}

		}
	}
	
	public void EnterDataInput(WebElement ele,String data) throws InterruptedException {
		ele.click();
		ele.sendKeys(Keys.CONTROL + "A");
		ele.sendKeys(Keys.BACK_SPACE);
		Thread.sleep(2000);
		ele.sendKeys(data);
		ele.sendKeys(Keys.ENTER);
		Thread.sleep(2000);
		
	}

	@AfterClass
	public void tearDown() {
		if (driver != null) {
			driver.quit();
		}
	}
}
