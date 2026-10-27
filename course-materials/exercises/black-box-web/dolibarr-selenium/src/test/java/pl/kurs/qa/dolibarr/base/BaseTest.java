package pl.kurs.qa.dolibarr.base;

import java.time.Duration;
import java.util.logging.Level;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.logging.LogType;
import org.openqa.selenium.logging.LoggingPreferences;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import pl.kurs.qa.dolibarr.config.TestConfig;
import pl.kurs.qa.dolibarr.pages.DemoEntryPage;
import pl.kurs.qa.dolibarr.pages.LoginPage;

public abstract class BaseTest {
    protected TestConfig config;
    protected WebDriver driver;

    @BeforeMethod(alwaysRun = true)
    public void createBrowser() {
        config = TestConfig.load();
        ChromeOptions options = new ChromeOptions();
        if (config.headless()) {
            options.addArguments("--headless=new");
        }
        if (config.noSandbox()) {
            options.addArguments("--no-sandbox");
        }
        options.addArguments("--window-size=1366,900", "--lang=en-US");
        LoggingPreferences logging = new LoggingPreferences();
        logging.enable(LogType.BROWSER, Level.ALL);
        options.setCapability("goog:loggingPrefs", logging);
        driver = new ChromeDriver(options);
        driver.manage().timeouts().implicitlyWait(Duration.ZERO);
        driver.manage().timeouts().pageLoadTimeout(config.timeout());
        driver.manage().window().setSize(new Dimension(1366, 900));
    }

    protected LoginPage openLogin() {
        return new DemoEntryPage(driver, config.timeout()).open(config.baseUrl()).chooseServiceCompany();
    }

    public WebDriver driver() {
        return driver;
    }

    @AfterMethod(alwaysRun = true)
    public void closeBrowser() {
        if (driver != null) {
            driver.quit();
            driver = null;
        }
    }
}
