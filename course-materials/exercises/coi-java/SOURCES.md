# Wersje i źródła

Stan sprawdzenia dokumentacji: **26.10.2026**. Wersje są świadomie przypięte do startera, nie są deklaracją "najnowsze" ani potwierdzeniem instalacji na komputerach COI. APIs JUnit, Selenium i REST Assured sprawdzono przez Context7 (`resolve-library-id`, `query-docs`) oraz źródła producentów. Dostępność POM poniższych artefaktów potwierdzono w Maven Central (HTTP 200); to nie jest wykonanie testów ani pełny audyt zależności.

| Element | Wybrana wersja / API | Źródło |
|---|---|---|
| JDK | release 17, HttpServer, HttpExchange, HttpClient | [JDK 17 HttpServer](https://docs.oracle.com/en/java/javase/17/docs/api/jdk.httpserver/com/sun/net/httpserver/HttpServer.html), [HttpExchange](https://docs.oracle.com/en/java/javase/17/docs/api/jdk.httpserver/com/sun/net/httpserver/HttpExchange.html) |
| Maven | sprawdzono 3.10.0; wcześniejsza referencja 3.9.11 | [Aktualne wydanie](https://maven.apache.org/download.cgi), [referencja 3.9.11](https://maven.apache.org/docs/3.9.11/release-notes.html) |
| JUnit Jupiter | 5.13.4, @Test, assertEquals, assertThrows | [Wersjonowany przewodnik](https://docs.junit.org/5.13.4/user-guide/) |
| Surefire | 3.5.4, JUnit Platform, failIfNoTests | [JUnit Platform](https://maven.apache.org/surefire/maven-surefire-plugin/examples/junit-platform.html), [parametry](https://maven.apache.org/surefire/maven-surefire-plugin/test-mojo.html) |
| Compiler plugin | 3.14.1, release=17 | [Parametr release](https://maven.apache.org/plugins/maven-compiler-plugin/examples/set-compiler-release.html) |
| Build Helper | 3.6.1, add-test-source w generate-test-sources | [Dokumentacja celu](https://www.mojohaus.org/build-helper-maven-plugin/add-test-source-mojo.html) |
| Selenium Java | 4.38.0, ChromeOptions, ChromeDriverService, WebDriverWait(Duration) | [Serwis drivera](https://www.selenium.dev/documentation/webdriver/drivers/service/), [czekanie](https://www.selenium.dev/documentation/webdriver/waits/), [Page Objects](https://www.selenium.dev/documentation/test_practices/encouraged/page_object_models/) |
| REST Assured | 5.5.6, given/when/then, statusCode i body | [Oficjalne przykłady](https://github.com/rest-assured/rest-assured/wiki/Usage) |

Dokumentacja ogólna może pokazywać wersję nowszą niż przypięta. Dla migracji porównaj release notes i rzeczywiście uruchom testy na wybranym JDK, nie zmieniaj wersji podczas diagnozy bez zapisania powodu. ChromeDriver musi pasować do istniejącego Chrome; serwer Selenium/Grid i przeglądarka chmurowa nie są wymagane.

Porównanie [Playwright Java](https://playwright.dev/java/docs/intro) dotyczy biblioteki testowej; [Playwright CLI](https://github.com/microsoft/playwright-cli) to narzędzie sterowania przeglądarką przez agenta. Nie dołączamy drugiego frameworka do Maven ani nie pobieramy browserów w tym starterze. Android ma [oddzielne źródła i granice](../../Research/android-testing-options-2026-10.md).

Copilot: [CLI i logowanie](https://docs.github.com/en/copilot/how-tos/copilot-cli/set-up-copilot-cli/install-copilot-cli), [macierz instrukcji](https://docs.github.com/en/copilot/reference/custom-instructions-support). Zainstalowane wersje IntelliJ, wtyczki i CLI, uprawnienia oraz dostępne modele wpisz do własnego raportu. Źródło producenta nie potwierdza polityki organizacji ani działania reguły uprawnień na Twojej maszynie.
