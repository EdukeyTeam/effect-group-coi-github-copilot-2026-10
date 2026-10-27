package pl.kurs.qa.mobile.support;

import io.appium.java_client.android.AndroidDriver;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.logging.LogEntry;
import org.testng.ITestListener;
import org.testng.ITestResult;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.Instant;
import java.util.List;

/**
 * Saves failure evidence into target/evidence/&lt;Class&gt;.&lt;method&gt;/ :
 * screenshot.png, page-source.xml, logcat-tail.txt and failure.json.
 * The steps are independent on purpose - an app that sets FLAG_SECURE
 * forbids screenshots, but that must not stop the page source or logcat
 * from being saved.
 */
public class MobileEvidenceListener implements ITestListener {

    private static final int LOGCAT_TAIL_LINES = 300;

    @Override
    public void onTestFailure(ITestResult result) {
        Path dir = Paths.get("target", "evidence",
                result.getTestClass().getRealClass().getSimpleName() + "." + result.getName());
        try {
            Files.createDirectories(dir);
        } catch (IOException e) {
            System.err.println("Evidence: cannot create " + dir + ": " + e.getMessage());
            return;
        }

        AndroidDriver driver = MobileDriverHolder.get();
        saveFailureJson(result, dir,
                saveScreenshot(driver, dir),
                savePageSource(driver, dir),
                saveLogcatTail(driver, dir));
    }

    private String saveScreenshot(AndroidDriver driver, Path dir) {
        if (driver == null) {
            return null;
        }
        try {
            Path file = dir.resolve("screenshot.png");
            Files.write(file, driver.getScreenshotAs(OutputType.BYTES));
            return file.getFileName().toString();
        } catch (RuntimeException | IOException e) {
            writeNote(dir, "screenshot-error.txt", e); // e.g. FLAG_SECURE forbids capture
            return null;
        }
    }

    private String savePageSource(AndroidDriver driver, Path dir) {
        if (driver == null) {
            return null;
        }
        try {
            Path file = dir.resolve("page-source.xml");
            Files.writeString(file, driver.getPageSource(), StandardCharsets.UTF_8);
            return file.getFileName().toString();
        } catch (RuntimeException | IOException e) {
            writeNote(dir, "page-source-error.txt", e);
            return null;
        }
    }

    private String saveLogcatTail(AndroidDriver driver, Path dir) {
        if (driver == null) {
            return null;
        }
        try {
            List<String> lines = driver.manage().logs().get("logcat").getAll().stream()
                    .map(LogEntry::getMessage)
                    .map(String::stripTrailing)
                    .toList();
            List<String> tail = lines.subList(Math.max(0, lines.size() - LOGCAT_TAIL_LINES), lines.size());
            Path file = dir.resolve("logcat-tail.txt");
            Files.write(file, tail, StandardCharsets.UTF_8);
            return file.getFileName().toString();
        } catch (RuntimeException | IOException e) {
            writeNote(dir, "logcat-error.txt", e);
            return null;
        }
    }

    private void saveFailureJson(ITestResult result, Path dir, String screenshot,
                                 String pageSource, String logcatTail) {
        Throwable cause = result.getThrowable();
        String json = """
                {
                  "timestamp": "%s",
                  "status": "FAIL",
                  "testClass": "%s",
                  "testMethod": "%s",
                  "errorMessage": "%s",
                  "artifacts": {
                    "screenshot": %s,
                    "pageSource": %s,
                    "logcatTail": %s
                  }
                }
                """.formatted(
                Instant.now().toString(),
                jsonEscape(result.getTestClass().getName()),
                jsonEscape(result.getName()),
                jsonEscape(cause == null ? "unknown" : String.valueOf(cause.getMessage())),
                jsonString(screenshot),
                jsonString(pageSource),
                jsonString(logcatTail));
        try {
            Files.writeString(dir.resolve("failure.json"), json, StandardCharsets.UTF_8);
        } catch (IOException e) {
            System.err.println("Evidence: cannot write failure.json: " + e.getMessage());
        }
    }

    private static String jsonString(String value) {
        return value == null ? "null" : "\"" + jsonEscape(value) + "\"";
    }

    private static String jsonEscape(String value) {
        StringBuilder escaped = new StringBuilder();
        for (char c : value.toCharArray()) {
            switch (c) {
                case '"' -> escaped.append("\\\"");
                case '\\' -> escaped.append("\\\\");
                case '\n' -> escaped.append("\\n");
                case '\r' -> escaped.append("\\r");
                case '\t' -> escaped.append("\\t");
                default -> {
                    if (c < 0x20) {
                        escaped.append(String.format("\\u%04x", (int) c));
                    } else {
                        escaped.append(c);
                    }
                }
            }
        }
        return escaped.toString();
    }

    private static void writeNote(Path dir, String fileName, Throwable error) {
        try {
            Files.writeString(dir.resolve(fileName),
                    String.valueOf(error.getMessage()) + System.lineSeparator(),
                    StandardCharsets.UTF_8);
        } catch (IOException ignored) {
            // nothing left to try - the remaining artifacts are still saved
        }
    }
}
