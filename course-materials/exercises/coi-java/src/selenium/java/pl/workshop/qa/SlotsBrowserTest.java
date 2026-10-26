package pl.workshop.qa;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.time.Duration;
import org.junit.jupiter.api.*;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.*;
import org.openqa.selenium.support.ui.*;
import static org.junit.jupiter.api.Assertions.*;

class SlotsBrowserTest {
    private FixtureServer server;
    private ChromeDriver driver;
    private WebDriverWait wait;

    @BeforeEach void openRealFixture() throws Exception {
        String chrome = System.getProperty("workshop.chrome");
        String chromedriver = System.getProperty("workshop.chromedriver");
        if (chrome == null || chromedriver == null) {
            throw new IllegalStateException("Podaj -Dworkshop.chrome i -Dworkshop.chromedriver; brak automatycznego pobierania.");
        }
        if (!Files.isRegularFile(Path.of(chrome)) || !Files.isRegularFile(Path.of(chromedriver))) {
            throw new IllegalArgumentException("Chrome i ChromeDriver muszą wskazywać zatwierdzone, istniejące pliki.");
        }
        server = FixtureServer.start(0);
        ChromeOptions options = new ChromeOptions();
        options.setBinary(chrome);
        options.addArguments("--headless=new", "--window-size=1280,900");
        ChromeDriverService service = new ChromeDriverService.Builder()
                .usingDriverExecutable(new File(chromedriver)).build();
        driver = new ChromeDriver(service, options);
        wait = new WebDriverWait(driver, Duration.ofSeconds(5));
        driver.get(server.baseUri().toString());
        wait.until(ExpectedConditions.textToBe(By.id("load-status"), "Gotowe"));
    }

    @Test void listsBothSlotsOnRealPage() {
        assertEquals("Wizyty - dane syntetyczne", driver.getTitle());
        assertEquals(2, driver.findElements(By.cssSelector("[data-testid='slot']")).size());
        assertTrue(driver.findElement(By.id("slot-SYN-01")).getText().contains("2"));
    }

    @Test void selectsAvailableSlotByStableId() {
        driver.findElement(By.id("slot-SYN-01")).click();
        assertEquals("SYN-01", driver.findElement(By.id("selected-slot")).getText());
        assertFalse(driver.findElement(By.id("slot-SYN-02")).isEnabled());
    }

    @AfterEach void preserveScreenshotAndClose(TestInfo info) throws Exception {
        try {
            if (driver != null) {
                try {
                    Path evidence = Path.of("target", "evidence");
                    Files.createDirectories(evidence);
                    String name = info.getTestMethod().orElseThrow().getName() + ".png";
                    Files.copy(driver.getScreenshotAs(OutputType.FILE).toPath(), evidence.resolve(name), StandardCopyOption.REPLACE_EXISTING);
                } finally { driver.quit(); }
            }
        } finally { if (server != null) server.close(); }
    }
}
