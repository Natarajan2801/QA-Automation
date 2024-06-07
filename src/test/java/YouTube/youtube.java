package YouTube;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.time.Duration;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
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
		String url = driver.getCurrentUrl();
		Assert.assertTrue(url.contains("youtube"));

		Thread.sleep(2000);
		driver.findElement(By.xpath("(//button[@id='button'])[7]")).click();
		Thread.sleep(2000);
		driver.findElement(By.xpath("//a[text()='About']")).click();
		Thread.sleep(2000);
		System.out.println(driver.findElement(By.xpath("//p[1]")).getText());
		driver.navigate().to(url);
		driver.findElement(By.xpath("(//button[@id='button'])[7]")).click();
		driver.findElement(By.xpath("//yt-formatted-string[text()='Movies']")).click();
		Thread.sleep(2000);
		for (int i = 0; i < 3; i++) { // *[@id="right-arrow"]/ytd-button-renderer/yt-button-shape/button/yt-touch-feedback-shape/div/div[2]
			driver.findElement(By.xpath(
					"//*[@id='right-arrow']/ytd-button-renderer/yt-button-shape/button/yt-touch-feedback-shape/div/div[2]"))
					.click();
			Thread.sleep(2000);
		}
		System.out.println(driver
				.findElement(
						By.xpath("//*[@id='items']/ytd-grid-movie-renderer[16]/ytd-badge-supported-renderer/div[2]/p"))
				.getText());
		System.out
				.println(driver.findElement(By.xpath("//*[@id='items']/ytd-grid-movie-renderer[16]/a/span")).getText());

		driver.findElement(By.xpath("(//button[@id='button'])[7]")).click();
		Thread.sleep(2000);
		driver.findElement(By.xpath("//yt-formatted-string[text()='Music']")).click();
		Thread.sleep(2000);
		for (int i = 0; i < 3; i++) { // *[@id="right-arrow"]/ytd-button-renderer/yt-button-shape/button/yt-touch-feedback-shape/div/div[2]
			driver.findElement(By.xpath(
					"(//*[@id='right-arrow']/ytd-button-renderer/yt-button-shape/button/yt-touch-feedback-shape/div/div[2])[2]"))
					.click();
			Thread.sleep(2000);
		}
		System.out.println(driver.findElement(By.xpath("//"
				+ " h3[contains(text(),'Bollywood Dance Hitlist')]")).getText());

		System.out.println(driver
				.findElement(By.xpath(
						"(//h3[contains(text(),'Bollywood Dance Hitlist')]/..//p[contains(text(),'tracks')])[2]"))
				.getText());

		
		driver.findElement(By.xpath("(//button[@id='button'])[7]")).click();
		Thread.sleep(2000);
		driver.findElement(By.xpath("//yt-formatted-string[text()='News']")).click();

		List<WebElement> title = driver.findElements(By.xpath("//a/span[@class='style-scope ytd-post-renderer']"));
		List<WebElement> news = driver.findElements(By.xpath("//*[@id='home-content-text']/span"));
		// span[@id='vote-count-middle']
		List<WebElement> vote = driver.findElements(By.xpath("//span[@id='vote-count-middle']"));

		for (int i = 0; i < 3; i++) {
			System.out.println(title.get(i).getText());
			System.out.println(news.get(i).getText());
			System.out.println(vote.get(i).getText());
		}
		
		String filePath =  System.getProperty("user.dir") + "\\src\\test\\resources\\qa_codeathon_week3.xlsx";
        ArrayList<String> searchTerms = readExcelData(filePath);
        
        for(int i=0;i<searchTerms.size();i++) {
        	WebElement input=driver.findElement(By.xpath("//input[@id='search']"));
    		input.click();
    		input.clear();
    		input.sendKeys(searchTerms.get(i));
    		input.sendKeys(Keys.ENTER);
    		Thread.sleep(4000);
    		List<WebElement> view = driver.findElements(By.xpath("//*[@id='metadata-line']/span[1]"));
    		for(int j=0;j<view.size();j++) 
    		{
    			((JavascriptExecutor)driver).executeScript("arguments[0].scrollIntoView(true);", view.get(j));
    		    
    			System.out.println(view.get(j).getText());
    		}
    		System.out.println(view.size());
        }
		
	}
	
	public static ArrayList<String> readExcelData(String filePath) {
        ArrayList<String> dataList = new ArrayList<>();

        try (FileInputStream fis = new FileInputStream(new File(filePath));
             Workbook workbook = new XSSFWorkbook(fis)) {

            Sheet sheet = workbook.getSheetAt(0);
            Iterator<Row> rowIterator = sheet.iterator();
            
            // Skip header row if present
            if (rowIterator.hasNext()) {
                rowIterator.next();
            }

            while (rowIterator.hasNext()) {
                Row row = rowIterator.next();
                Cell cell = row.getCell(0); // Assuming data is in the first column
                if (cell != null) {
                    dataList.add(cell.getStringCellValue());
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

        return dataList;
    }

	@AfterClass
	public void tearDown() {
		if (driver != null) {
			 driver.quit();
		}
	}
}
