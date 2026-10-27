package pl.kurs.qa.dolibarr.triage;

import java.time.Duration;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.Test;
import pl.kurs.qa.dolibarr.base.BaseTest;
import pl.kurs.qa.dolibarr.pages.HomePage;
import pl.kurs.qa.dolibarr.pages.LoginPage;

public class TriageDemoTest extends BaseTest {
    @Test(description = "TRIAGE-01: formularz umożliwia wpisanie hasła")
    public void passwordFieldIsAvailable() {
        openLogin();
        new LoginForm(driver, config.timeout()).enterPassword(config.password());
    }

    @Test(description = "TRIAGE-02: po zalogowaniu strona ma tytuł Dashboard")
    public void landingPageTitle() {
        openLogin().loginAs(config.login(), config.password());
        Assert.assertTrue(new HomePage(driver, config.timeout()).isLoaded());
        Assert.assertEquals(driver.getTitle(), "Dashboard");
    }

    @Test(description = "TRIAGE-03: środowisko staging udostępnia stronę logowania")
    public void stagingLoginPage() {
        driver.get("https://dolibarr-staging.invalid");
    }

    @Test(description = "TRIAGE-04: kryterium akceptacji Jira: błędne hasło wyświetla komunikat Nieprawidłowy login lub hasło")
    public void rejectedLoginMessage() {
        LoginPage login = openLogin();
        login.loginAs(config.login(), "incorrect-" + System.nanoTime());
        Assert.assertEquals(login.errorMessage(), "Nieprawidłowy login lub hasło");
    }

    private static final class LoginForm {
        private final WebDriver driver;
        private final Duration timeout;
        private final By password = By.id("passwrd");

        private LoginForm(WebDriver driver, Duration timeout) {
            this.driver = driver;
            this.timeout = timeout;
        }

        private void enterPassword(String value) {
            new WebDriverWait(driver, timeout)
                    .until(ExpectedConditions.visibilityOfElementLocated(password)).sendKeys(value);
        }
    }
}
