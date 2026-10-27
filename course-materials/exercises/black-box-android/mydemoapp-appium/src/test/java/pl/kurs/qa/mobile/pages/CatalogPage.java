package pl.kurs.qa.mobile.pages;

import io.appium.java_client.AppiumBy;
import io.appium.java_client.android.AndroidDriver;
import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import pl.kurs.qa.mobile.config.MobileConfig;

public class CatalogPage {

    private final WebDriverWait wait;
    private final By title = AppiumBy.id("com.saucelabs.mydemoapp.android:id/productTV");
    private final By productList = AppiumBy.id("com.saucelabs.mydemoapp.android:id/productRV");

    public CatalogPage(AndroidDriver driver) {
        this.wait = new WebDriverWait(driver, MobileConfig.timeout());
    }

    public String title() {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(title)).getText();
    }

    public boolean isProductListVisible() {
        try {
            return wait.until(ExpectedConditions.visibilityOfElementLocated(productList)).isDisplayed();
        } catch (TimeoutException e) {
            return false;
        }
    }
}
