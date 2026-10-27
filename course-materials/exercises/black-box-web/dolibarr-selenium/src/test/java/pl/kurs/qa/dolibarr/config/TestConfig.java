package pl.kurs.qa.dolibarr.config;

import java.time.Duration;

public record TestConfig(String baseUrl, String login, String password, boolean headless,
                         Duration timeout, boolean noSandbox) {
    public static TestConfig load() {
        long seconds = Long.parseLong(value("timeoutSeconds", "TIMEOUT_SECONDS", "15"));
        if (seconds <= 0) {
            throw new IllegalArgumentException("dolibarr.timeoutSeconds must be positive");
        }
        return new TestConfig(
                value("baseUrl", "BASE_URL", "https://demo.dolibarr.org").replaceAll("/+$", ""),
                value("login", "LOGIN", "demo"),
                value("password", "PASSWORD", "demo"),
                Boolean.parseBoolean(value("headless", "HEADLESS", "true")),
                Duration.ofSeconds(seconds),
                Boolean.parseBoolean(value("noSandbox", "NO_SANDBOX", "false")));
    }

    private static String value(String property, String environment, String fallback) {
        return System.getProperty("dolibarr." + property,
                System.getenv().getOrDefault("DOLIBARR_" + environment, fallback));
    }
}
