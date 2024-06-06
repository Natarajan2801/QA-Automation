package sample;

import java.time.Duration;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import io.github.bonigarcia.wdm.WebDriverManager;

public class main {

	public static void main(String[] args) throws InterruptedException {
		// TODO Auto-generated method stub
		WebDriverManager.chromedriver().setup();
		WebDriver driver=new ChromeDriver();
	
		//td[@class='film-best-picture']	
		
		driver.get("https://www.scrapethissite.com/pages/");
		driver.findElement(By.xpath("//a[text()='Oscar Winning Films: AJAX and Javascript']")).click();
		Thread.sleep(2000);
		driver.findElement(By.xpath("//a[@id='2015']")).click();
		Thread.sleep(4000);
		List<WebElement> win = driver.findElements(
				By.xpath("//td[@class='film-best-picture']"));
		
		for(WebElement e:win) {
			System.out.println("---"+e.getText());
			
		}
	

	}

}
