# Warsztat natywnego Androida

Wybieramy tę ścieżkę po pierwszym dniu, jeśli grupa ma gotowe i dozwolone środowisko. Natywny ekran Androida wymaga natywnego runnera. Selenium i przeglądarkowy Playwright nie obsługują drzewa Compose ani Android Views w tych aplikacjach.

## Dwie różne aplikacje

| Aplikacja | Rzeczywisty zakres | Deskryptor | Ograniczenie |
| --- | --- | --- | --- |
| [Główna aplikacja Kotlin](https://github.com/LucasMatuszewski/sentry-course-06-2026/tree/f788b15e0ac72bb1f2bb000c6f290dbb6a716326/apps/android) | Compose, lokalne polisy i szkody w Room, eksperymenty błędów, kalkulacja i osobne wywołanie NBP | AGP 9.1.1, katalog Kotlin 2.2.10, Room 2.7.0, Sentry 7.14.0; compileSdk 36.1, targetSdk 36, minSdk 24; target bytecode Java 11 | Brak wrappera Gradle; aplikacja zawiera telemetrię i funkcje poza zakresem ćwiczenia |
| [Referencja Java](https://github.com/LucasMatuszewski/sentry-course-06-2026/tree/f788b15e0ac72bb1f2bb000c6f290dbb6a716326/apps/android-sentry-reference) | Mała Activity: komunikat, obsłużony wyjątek, kontekst, log, feedback, transakcja, celowy crash | AGP 8.13.1, Java 17, compile/targetSdk 36, minSdk 21, Sentry 8.46.0 | Brak wrappera; nie ma modelu polis/szkód ani gotowego zestawu testów |

Nie łącz tych projektów w jedną aplikację. Katalog wersji Kotlin nie jest dowodem, że sam kompilator uruchomił się w tej wersji. Podobnie Java 11 w `compileOptions` nie określa wymaganej JVM dla AGP. Sprawdź właściwe JDK i zgodność Gradle w gotowym środowisku.

Główna aplikacja nie wywołuje `POST /api/quotes`. `MainActivity` tworzy `SentryDemoViewModel`, ten używa `InsuranceRepository` i DAO Room. Metoda `fetchPolandExchangeRates` wykonuje osobne HTTP do publicznego NBP, nie do Springa. Podczas tego ćwiczenia nie uruchamiamy wywołania NBP. Referencja Java również nie jest klientem backendu.

## Scenariusz szkody w lokalnej bazie

Użytkownik wybiera polisę, zgłasza szkodę z kosztem 2500 PLN i opisem "Syntetyczna szkoda testowa", sprawdza obecność zgłoszenia, zamyka i ponownie otwiera aplikację, usuwa zgłoszenie, a następnie przywraca dane demonstracyjne. Dopiero po podstawowym przebiegu bada niepoprawne dane i ponowienia.

| Krok / wejście | Metoda i plik w `apps/android/app/src/main/java/com/example/` | Obserwacja do sprawdzenia |
| --- | --- | --- |
| Odczyt polis i szkód | `data/InsuranceDao.kt`, `getAllPolicies`, `getAllClaims`; `data/InsuranceDatabase.kt` | Polisy sortowane malejąco po składce, szkody malejąco po czasie zgłoszenia; seed bazy jest asynchroniczny |
| Wybór karty polisy | `ui/SentrySandboxScreen.kt`, `TabInsurancePortal`, linie 499-568 | Otwarty dialog dla wybranej polisy, przekazany jej rzeczywisty `id` |
| Koszt 2500, opis syntetyczny | `ClaimSubmissionDialog`, linie 803-896; `SentryDemoViewModel.submitNewClaim`, linie 301-355 | Zapis przez repozytorium do Room, numer `SZK-...-2026`, status `Weryfikacja (Verification)`; brak HTTP do Springa |
| Koszt -1 | `submitNewClaim`, linie 316-318 | Przechwycony `IllegalArgumentException`; brak nowej szkody, komunikat błędu w lokalnych logach UI |
| Koszt 0, pusty tekst, `abc`, `12,50`, `NaN` | Parser w dialogu, linia 878; walidacja ViewModel | Zadanie do eksploracji i uzgodnienia wymagań. Nie zakładaj walidacji kwoty, separatora ani wartości skończonych, której nie ma w źródle |
| Powtórzenie zgłoszenia | `submitNewClaim` i `data/Claim.kt` | Kolejny numer losowy, automatyczne ID; brak reguły deduplikacji po opisie, koszcie lub numerze polisy |
| Usunięcie | `deleteClaimsReport`, `InsuranceRepository.deleteClaim`, DAO `deleteClaim` | Wiersz znika; drugie usunięcie tej samej encji wymaga obserwacji wyniku |
| Reset | `resetAndReseedDatabase`, repozytorium `resetDatabase` / `reseedDatabase` | Czyszczenie dwóch tabel i odtworzenie czterech polis; sprawdź stan końcowy, nie stałe ID; brak wspólnej transakcji w repozytorium |

`Claim.policyId` jest zwykłym polem liczbowym bez zadeklarowanego klucza obcego w encji. `OnConflictStrategy.REPLACE` dotyczy klucza głównego, nie biznesowej deduplikacji. To punkty analizy testera, a nie polecenie przebudowania modelu.

Celowo dostępne są osobne wyzwalacze awarii: `triggerUncaughtCrash` kończy aplikację przez NPE; ujemne koszty mają oznaczoną opcję w dialogu; kalkulacja odrzuca wiek poniżej 18 lat. Nie traktuj tych eksperymentów jako odkrytych, niezamierzonych defektów. Podejrzenia błędów parsera czy resetu opisuj oddzielnie.

## Gotowość i pierwszy test

Warunek wejścia: zaakceptowane lokalne środowisko z Android Studio, odpowiednim JDK/Gradle/SDK oraz już dostępnym emulatorem albo uzgodnionym urządzeniem testowym. Nie instalujemy tu SDK, emulatora, nowego runnera ani narzędzia do zdalnego telefonu. Brak środowiska oznacza wariant analityczny.

W obu projektach nie ma `gradlew` ani `gradlew.bat`. Nie używaj polecenia `./gradlew` z innej aplikacji. W Android Studio otwórz dokładny katalog projektu i sprawdź konfigurację lokalnego Gradle; z terminala korzystaj tylko z już skonfigurowanej zgodnej instalacji.

Pierwszy pomiar w katalogu wybranego projektu, jeśli globalny launcher istnieje:

```bash
gradle --version
```

Główna aplikacja deklaruje runner `androidx.test.runner.AndroidJUnitRunner`, zależności Espresso i Compose UI oraz testy lokalne Robolectric/Roborazzi. Po przygotowaniu kopii bez aktywnej telemetrii i bez ładowania sekretów można sprawdzić istniejące testy lokalne:

```bash
gradle :app:testDebugUnitTest
```

Testy na już gotowym urządzeniu/emulatorze to osobny etap:

```bash
gradle :app:connectedDebugAndroidTest
```

Te polecenia wynikają z projektu i [dokumentacji testów Androida](https://developer.android.com/studio/test/command-line); nie są deklaracją udanego wykonania. Referencja Java nie ma deklaracji runnera ani zależności testowych. Nie przedstawiaj jej jako gotowej do uruchomienia tych samych testów.

W głównej aplikacji są trzy metody testów lokalnych: dodawanie 2+2, odczyt nazwy aplikacji, zrzut przykładowego Greeting. Jedyny test instrumentowany sprawdza nazwę pakietu. Nie testują procesu szkody. Ponadto źródło testu screenshotowego odwołuje się do `Greeting`, którego deklaracji nie ma, a test pakietu oczekuje `com.example`, choć `applicationId` to `com.aistudio.sentrydemo.kblyzw`. Zapisz te rozbieżności jako zastany stan. Nie usuwaj testów ani nie zmieniaj ich po cichu, aby ukryć problem.

Przed ręcznym przebiegiem potwierdź wyłączenie inicjalizacji i wysyłki Sentry w uzgodnionej kopii. Oryginał ma automatyczną instrumentację i demonstracje feedbacku. Nie wpisuj danych rzeczywistych, nie otwieraj konfiguracji poświadczeń i nie uruchamiaj wysyłki feedbacku. Brak bezpiecznej kopii oznacza analizę bez uruchomienia. W UI mogą występować angielskie etykiety; zapisz rzeczywiste teksty, nie deklaruj pełnej polskiej lokalizacji.

## Zadanie 45-60 min

Poświęć 10 min na mapę stanu i istniejące testy, 10 min na uzgodnienie reguł kwoty i ponowień, 20-25 min na jeden nowy test lokalny albo natywny, 5-15 min na ręczny przebieg i raport. Korzystaj z oczekiwania na stan/semantykę, nie ze stałych opóźnień. Polisy początkowe i zapis szkody są asynchroniczne.

Zadanie podstawowe: koszt 2500, zapis, ponowne otwarcie, odczyt, usunięcie. Zadanie rozszerzające: jedna granica walidacji albo dwa identyczne zgłoszenia. Oczekiwane zachowanie po błędzie ustal z grupą przed kodem. Nie rozwiązuj wszystkich przypadków naraz i nie zmieniaj aplikacji w zadaniu dotyczącym testów.

### REAL-QA04 v1 - natywny stan i test szkody

```text
Zakres tego ćwiczenia ma pierwszeństwo przed przykładami i instrukcjami dotyczącymi wcześniejszego kursu: bez automatycznego local Context7/refresh, instalacji, MCP, computer-use ani lekcji Sentry. Raport z ćwiczenia pisz po polsku; język istniejącego kodu zachowaj. Pozostałe zasady bezpieczeństwa i ochrony zmian repozytorium nadal obowiązują.
Pracujemy z istniejącym apps/android z rewizji f788b15e0ac72bb1f2bb000c6f290dbb6a716326. To Kotlin/Compose/Room, nie aplikacja webowa i nie klient API wycen. apps/android-sentry-reference jest osobną referencją Java. Przeczytaj instrukcje, katalog wersji, deskryptory, MainActivity, ViewModel, dialog szkody, repozytorium, DAO, encje i istniejące testy. Nie czytaj .env, kluczy, ankiet ani prywatnych logów.
Najpierw przedstaw scenariusz: wybór rzeczywistej lokalnej polisy, koszt 2500 PLN i opis "Syntetyczna szkoda testowa", zapis, odczyt po ponownym otwarciu, usunięcie. Wskaż stan przed/po, asynchroniczne operacje i oczekiwany wynik każdej asercji. Dodaj jeden niepokryty przypadek: błędna kwota albo powtórzenie zgłoszenia. Oddziel to, co robi kod, od wymagania, które ja muszę zatwierdzić. Nie traktuj oznaczonego wyzwalacza crasha jako znalezionego defektu.
Sprawdź istniejący runner oraz gotowość JDK/Gradle/SDK i urządzenia bez instalacji. W obu katalogach brak wrappera, więc nie wymyślaj ./gradlew. Wskaż zastane rozbieżności Greeting i applicationId bez osłabiania testów. Jeśli środowisko i kopia z wyłączoną telemetrią są gotowe, po mojej ocenie oczekiwań dodaj jeden test w istniejącym stosie lokalnym albo natywnym. Selenium i przeglądarkowy Playwright nie są runnerami Compose. Nie uruchamiaj NBP, Sentry, chmury, realnego telefonu ani instalacji. Nie modyfikuj produkcji ani zależności.
Jeżeli brak warunku uruchomienia, przygotuj macierz testów i analizę wywołań bez udawania testu wykonanego. W raporcie oddziel kompilację, testy lokalne, instrumentowane, start i ręczne sprawdzenie UI ze zrzutami ekranu. Podaj liczbę przypadków wykonanych i pominiętych oraz dokładne braki. Nie commituj i nie pushuj.
```

## Bez instalacji i wariant Java

W publicznych źródłach prześledź `onSubmit` dialogu aż do DAO. Opracuj cztery przypadki z danymi 2500, -1, 0 i `abc`; dla każdego wskaż spodziewaną zmianę bazy, komunikat oraz brakujący dowód wykonania. Nie przepisuj gotowego kodu jako rozwiązania. Nagranie lub zrzut z lokalnego pokazu pomaga analizie, ale nie jest testem wykonanym na Twoim środowisku.

Jeśli grupa wybierze referencję Java zamiast procesu szkody, zakresem jest obsłużony wyjątek i lokalna zmiana etykiety statusu bez zakończenia Activity, a osobno celowy crash i ponowne uruchomienie. Nie oczekuj dostarczenia zdarzenia do Sentry. Nie ma tu polis, szkód, Room ani połączenia ze Springiem. Dodanie natywnego runnera byłoby osobnym uzgodnionym zadaniem, nie istniejącą funkcjonalnością.
