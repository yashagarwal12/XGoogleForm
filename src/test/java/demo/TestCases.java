package demo;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeDriverService;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.logging.LogType;
import org.openqa.selenium.logging.LoggingPreferences;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.NoSuchElementException;
import java.util.logging.Level;
// import io.github.bonigarcia.wdm.WebDriverManager;
import demo.wrappers.Wrappers;

public class TestCases {
    ChromeDriver driver;
    Wrappers wrap = new Wrappers();
    By Name = By.xpath("//div[@class='Qr7Oae'][1]//input[@type='text']");
    By why_q = By.xpath("//div[@class='Qr7Oae'][2]//textarea[@aria-label='Your answer']");
    By experience = By
            .xpath("//div[@class='Qr7Oae'][3]//div[@class='nWQGrd zwllIb']/label/div/div/div[@aria-label='0 - 2']");
    By choice1 = By.xpath("//div[@class='Qr7Oae'][4]//div[@role='list']//label//div[@aria-label='Java']");
    By choice2 = By.xpath("//div[@class='Qr7Oae'][4]//div[@role='list']//label//div[@aria-label='Selenium']");
    By choice3 = By.xpath("//div[@class='Qr7Oae'][4]//div[@role='list']//label//div[@aria-label='TestNG']");
    By list = By.xpath("//div[@class='Qr7Oae'][5]//div[@role='listbox']");
    By initial = By.xpath("//div[@class='Qr7Oae'][5]//div[@role='listbox']/div[2]/div[@data-value='Mr']");
    By date = By.xpath("//div[@class='Qr7Oae'][6]//input[@type='date']");
    By hour = By
            .xpath("//div[@class='Qr7Oae'][7]//div[@class='ocBCTb']/div[@class='vEXS5c'][1]//input[@type='text']");
    By minute = By
            .xpath("//div[@class='Qr7Oae'][7]//div[@class='ocBCTb']/div[@class='vEXS5c'][2]//input[@type='text']");

    @BeforeTest
    public void startBrowser() {
        System.setProperty("java.util.logging.config.file", "logging.properties");
        ChromeOptions options = new ChromeOptions();
        LoggingPreferences logs = new LoggingPreferences();

        logs.enable(LogType.BROWSER, Level.ALL);
        logs.enable(LogType.DRIVER, Level.ALL);
        options.setCapability("goog:loggingPrefs", logs);
        options.addArguments("--remote-allow-origins=*");

        System.setProperty(ChromeDriverService.CHROME_DRIVER_LOG_PROPERTY, "build/chromedriver.log");

        driver = new ChromeDriver(options);

        driver.manage().window().maximize();
    }

    @Test

    public void testCase01() throws InterruptedException {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        // Navigated to the google form
        driver.navigate().to(
                "https://docs.google.com/forms/d/e/1FAIpQLSep9LTMntH5YqIXa5nkiPKSs283kdwitBBhXWyZdAS-e4CxBQ/viewform?pli=1");

        Thread.sleep(2000);

        // Enter Name
        WebElement name = driver.findElement(Name);
        wrap.sendkeys(name, "Crio Learner");

        Thread.sleep(1000);

        long epoch = System.currentTimeMillis() / 1000;
        System.out.println(epoch);

        // Enter the reason
        WebElement why = driver.findElement(why_q);
        wrap.sendkeys(why, "I want to be the best QA Engineer! " + epoch);
        Thread.sleep(1000);

        // Select Experience
        WebElement exp = driver.findElement(experience);
        wrap.clk(exp);
        Thread.sleep(1000);

        // Select the skills
        WebElement c1 = driver.findElement(choice1);
        wrap.clk(c1);
        Thread.sleep(1000);

        WebElement c2 = driver.findElement(choice2);
        wrap.clk(c2);
        Thread.sleep(1000);

        WebElement c3 = driver.findElement(choice3);
        wrap.clk(c3);
        Thread.sleep(1000);

        // Select the initial
        WebElement li = driver.findElement(list);
        wrap.clk(li);
        Thread.sleep(1000);

        WebElement mr = driver.findElement(initial);
        wrap.clk(mr);

        Thread.sleep(1000);

        LocalDate myDateObj = LocalDate.now();
        LocalDate newDate = myDateObj.minusDays(7);
        DateTimeFormatter myFormatObj = DateTimeFormatter.ofPattern("MM/dd/yyyy");
        String formattedDate = newDate.format(myFormatObj);
        formattedDate = formattedDate.replaceAll("/", "");
        System.out.println("formattedDate: " + formattedDate);

        WebElement day = driver.findElement(date);

        // Enter Date
        wrap.sendkeys(day, formattedDate);
        Thread.sleep(1000);

        LocalTime lt = LocalTime.now();
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("HH:mm");
        String formattedTime = lt.format(dtf);
        String[] split_time = formattedTime.split(":");

        // Enter Time
        WebElement hr = driver.findElement(hour);
        wrap.clk(hr);
        wrap.sendkeys(hr, split_time[0]);

        Thread.sleep(1000);

        WebElement mi = driver.findElement(minute);
        wrap.clk(mi);
        wrap.sendkeys(mi, split_time[1]);
        Thread.sleep(1000);

        // Click on save
        WebElement save = driver.findElement(By.xpath("//div[@role='button' and @aria-label='Submit']"));
        wrap.clk(save);

        Thread.sleep(1000);

        wait.until(ExpectedConditions.urlContains("formResponse"));

        // Verify the response
        WebElement verify_res = driver.findElement(By.xpath("//div[@class='RH5hzf RLS9Fe']//div[@class='vHW8K']"));
        System.out.println(verify_res.getText());

    }

    @AfterTest
    public void endTest() {
        driver.close();
        driver.quit();

    }
}