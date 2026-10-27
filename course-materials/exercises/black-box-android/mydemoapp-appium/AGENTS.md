# mydemoapp-appium - agent instructions

Black-box Android starter for Sauce Labs My Demo App 2.3.0
(`com.saucelabs.mydemoapp.android`). Java 17, Appium 3 + UiAutomator2,
TestNG only. Open this directory as its own IntelliJ project.

## Commands and versions

- Compile: `./mvnw -B -q test-compile` (Windows: `.\mvnw.cmd -B -q test-compile`).
- Smoke suite: `./mvnw test`; requires Appium at `http://127.0.0.1:4723`
  and an emulator with the app installed, or `-Dapp=apps/mda-2.3.0-27.apk`.
- Select a device with `-Dudid=emulator-5556` (serial from `adb devices`).
  `deviceName` is a label and does not select the Android device.
- Configuration: `MobileConfig`, system properties with environment fallbacks;
  `-Dtimeout=10` / `TIMEOUT` controls explicit waits in seconds.
- Keep java-client 11.0.0 (Appium 3 required). It brings only selenium-api;
  selenium-support is needed for WebDriverWait. Keep Selenium aligned via the BOM.
- Keep Surefire 3.5.6 while using `testng.xml`; 3.6.x removed suiteXmlFiles.
  The script-only wrapper downloads Maven 3.9.11.

## Conventions and boundaries

- Private `By` fields in Page Objects, full resource ids from `../LOKATORY-Z-APK.md`.
  Explain any provisional locator; confirm it with Appium Inspector.
- Explicit `WebDriverWait` only; no implicit waits or `Thread.sleep`.
- English code; Polish test descriptions and assertion messages.
  Each test description starts with its case ID (`TC-AND-01: ...`).
- One driver per method; failure evidence in `target/evidence/<Class>.<method>/`.
  Screenshot denial (FLAG_SECURE) must not prevent XML/metadata capture;
  logcat is best effort. A secure screen may also yield a blank image.
- Confirm the menu text "Log In" and locked-out error presentation in Inspector.
  Do not assert unverified text. `isAnyErrorShown()` is a provisional candidate check.
- Keep the three login tests. Checkout remains the participants' Copilot exercise.
- Do not weaken assertions to hide defects or add unrelated dependencies.
