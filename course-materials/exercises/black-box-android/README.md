# Testy czarnej skrzynki: aplikacja Android

Dostajesz APK i konto testowe, bez kodu aplikacji. Tak wygląda praca z wersją testową mObywatela. Ćwiczymy na publicznej aplikacji szkoleniowej Sauce Labs My Demo App 2.3.0: logowanie, formularz danych, koszyk, biometria i skaner QR to te same wzorce ekranów, które znacie z aplikacji obywatelskiej.

- APK: [mda-2.3.0-27.apk](https://github.com/saucelabs/my-demo-app-android/releases/download/2.3.0/mda-2.3.0-27.apk), pakiet `com.saucelabs.mydemoapp.android`.
- Projekt startowy: [`mydemoapp-appium`](mydemoapp-appium/) (Java, TestNG, Appium z UiAutomator2, Page Object).
- Lokatory odczytane z APK: [LOKATORY-Z-APK.md](LOKATORY-Z-APK.md).

## Jak to działa

Appium uruchamia sterownik UiAutomator2 na urządzeniu i rozmawia z nim przez WebDriver, tak jak Selenium z przeglądarką. Test w Javie wygląda prawie jak test Selenium: `AndroidDriver`, lokatory, jawne oczekiwania, Page Object. Zamiast DOM masz hierarchię widoków Androida.

Kolejność lokatorów:

1. accessibility id (`content-desc`), bo nie zależy od układu i wspiera dostępność,
2. `resource-id`,
3. UiSelector albo tekst, gdy dwa poprzednie nie istnieją,
4. XPath tylko w ostateczności.

Jeśli brakuje `content-desc` albo `resource-id` na ważnym elemencie, to dobre zgłoszenie do developerów: poprawia testowalność i dostępność jednocześnie.

## Pokaz prowadzącego (20 min)

Emulator Android 14 z Appium działa u prowadzącego. Zobaczycie:

1. Appium Inspector: zrzut ekranu, hierarchia, proponowane lokatory.
2. Zrzut hierarchii bez Appium: `adb exec-out uiautomator dump /dev/tty`.
3. Uruchomienie `LoginTest` i dowody z `target/evidence/` przy porażce.
4. Agenta w IntelliJ z serwerem MCP dla urządzenia (mobile-mcp albo Appium MCP): agent czyta hierarchię, klika, opisuje kroki i proponuje test.

## Ćwiczenie (30 min, bez urządzenia)

1. Otwórz projekt `mydemoapp-appium` w IntelliJ. Przeczytaj `LoginPage` i `LoginTest`.
2. W Copilot Chat (tryb Agent) użyj promptu **BB-AND-01**. Załącz `LOKATORY-Z-APK.md`.
3. Copilot dodaje `CheckoutInfoPage` i test walidacji pól obowiązkowych formularza wysyłki (puste pola, komunikaty błędów, przejście dalej po poprawnych danych).
4. Sprawdź kompilację: `.\mvnw.cmd -B test-compile` (Windows) albo `./mvnw -B test-compile`.
5. Oddaj test do uruchomienia prowadzącemu. Uruchomienie na emulatorze pokaże, które lokatory i założenia przetrwały kontakt z aplikacją.

## mObywatel w praktyce

Pytania do zespołu developerskiego, zanim zaczniecie automatyzację wersji testowej:

- Czy build testowy działa na emulatorze i bez Play Integrity? Produkcyjne aplikacje tego typu zwykle blokują emulatory i zrootowane urządzenia.
- Czy ekrany z dokumentami mają `FLAG_SECURE`? Wtedy zrzuty ekranu są czarne, ale hierarchia (`getPageSource`) i kliknięcia działają.
- Jak zalogować się bez prawdziwej tożsamości: konta testowe, stały PIN, `noReset` dla aktywowanej aplikacji.
- Czy kluczowe elementy mają `content-desc` lub stabilne `resource-id`?

## Prompt BB-AND-01

```text
Pracujesz w projekcie testów Appium (Java, TestNG, UiAutomator2) dla aplikacji Android, do której nie mamy kodu źródłowego. Mamy tylko APK i lokatory odczytane z niego (załączony LOKATORY-Z-APK.md).

Najpierw przeczytaj: pom.xml, testng.xml, config/MobileConfig.java, base/BaseMobileTest.java, pages/*.java, tests/LoginTest.java. Trzymaj się ich konwencji.

Zadanie: formularz danych do wysyłki (checkout).
1. Zaproponuj plan: CheckoutInfoPage z metodami intencji (wypełnij dane, wyślij, odczytaj błędy), lokatory zgodnie z kolejnością: accessibility id, resource-id, UiSelector, XPath tylko z uzasadnieniem.
2. Przypadki: TC-AND-10 puste pola obowiązkowe pokazują komunikaty błędów, TC-AND-11 brak kodu pocztowego pokazuje tylko błąd kodu, TC-AND-12 poprawne dane pozwalają przejść do płatności.
3. Do ekranu checkout dojdziesz przez: logowanie, produkt z katalogu, dodanie do koszyka, koszyk, przejście do zamówienia. Lokatorów tych kroków nie ma w pliku: oznacz je jako DO SPRAWDZENIA w Appium Inspector i nie zgaduj tekstów.
4. Tylko jawne oczekiwania, bez Thread.sleep. Asercje sprawdzają teksty komunikatów z pliku lokatorów.
5. Uruchom: mvnw -B test-compile. Nie uruchamiaj testów, jeśli nie ma serwera Appium.

Na koniec: lista plików, lokatory oznaczone jako DO SPRAWDZENIA i ryzyka, które pokaże dopiero uruchomienie na urządzeniu.
```

## Prompt BB-AND-02 (pokaz: agent na urządzeniu)

```text
Masz dostęp do urządzenia Android przez serwer MCP. Aplikacja com.saucelabs.mydemoapp.android jest zainstalowana. Pracujesz jak tester eksploracyjny, bez kodu aplikacji.

Cel: sprawdź logowanie i formularz danych do wysyłki. Czas: 10 minut. Dane: tylko konta z ekranu logowania i fikcyjne adresy.
1. Przed każdą akcją odczytaj hierarchię ekranu i nazwij element, który klikasz (resource-id lub content-desc).
2. Zapisuj kroki tak, by człowiek mógł je powtórzyć ręcznie.
3. Szukaj: brakujących komunikatów błędów, elementów bez content-desc, niespójnych tekstów, problemów z klawiaturą zasłaniającą pola.
4. Oddziel obserwację od hipotezy.

Wynik: lista znalezisk (krok, oczekiwane, rzeczywiste, dowód) i propozycja trzech przypadków testowych do automatyzacji w Appium + TestNG.
```
