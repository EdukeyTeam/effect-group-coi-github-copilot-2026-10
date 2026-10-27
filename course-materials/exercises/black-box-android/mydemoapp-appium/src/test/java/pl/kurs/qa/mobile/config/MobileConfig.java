package pl.kurs.qa.mobile.config;

import java.net.URI;
import java.time.Duration;

/**
 * Central test configuration. Every value comes from a -D system property first,
 * then an environment variable, then the default below.
 *
 * Example: ./mvnw test -Dudid=emulator-5556 -DnoReset=true
 */
public final class MobileConfig {

    private static final String APPIUM_URL = value("appiumUrl", "APPIUM_URL", "http://127.0.0.1:4723");
    private static final String DEVICE_NAME = value("deviceName", "DEVICE_NAME", "emulator-5554");
    private static final String UDID = value("udid", "UDID", "");

    /** Optional APK to install; empty = the app is already installed on the device. */
    private static final String APP_PATH = value("app", "APP", "");

    private static final String APP_PACKAGE = value("appPackage", "APP_PACKAGE",
            "com.saucelabs.mydemoapp.android");
    private static final String APP_ACTIVITY = value("appActivity", "APP_ACTIVITY",
            "com.saucelabs.mydemoapp.android.view.activities.SplashActivity");
    private static final String APP_WAIT_ACTIVITY = value("appWaitActivity", "APP_WAIT_ACTIVITY",
            "com.saucelabs.mydemoapp.android.view.activities.MainActivity");

    private static final boolean NO_RESET = Boolean.parseBoolean(value("noReset", "NO_RESET", "false"));
    private static final long TIMEOUT_SECONDS =
            Long.parseLong(value("timeout", "TIMEOUT", "10"));

    private MobileConfig() {
    }

    private static String value(String property, String envVar, String defaultValue) {
        String fromProperty = System.getProperty(property);
        if (fromProperty != null && !fromProperty.isBlank()) {
            return fromProperty;
        }
        String fromEnv = System.getenv(envVar);
        if (fromEnv != null && !fromEnv.isBlank()) {
            return fromEnv;
        }
        return defaultValue;
    }

    public static URI appiumUrl() {
        return URI.create(APPIUM_URL);
    }

    public static String deviceName() {
        return DEVICE_NAME;
    }

    /** Device serial from adb devices; blank lets UiAutomator2 select a device. */
    public static String udid() {
        return UDID;
    }

    /**
     * Local path or URL of the APK to install before the session starts,
     * e.g. apps/mda-2.3.0-27.apk (download: https://github.com/saucelabs/my-demo-app-android/
     * releases/download/2.3.0/mda-2.3.0-27.apk). Blank by default: then the session starts the
     * already installed app by appPackage/appActivity.
     */
    public static String appPath() {
        return APP_PATH;
    }

    public static String appPackage() {
        return APP_PACKAGE;
    }

    public static String appActivity() {
        return APP_ACTIVITY;
    }

    /** The activity the session waits for. "*" would accept any landing activity. */
    public static String appWaitActivity() {
        return APP_WAIT_ACTIVITY;
    }

    public static boolean noReset() {
        return NO_RESET;
    }

    public static Duration timeout() {
        if (TIMEOUT_SECONDS <= 0) {
            throw new IllegalArgumentException("timeout / TIMEOUT must be greater than zero");
        }
        return Duration.ofSeconds(TIMEOUT_SECONDS);
    }
}
