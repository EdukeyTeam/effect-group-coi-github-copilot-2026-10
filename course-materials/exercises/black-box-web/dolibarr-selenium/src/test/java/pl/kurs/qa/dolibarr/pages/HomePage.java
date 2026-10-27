package pl.kurs.qa.dolibarr.pages;

import java.time.Duration;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class HomePage {
    private final WebDriverWait wait;
    private final By loggedInUser = By.id("topmenu-login-dropdown");

    public HomePage(WebDriver driver, Duration timeout) {
        this.wait = new WebDriverWait(driver, timeout);
    }

    public boolean isLoaded() {
        return wait.until(ExpectedConditions.titleContains("Home"))
                && isLoggedInUserVisible();
    }

    public boolean isLoggedInUserVisible() {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(loggedInUser)).isDisplayed();
    }
}
