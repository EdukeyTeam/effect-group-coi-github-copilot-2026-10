package pl.kurs.qa.dolibarr.pages;

import java.time.Duration;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class DemoEntryPage {
    private final WebDriver driver;
    private final Duration timeout;
    private final By serviceCompany = By.cssSelector("a:has(img[alt='Demo DemoCompanyServiceOnly'])");

    public DemoEntryPage(WebDriver driver, Duration timeout) {
        this.driver = driver;
        this.timeout = timeout;
    }

    public DemoEntryPage open(String baseUrl) {
        driver.get(baseUrl + "/public/demo/index.php");
        return this;
    }

    public LoginPage chooseServiceCompany() {
        new WebDriverWait(driver, timeout)
                .until(ExpectedConditions.elementToBeClickable(serviceCompany)).click();
        return new LoginPage(driver, timeout);
    }
}
