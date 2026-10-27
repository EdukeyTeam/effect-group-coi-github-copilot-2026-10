package pl.kurs.qa.mobile.base;

import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.options.UiAutomator2Options;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import pl.kurs.qa.mobile.config.MobileConfig;
import pl.kurs.qa.mobile.support.MobileDriverHolder;

import java.net.MalformedURLException;
import java.net.URL;

/**
 * Base class for all mobile tests: one AndroidDriver per test method
 * (a fresh app session, like a fresh browser per test) and quit afterwards.
 */
public abstract class BaseMobileTest {

    protected AndroidDriver driver;

    @BeforeMethod(alwaysRun = true)
    public void startAppSession() {
        UiAutomator2Options options = new UiAutomator2Options()
                .setDeviceName(MobileConfig.deviceName())
                .setAppPackage(MobileConfig.appPackage())
                .setAppActivity(MobileConfig.appActivity())
                // SplashActivity only redirects; wait for the real landing screen instead
                .setAppWaitActivity(MobileConfig.appWaitActivity())
                .setNoReset(MobileConfig.noReset());

        if (!MobileConfig.udid().isBlank()) {
            options.setUdid(MobileConfig.udid());
        }
        String appPath = MobileConfig.appPath();
        if (!appPath.isBlank()) {
            options.setApp(appPath); // installs the APK before starting it
        }

        URL serverUrl;
        try {
            serverUrl = MobileConfig.appiumUrl().toURL();
        } catch (MalformedURLException e) {
            throw new IllegalStateException("Invalid appiumUrl: " + MobileConfig.appiumUrl(), e);
        }
        driver = new AndroidDriver(serverUrl, options);
        MobileDriverHolder.set(driver);
    }

    @AfterMethod(alwaysRun = true)
    public void stopAppSession() {
        AndroidDriver session = driver;
        driver = null;
        MobileDriverHolder.clear();
        if (session != null) {
            session.quit();
        }
    }
}
