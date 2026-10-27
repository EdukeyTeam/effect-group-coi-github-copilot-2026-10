package pl.kurs.qa.dolibarr.tests;

import org.testng.Assert;
import org.testng.annotations.Test;
import pl.kurs.qa.dolibarr.base.BaseTest;
import pl.kurs.qa.dolibarr.pages.HomePage;
import pl.kurs.qa.dolibarr.pages.LoginPage;

public class LoginTest extends BaseTest {
    @Test(description = "TC-WEB-01: poprawne dane otwierają stronę Home zalogowanego użytkownika")
    public void validLoginShowsHome() {
        LoginPage login = openLogin();
        login.loginAs(config.login(), config.password());
        HomePage home = new HomePage(driver, config.timeout());
        Assert.assertTrue(home.isLoaded(), "Strona Home powinna być załadowana");
        Assert.assertTrue(driver.getTitle().startsWith("Home"), driver.getTitle());
        Assert.assertTrue(home.isLoggedInUserVisible(), "Brak znacznika zalogowanego użytkownika");
    }

    @Test(description = "TC-WEB-02: błędne hasło wyświetla komunikat logowania")
    public void wrongPasswordShowsError() {
        LoginPage login = openLogin();
        login.loginAs(config.login(), "incorrect-" + System.nanoTime());
        Assert.assertEquals(login.errorMessage(), "Bad value for login or password");
    }
}
