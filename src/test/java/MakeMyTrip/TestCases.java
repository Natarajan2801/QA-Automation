
package MakeMyTrip;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;
import org.openqa.selenium.chrome.ChromeDriver;
import io.github.bonigarcia.wdm.WebDriverManager;
//Selenium Imports
import java.util.logging.Level;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriverService;
import org.openqa.selenium.chrome.ChromeOptions;

import org.openqa.selenium.logging.LogType;
import org.openqa.selenium.logging.LoggingPreferences;
///

public class TestCases {
    static WebDriver driver;

    public TestCases() {
        System.out.println("Constructor: TestCases");

        WebDriverManager.chromedriver().timeout(30).setup();
        ChromeOptions options = new ChromeOptions();
        LoggingPreferences logs = new LoggingPreferences();

        // Set log level and type
        logs.enable(LogType.BROWSER, Level.ALL);
        logs.enable(LogType.DRIVER, Level.ALL);
        options.setCapability("goog:loggingPrefs", logs);

        // Set path for log file
        System.setProperty(ChromeDriverService.CHROME_DRIVER_LOG_PROPERTY, "chromedriver.log");

        driver = new ChromeDriver(options);

        // Set browser to maximize and wait
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
        driver.get("https://www.makemytrip.com/");
        driver.findElement(By.xpath("//span[@data-cy='closeModal']")).click();

    }

    public void endTest() {
        System.out.println("End Test: TestCases");
      //  driver.close();
        driver.quit();

    }

    public boolean testCase01() throws InterruptedException {
        System.out.println("TestCase01: Verify Make My Trip Homepage URL");
       
        String url = driver.getCurrentUrl();
        if (url.contains("makemytrip")) {
            System.out.println("The URL of the Make My Trip homepage contains \"makemytrip.\"");
            return true;
        } else {
            System.out.println("Testcase Failed");
            return false;
        }
    }

    public boolean testCase02() throws InterruptedException {

        try {
            driver.navigate().to("https://www.makemytrip.com/");

            System.out.println("TestCase02: Get Flight Details from Bangalore to New Delhi");
           // driver.findElement(By.xpath("//span[@data-cy='closeModal']")).click();
            Thread.sleep(1000);
            WebElement FromBox = driver.findElement(By.id("fromCity"));
            FromBox.click();
            driver.findElement(By.xpath("//input[@placeholder='From']")).sendKeys("blr");
            Thread.sleep(2000);
            driver.findElement(By.xpath("//p[@class='font14 appendBottom5 blackText']")).click();

            WebElement ToBox = driver.findElement(By.id("toCity"));
            ToBox.click();
            driver.findElement(By.xpath("//input[@placeholder='To']")).sendKeys("del");
            Thread.sleep(1000);
            driver.findElement(By.xpath("//p[@class='font14 appendBottom5 blackText']")).click();
            Thread.sleep(1000);
            SelectCalendar(driver, "September 2024", "20");

            // driver.findElement(By.xpath("//span[text()='Departure']")).click();
            // driver.findElement(By.cssSelector(".DayPicker-NavButton.DayPicker-NavButton--next")).click();
            // driver.findElement(By.xpath(" //div[@aria-label='Sat Jan 20
            // 2024']")).click();
            driver.findElement(By.xpath(" //a[text()='Search']")).click();
            driver.findElement(By.cssSelector(".overlayCrossIcon")).click();
            List<WebElement> pricelist = driver.findElements(By.cssSelector(".clusterViewPrice"));
            List<String> pricelistText = new ArrayList<String>();

            for (WebElement a : pricelist) {

                // System.out.println(a.getText());

                pricelistText.add(a.getText());

            }
            for(int i=0;i<pricelistText.size();i++){
                if(pricelistText.get(i).contains("adult")){
                        System.out.println(pricelistText.get(i));
                }
            }
            System.out.println("Flight Price List :" + pricelistText);

        } catch (Exception e) {
            System.out.println(e.getMessage());
            return false;
        }
        return true;
    }

    public void SelectCalendar(WebDriver driver, String YearMonth, String Date) {
            String Year=driver.findElement(By.xpath("(//div[@class='DayPicker-Caption'])[2]")).getText();
        while (true) {
            if (Year.contains(YearMonth)) {
                break;
            } else {
                driver.findElement(By.cssSelector(".DayPicker-NavButton.DayPicker-NavButton--next")).click();
            }
        }
        // train (//div[@class='DayPicker-Body'])[2]//div[@class='DayPicker-Day']
        List<WebElement> list = driver
                .findElements(By.xpath("(//div[@class='DayPicker-Body'])[2]//div[@class='DayPicker-Day']"));
        for (int i = 0; i < list.size(); i++) {
            if (list.get(i).getText().contains(Date)) {
                list.get(i).click();
                System.out.println("Date Clicked");
                break;
            }
        }

    }

    public boolean testCase03() throws InterruptedException {

        // try {
        driver.navigate().to("https://www.makemytrip.com/");

        driver.findElement(
                By.xpath("//span[text()='Trains' and @class='headerIconTextAlignment chNavText darkGreyText']"))
                .click();

        driver.findElement(By.id("fromCity")).click();

        driver.findElement(By.cssSelector("[placeholder='From']")).sendKeys("ypr");

        Thread.sleep(1000);
        JavascriptExecutor j = (JavascriptExecutor) driver;
        j.executeScript("arguments[0].click();", driver.findElement(By.xpath("//span[text()='Bangalore']")));

        Thread.sleep(1000);
        WebElement ToBox = driver.findElement(By.xpath("//input[@placeholder='To']"));

        ToBox.click();

        ToBox.sendKeys("ndls");

        Thread.sleep(1000);

        driver.findElement(By.xpath("//span[text()='Delhi']")).click();

        Thread.sleep(1000);

        SelectCalendar(driver, "September 2024", "20");

        // driver.findElement(By.cssSelector(".DayPicker-NavButton.DayPicker-NavButton--next")).click();

        // driver.findElement(By.xpath(" //div[@aria-label='Sat Jan 20
        // 2024']")).click();

        Thread.sleep(1000);

        driver.findElement(By.xpath("//li[text()='Third AC']")).click();

        driver.findElement(By.xpath(" //a[text()='Search']")).click();

        Thread.sleep(1000);
        List<WebElement> pricelist = driver.findElements(By.xpath("//div[text()='3A']/../following-sibling::div"));

        List<String> pricelistText = new ArrayList<String>();

        for (WebElement a : pricelist) {
            pricelistText.add(a.getText());
        }

        System.out.println("Train Price List :" + pricelistText);
        return true;

    }

    public boolean testCase04() throws InterruptedException {

        driver.navigate().to("https://www.makemytrip.com/");
        driver.findElement(
                By.xpath("//span[text()='Buses' and @class='headerIconTextAlignment chNavText darkGreyText']")).click();
        driver.findElement(By.id("fromCity")).click();

        driver.findElement(By.cssSelector("[placeholder='From']")).sendKeys("bangl");

        Thread.sleep(1000);

        driver.findElement(By.xpath("//span[text()='Bangalore, Karnataka']")).click();

        Thread.sleep(1000);

        driver.findElement(By.cssSelector("[placeholder='To']")).sendKeys("del");

        Thread.sleep(1000);

        driver.findElement(By.xpath("//span[text()='Delhi']")).click();

        Thread.sleep(1000);

        SelectCalendar(driver, "September 2024", "20");

        // driver.findElement(By.cssSelector(".DayPicker-NavButton.DayPicker-NavButton--next")).click();

        // driver.findElement(By.xpath(" //div[@aria-label='Sat Jan 20
        // 2024']")).click();

        driver.findElement(By.xpath(" //button[text()='Search']")).click();

        Thread.sleep(1000);

        String output = driver.findElement(By.cssSelector(".error-title")).getText();

        // .error-title

        if (output.contains("No buses")) {

            System.out.println("No buses found is displayed ");

        }

        else {

            System.out.println("Fail");

            return false;

        }

        return true;

    }

}
