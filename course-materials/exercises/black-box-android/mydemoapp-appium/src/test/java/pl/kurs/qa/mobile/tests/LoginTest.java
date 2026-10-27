package pl.kurs.qa.mobile.tests;

import org.testng.Assert;
import org.testng.annotations.Test;
import pl.kurs.qa.mobile.base.BaseMobileTest;
import pl.kurs.qa.mobile.pages.CatalogPage;
import pl.kurs.qa.mobile.pages.LoginPage;

public class LoginTest extends BaseMobileTest {

    @Test(description = "TC-AND-01: poprawne dane logowania otwierają katalog produktów")
    public void tcAnd01ValidUserLandsOnCatalog() {
        LoginPage loginPage = new LoginPage(driver);
        loginPage.openFromMenu();
        loginPage.loginAs("bod@example.com", "10203040");

        // Post-login text is not verified from the APK; the stable catalog title is.
        Assert.assertEquals(new CatalogPage(driver).title(), "Products",
                "Po poprawnym logowaniu aplikacja powinna wyświetlić katalog produktów");
    }

    @Test(description = "TC-AND-02: pusta nazwa użytkownika wyświetla komunikat o wymaganym polu")
    public void tcAnd02EmptyUsernameShowsRequiredError() {
        LoginPage loginPage = new LoginPage(driver);
        loginPage.openFromMenu();
        loginPage.loginAs("", "10203040");

        Assert.assertEquals(loginPage.usernameError(), "Username is required",
                "Pusta nazwa użytkownika powinna wyświetlić komunikat o wymaganym polu");
    }

    @Test(description = "TC-AND-03: zablokowany użytkownik widzi błąd (treść wymaga potwierdzenia)")
    public void tcAnd03LockedOutUserSeesError() {
        LoginPage loginPage = new LoginPage(driver);
        loginPage.openFromMenu();
        loginPage.loginAs("alice@example.com", "10203040");

        Assert.assertTrue(loginPage.isAnyErrorShown(),
                "Zablokowany użytkownik powinien zobaczyć komunikat błędu");
    }
}
