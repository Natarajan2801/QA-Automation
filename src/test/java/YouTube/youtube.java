package YouTube;

import java.time.Duration;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import io.github.bonigarcia.wdm.WebDriverManager;

public class youtube {
	private WebDriver driver;

	@BeforeClass
	public void setUp() throws Exception {
		WebDriverManager.chromedriver().setup();
		driver = new ChromeDriver();
		driver.get("https://www.youtube.com/");
		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(40));
	}
	
	@Test
	public void youTube() throws Exception {
		String url=driver.getCurrentUrl();
		Assert.assertTrue(url.contains("youtube"));
	//	(//*[@id="guide-icon"]/yt-icon-shape/icon-shape/div)[1]
		//a[text()='About']
		//p[1]
		
		//url
		//yt-formatted-string[text()='Films']
//		(//*[@id="right-arrow"]/ytd-button-renderer/yt-button-shape/button/yt-touch-feedback-shape/div/div[2])[2]
		
		//*[@id="items"]/ytd-grid-movie-renderer[16]/ytd-badge-supported-renderer/div[2]/p
		
		//*[@id="items"]/ytd-grid-movie-renderer[16]/a/span
		
		
	}
	
	@AfterClass
	public void tearDown() {
		if (driver != null) {
			driver.quit();
		}
	}
}
