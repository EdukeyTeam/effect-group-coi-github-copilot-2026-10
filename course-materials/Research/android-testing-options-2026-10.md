# Android dla testerów Java/Selenium: wybór warstwy i dowodów

Stan dokumentacji: **26.10.2026**. Zaczynamy od analizy bez urządzenia. Wariant live zależy od wybranego celu, rzeczywistych uprawnień i zatwierdzonego środowiska; nie zakładamy emulatora, telefonu, APK, dostępu chmurowego ani gotowej sesji na komputerze uczestnika.

## Co faktycznie kontroluje narzędzie

| Sposób pracy | Obserwowana warstwa | Co można potwierdzić |
|---|---|---|
| Selenium Java lub Playwright Java w desktopowym browserze | HTML DOM | Zachowanie strony, lokatory, nawigację i responsywność |
| Mobilny viewport/emulacja przeglądarki | Nadal strona WWW | Układ i interakcje web; nie stan natywnej Activity ani dialog uprawnień Android |
| Agent z Playwright CLI | Przeglądarka i udostępnione narzędzia | Eksplorację web, screenshot i obserwacje DOM; nie automatycznie test regresyjny |
| Appium + driver UiAutomator2 | Natywne drzewo Android udostępnione przez driver | Interakcje i stan natywnego UI na rzeczywistym, zatwierdzonym urządzeniu/emulatorze |
| Oglądanie strumienia urządzenia w przeglądarce | Obraz i sterowanie zdalnym ekranem | Widoczną obserwację; sama obecność strumienia nie daje sesji Appium lub kanału ADB |
| Plik XML hierarchii + log | Zachowany fragment dowodu | Hipotezy i propozycje lokatorów; nie rzeczywiste wykonanie testu |

Playwright opisuje [emulację urządzeń web](https://playwright.dev/java/docs/emulation); Appium rozdziela klienta, serwer i [drivery automatyzacji](https://appium.io/docs/en/latest/intro/drivers/). W aplikacji hybrydowej najpierw ustal kontekst `NATIVE_APP` lub webview. Lokator CSS nie jest dowodem dostępu do natywnego drzewa. Strategia `resource-id` i accessibility id musi wynikać z istniejących atrybutów, nie z etykiety widocznej wyłącznie na screenshotcie. Zobacz [UiAutomator2 i strategie lokatorów](https://github.com/appium/appium-uiautomator2-driver#element-location).

## Środowisko live wybieramy osobno

| Opcja | Twardy warunek | Ograniczenie |
|---|---|---|
| Telefon USB | Zatwierdzony telefon testowy, sterowniki/SDK i debugowanie autoryzowane na urządzeniu | Izolacja danych, reset i jeden właściciel aktywnej sesji |
| Lokalny emulator | Zgoda IT, odpowiednia wirtualizacja, pamięć/dysk i zatwierdzony obraz | Nie jest domyślnie dostępny na komputerze z ograniczonymi prawami |
| Zdalny emulator lub urządzenie | Zatwierdzona sieć, dostęp, izolacja sesji i rzeczywisty protokół automatyzacji | Sam URL do podglądu nie potwierdza kanału Appium/ADB |
| Usługa testowania urządzeń | Zgoda organizacji i obsługiwany framework/przesyłanie APK | Nie każda usługa batch jest interaktywnym endpointem Appium |
| Analiza syntetycznych plików | Istniejący edytor lub przeglądarka repozytorium | Brak potwierdzenia wykonania i zachowania prawdziwego urządzenia |

[Android Emulator](https://developer.android.com/studio/run/emulator) i [ADB](https://developer.android.com/tools/adb) mają osobne wymagania i mechanizmy autoryzacji. Firebase opisuje oddzielnie [Test Lab](https://firebase.google.com/docs/test-lab) oraz [Device Streaming](https://developer.android.com/studio/run/android-device-streaming); nie utożsamiaj uruchomienia testów wsadowych z dostępem do serwera Appium. Nie tworzymy w tym ćwiczeniu żadnego zasobu ani płatnej sesji.

Nie wystawiaj ADB lub Appium w publicznej sieci ani nie obchodź ograniczeń komputera. Serwer automatyzacji może wykonywać operacje na urządzeniu; zastosuj zatwierdzoną granicę sieciową, minimalne uprawnienia i właściwą autoryzację. [Zasady bezpieczeństwa Appium](https://appium.io/docs/en/latest/guides/security/) opisują te uprawnienia. Polecenia do zmiany portów, provisioningu, instalacji emulatora lub uruchamiania płatnych usług nie należą do tej ścieżki.

## Google AI Studio: natywny podgląd w przeglądarce

Google dokumentuje generowanie aplikacji Kotlin/Jetpack Compose oraz emulator Android działający w chmurze i przesyłający obraz do przeglądarki. W trybie Build wybiera się Android; do dalszej pracy lokalnej można pobrać projekt ZIP. Podgląd ma ograniczenia sprzętowe. [Oficjalna instrukcja Android w AI Studio](https://ai.google.dev/gemini-api/docs/aistudio-android?hl=en).

To kandydat do eksploracji bez lokalnego SDK, po sprawdzeniu konta i polityki organizacji. Nie potwierdzono dostępu na koncie kursowym ani integracji z Playwright. Dokumentacja nie stanowi obietnicy publicznego endpointu ADB/Appium. Sterowanie obrazem i przyciskami strony nie udostępnia automatycznie natywnych identyfikatorów ani drzewa UI. Do natywnej automatyzacji nadal potrzebny jest uzgodniony cel i odpowiedni driver.

Warianty Appium, emulatora i chmury wybieramy po pierwszym dniu. Nie instaluj dziś dodatkowych narzędzi ani nie twórz zasobów do tego ćwiczenia. Dla przygotowanego hosta diagnostyka obejmuje `adb devices -l`, `appium driver list --installed` i `appium driver doctor uiautomator2`. Sam emulator wymaga osobnej kontroli akceleracji i rzeczywistego małego przebiegu, nie tylko obecności wirtualizacji. [Akceleracja Android Emulator](https://developer.android.com/studio/run/emulator-acceleration), [diagnostyka UiAutomator2](https://github.com/appium/appium-uiautomator2-driver).

## Ćwiczenie bez urządzenia

Otwórz [COI-QA-07, pełny prompt i kryteria](../exercises/coi-java/WORKSHOP.md#coi-qa-07-v10-android-bez-urządzenia), [syntetyczne drzewo](../exercises/coi-java/evidence/android-hierarchy.synthetic.xml) i [syntetyczny log](../exercises/coi-java/evidence/android-ci.synthetic.log). Nie zawierają danych osoby ani prawdziwego pakietu klienta.

Rezultat to tabela: element -> istniejący atrybut -> proponowana strategia -> ryzyko -> dowód potrzebny do potwierdzenia. W logu błąd strategii CSS i niezgodny tekst są różnymi obserwacjami. Brak czasu przechwycenia XML nie uprawnia do uznania każdej niezgodności tekstu za flakiness. Oddziel fakt, hipotezę i kolejny eksperyment. Bez modelu przygotuj ten sam wynik ręcznie.

Do przyszłego live potrzebne będą: zatwierdzony APK i jego wersja, urządzenie i stan początkowy, zgodne wersje klienta Java/Appium/drivera, dostęp, metoda resetu, bezpieczne połączenie i ścieżki artefaktów. Nie podajemy losowej kombinacji wersji ani capabilities bez ustalonego celu. Sam test XML na komputerze nie jest testem natywnego UI Android.
