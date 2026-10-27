package pl.kurs.qa.mobile.pages;

import io.appium.java_client.AppiumBy;
import io.appium.java_client.android.AndroidDriver;
import org.openqa.selenium.By;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import pl.kurs.qa.mobile.config.MobileConfig;

/** Shared header; menu entries share itemTV and are distinguished by text. */
public class HeaderBar {

    private final WebDriverWait wait;
    private final By menu = AppiumBy.accessibilityId("View menu");
    private final By menuItems = AppiumBy.id("com.saucelabs.mydemoapp.android:id/itemTV");
    private final By cart = AppiumBy.accessibilityId("View cart");
    private final By cartBadge = AppiumBy.id("com.saucelabs.mydemoapp.android:id/cartTV");

    public HeaderBar(AndroidDriver driver) {
        this.wait = new WebDriverWait(driver, MobileConfig.timeout());
        this.wait.ignoring(StaleElementReferenceException.class);
    }

    public void openMenu() {
        wait.until(ExpectedConditions.elementToBeClickable(menu)).click();
    }

    public void openMenuItem(String title) {
        openMenu();
        wait.until(driver -> driver.findElements(menuItems).stream()
                .filter(item -> item.isDisplayed() && item.isEnabled() && title.equals(item.getText()))
                .findFirst().orElse(null)).click();
    }

    public void openCart() {
        wait.until(ExpectedConditions.elementToBeClickable(cart)).click();
    }

    public String cartBadgeText() {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(cartBadge)).getText();
    }

    public boolean isCartBadgeVisible() {
        try {
            return wait.until(ExpectedConditions.visibilityOfElementLocated(cartBadge)).isDisplayed();
        } catch (TimeoutException e) {
            return false;
        }
    }
}
