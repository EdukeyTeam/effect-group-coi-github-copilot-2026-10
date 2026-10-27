package pl.kurs.qa.mobile.support;

import io.appium.java_client.android.AndroidDriver;

/**
 * Holds the driver of the current test method so the TestNG listener
 * can reach it when a test fails. ThreadLocal keeps parallel runs safe.
 */
public final class MobileDriverHolder {

    private static final ThreadLocal<AndroidDriver> DRIVER = new ThreadLocal<>();

    private MobileDriverHolder() {
    }

    public static void set(AndroidDriver driver) {
        DRIVER.set(driver);
    }

    public static AndroidDriver get() {
        return DRIVER.get();
    }

    public static void clear() {
        DRIVER.remove();
    }
}
