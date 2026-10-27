package pl.kurs.qa.mobile.pages;

import io.appium.java_client.AppiumBy;
import io.appium.java_client.android.AndroidDriver;
import org.openqa.selenium.By;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import pl.kurs.qa.mobile.config.MobileConfig;

public class LoginPage {

    private static final String ID = "com.saucelabs.mydemoapp.android:id/";

    private final AndroidDriver driver;
    private final WebDriverWait wait;
    private final By username = AppiumBy.id(ID + "nameET");
    private final By password = AppiumBy.id(ID + "passwordET");
    private final By submit = AppiumBy.id(ID + "loginBtn");
    private final By usernameError = AppiumBy.id(ID + "nameErrorTV");
    private final By passwordError = AppiumBy.id(ID + "passwordErrorTV");
    private final By dialogTitle = AppiumBy.id("android:id/alertTitle");
    private final By dialogMessage = AppiumBy.id("android:id/message");
    // UiAutomator2 exposes transient toasts by class, without a stable resource id.
    private final By toast = AppiumBy.xpath("//android.widget.Toast");

    public LoginPage(AndroidDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, MobileConfig.timeout());
        this.wait.ignoring(StaleElementReferenceException.class);
    }

    // Confirm "Log In" and the locked-out error's text/presentation in Appium Inspector.
    public void openFromMenu() {
        new HeaderBar(driver).openMenuItem("Log In");
        wait.until(ExpectedConditions.visibilityOfElementLocated(username));
    }

    public void loginAs(String username, String password) {
        replaceValue(this.username, username);
        replaceValue(this.password, password);
        wait.until(ExpectedConditions.elementToBeClickable(submit)).click();
    }

    public String usernameError() {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(usernameError)).getText();
    }

    public String passwordError() {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(passwordError)).getText();
    }

    /** Provisional error candidates, not an assertion of unverified message text. */
    public boolean isAnyErrorShown() {
        try {
            return wait.until(ignored -> isDisplayed(usernameError) || isDisplayed(passwordError)
                    || isDisplayed(dialogTitle) || isDisplayed(dialogMessage) || isDisplayed(toast));
        } catch (TimeoutException e) {
            return false;
        }
    }

    private void replaceValue(By locator, String value) {
        WebElement field = wait.until(ExpectedConditions.elementToBeClickable(locator));
        field.clear();
        field.sendKeys(value);
    }

    private boolean isDisplayed(By locator) {
        return driver.findElements(locator).stream().anyMatch(WebElement::isDisplayed);
    }
}
