package pl.kurs.qa.dolibarr.pages;

import java.time.Duration;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class LoginPage {
    private final WebDriverWait wait;
    private final By username = By.id("username");
    private final By password = By.id("password");
    private final By submit = By.cssSelector("input[type='submit']");
    private final By error = By.cssSelector(".jnotify-message");

    public LoginPage(WebDriver driver, Duration timeout) {
        this.wait = new WebDriverWait(driver, timeout);
    }

    public void loginAs(String login, String secret) {
        replaceValue(username, login);
        replaceValue(password, secret);
        wait.until(ExpectedConditions.elementToBeClickable(submit)).click();
    }

    public String errorMessage() {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(error)).getText().trim();
    }

    private void replaceValue(By locator, String value) {
        WebElement field = wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
        field.clear();
        field.sendKeys(value);
    }
}
