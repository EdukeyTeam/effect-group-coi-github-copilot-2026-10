package pl.kurs.qa.dolibarr.support;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.Instant;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.json.Json;
import org.openqa.selenium.json.JsonOutput;
import org.openqa.selenium.logging.LogType;
import org.testng.ITestListener;
import org.testng.ITestResult;
import pl.kurs.qa.dolibarr.base.BaseTest;

public class EvidenceListener implements ITestListener {
    @Override
    public void onTestFailure(ITestResult result) {
        if (!(result.getInstance() instanceof BaseTest test) || test.driver() == null) {
            return;
        }
        WebDriver driver = test.driver();
        Path directory = Path.of("target", "evidence",
                result.getTestClass().getRealClass().getSimpleName() + "." + result.getName());
        try {
            Files.createDirectories(directory);
        } catch (IOException error) {
            System.err.println("Cannot create evidence directory: " + error.getMessage());
            return;
        }

        StringBuilder errors = new StringBuilder();
        String url = read(driver::getCurrentUrl, errors);
        String title = read(driver::getTitle, errors);
        save(errors, () -> Files.write(directory.resolve("screenshot.png"),
                ((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES)));
        save(errors, () -> write(directory.resolve("page.html"), driver.getPageSource()));
        save(errors, () -> write(directory.resolve("url.txt"), url + "\n"));
        save(errors, () -> write(directory.resolve("browser-console.log"),
                driver.manage().logs().get(LogType.BROWSER).getAll().stream()
                        .map(Object::toString).collect(Collectors.joining("\n"))));

        Throwable failure = result.getThrowable();
        String message = failure == null || failure.getMessage() == null ? "" : failure.getMessage();
        String firstFrame = failure == null ? "" : Arrays.stream(failure.getStackTrace())
                .filter(frame -> frame.getClassName().startsWith("pl.kurs.qa.dolibarr."))
                .findFirst().map(StackTraceElement::toString).orElse("");
        Map<String, Object> summary = new LinkedHashMap<>();
        summary.put("test", result.getName());
        summary.put("class", result.getTestClass().getName());
        summary.put("startedAt", Instant.ofEpochMilli(result.getStartMillis()).toString());
        summary.put("durationMs", result.getEndMillis() - result.getStartMillis());
        summary.put("url", url);
        summary.put("title", title);
        summary.put("exceptionClass", failure == null ? "" : failure.getClass().getName());
        summary.put("message", message.substring(0, Math.min(message.length(), 500)));
        summary.put("firstAppStackFrame", firstFrame);
        save(errors, () -> {
            StringBuilder json = new StringBuilder();
            try (JsonOutput output = new Json().newOutput(json)) {
                output.setPrettyPrint(true).write(summary);
            }
            write(directory.resolve("failure.json"), json + "\n");
        });
        if (!errors.isEmpty()) {
            System.err.println("Evidence capture warnings: " + errors);
            save(new StringBuilder(), () -> write(directory.resolve("capture-errors.log"), errors.toString()));
        }
    }

    private static String read(Supplier<String> action, StringBuilder errors) {
        try {
            return action.get();
        } catch (RuntimeException error) {
            errors.append(error.getClass().getSimpleName()).append(": ").append(error.getMessage()).append('\n');
            return "";
        }
    }

    private static void write(Path path, String text) throws IOException {
        Files.writeString(path, text, StandardCharsets.UTF_8);
    }

    private static void save(StringBuilder errors, Capture action) {
        try {
            action.run();
        } catch (IOException | RuntimeException error) {
            errors.append(error.getClass().getSimpleName()).append(": ").append(error.getMessage()).append('\n');
        }
    }

    @FunctionalInterface
    private interface Capture {
        void run() throws IOException;
    }
}
