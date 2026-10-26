# Warsztat Java API i Angular

Punktem wyjścia jest [aplikacja API w przypiętej rewizji](https://github.com/LucasMatuszewski/sentry-course-06-2026/tree/f788b15e0ac72bb1f2bb000c6f290dbb6a716326/apps/api-spring). Funkcjonalność jest już zaimplementowana. Twoim zadaniem jest znaleźć luki w testach i dostarczyć nowe dowody, bez kopiowania istniejących rozwiązań.

## Scenariusz od wejścia do wyniku

Najpierw poproś o poprawną wycenę samochodu. Następnie zmień jedno pole na niepoprawne, sprawdź odrzucenie, popraw dane i powtórz żądanie. Porównaj składkę i identyfikatory dwóch poprawnych odpowiedzi. Osobno zbadaj celowo włączany błąd serwera oraz powrót do zwykłej wyceny. Żaden krok nie kupuje polisy i nie zapisuje wyceny do bazy.

## Mapa zachowania

Ścieżki plików poniżej są względne wobec `apps/api-spring/src/main/java/com/policylab/api/`. [Odnośniki do źródeł](SOURCE-CATALOG.md) prowadzą do dokładnej rewizji.

| Wejście | Metoda i źródło | Zachowanie wynikające z kodu | Stan / obserwacja |
| --- | --- | --- | --- |
| `GET /actuator/health` | Actuator, ekspozycja w `src/main/resources/application.properties` | Health endpoint; nie jest metodą własnego kontrolera | Pierwszy sygnał startu; oczekujemy HTTP 200 i `status=UP` po rzeczywistym uruchomieniu |
| `POST /api/quotes` | `quote/QuoteController.java`, `createQuote`, linie 44-75 | Waliduje rekord, wybiera scenariusz, liczy wycenę; sukces to HTTP 200 | Brak repozytorium i trwałego zapisu; nowe UUID na każde poprawne wywołanie |
| JSON wejściowy | `quote/QuoteRequest.java`, linie 11-33 | `vehicleType`: car/motorcycle/van; `coverage`: third-party/comprehensive; oba bez rozróżniania wielkości liter, ale bez przycinania spacji | `postalCode` musi być niepusty; nie ma walidacji formatu kodu pocztowego |
| Rok i wiek | `QuoteRequest`, `isManufactureYearValid` | Rok 1900..bieżący, wiek 17..100 włącznie | Walidacja roku korzysta z `Year.now()`, wycena z wstrzykniętego `Clock` |
| Poprawne dane | `quote/QuoteService.java`, `createQuote`, linie 32-49 | Składka z typu pojazdu, zakresu, wieku pojazdu i kierowcy; zaokrąglenie HALF_UP do 2 miejsc | Wynik: `quoteId`, `annualPremium`, `currency`, `riskBand`; kod pocztowy nie zmienia ceny |
| Błędne pola rekordu | `QuoteController.createQuote`, linie 48-50 | HTTP 400, ProblemDetail, tytuł `Invalid quote request` | Bez utworzonej wyceny |
| `X-Demo-Scenario` | `QuoteController.activeScenario`, linie 77-83 | Bez włączonej bramki każdy scenariusz działa jak normalny; nieznany nagłówek też | Nagłówek jest zamieniany na małe litery, bez przycinania spacji |
| Włączone `validation` / `server-error` | Kontroler i `quote/ApiExceptionHandler.java` | Odpowiednio 400 `Demo validation error` i 500 `Demo server error` | Walidacja rekordu poprzedza scenariusz; 500 jest zamierzonym wyzwalaczem |
| Włączone `slow` / `pii` | Kontroler i `telemetry/SentryQuoteTelemetry.java` | `slow`: sen 750 ms przed wyceną; `pii`: zwykła wycena | 750 ms nie jest gwarancją całkowitego czasu HTTP; niczego nie wysyłamy do Sentry |
| Niepoprawny JSON, brak body, niewłaściwa metoda lub Content-Type | Binder MVC i `ApiExceptionHandler.unexpectedError`, linie 58-66 | Wymaga wykonania i oceny: szeroki handler może zamienić wyjątek wejścia w 500 | Nie zakładaj automatycznie 400/405/415 z pamięci ani nie uznawaj 500 za poprawne wymaganie |

Nie ma tras CRUD dla polis/szkód, logowania, sesji, statusu zakupionej polisy ani mechanizmu idempotencji. Ponowienie POST daje tę samą składkę dla tych samych danych i czasu, ale nowy identyfikator. Odrzucanie duplikatu nie jest istniejącą regułą biznesową.

## Uruchomienie i pierwszy pomiar

W `apps/api-spring` deskryptor wskazuje Spring Boot **3.5.0**, Java toolchain **17**, Sentry **8.11.1**, plugin Sentry **5.5.0**; wrapper wskazuje Gradle **9.6.1**. README aplikacji podaje inne wersje. Sprawdź zgodność tej konkretnej kombinacji, nie aktualizuj zależności w ramach ćwiczenia. [Macierz Gradle](https://docs.gradle.org/current/userguide/compatibility.html) opisuje obsługiwane JVM.

Przed pierwszym poleceniem Gradle potwierdź wyłączenie telemetrii pluginu Sentry w przygotowanej kopii i usuń odziedziczone poświadczenia z procesu. Wyłączenie SDK aplikacji nie dowodzi wyłączenia telemetrii pluginu. Bez potwierdzenia tych warunków pozostajemy przy analizie źródła, bez uruchamiania zadań Gradle i bez odczytu `.env`.

W Windows rozpocznij nową sesję PowerShell i usuń zmienne Sentry, bez wypisywania wartości:

```powershell
Get-ChildItem Env:SENTRY_* | Remove-Item
```

Polecenia bash uruchamiaj w czystym środowisku procesu podanym poniżej. Ustawienie niesekretnego JAVA_HOME może być potrzebne zgodnie z lokalną konfiguracją.

Pierwszy warunek w terminalu:

```bash
java -version
```

Następnie sprawdź launcher, bez instalowania globalnego Gradle:

```bash
./gradlew --version
```

Wymagany toolchain kompilacji to **JDK 17**, niezależnie od JDK używanego do uruchamiania Gradle. Samo JDK 21 z ćwiczenia jFTP nie zapewnia toolchain 17; projekt nie deklaruje automatycznego resolvera. Sprawdź dostępne toolchainy przed testami:

```bash
env -i HOME="$HOME" PATH="$PATH" SENTRY_DSN= SENTRY_ENABLED=false SENTRY_DEBUG=false ./gradlew -q javaToolchains
```

W Windows:

```powershell
.\gradlew.bat -q javaToolchains
```

Jeśli JDK 17 nie jest już dostępne, wybierz analizę bez instalacji i zgłoś brak prowadzącemu.

W Windows PowerShell odpowiednikiem launchera jest `./gradlew.bat`. Pierwsze wywołanie wrappera może wymagać pobrania dystrybucji. Brak JDK, sieci lub zgody na pobranie oznacza przejście do wariantu bez instalacji.

Backend ma diagnostykę startową, która może wypisywać zmienne Sentry. Używaj czystego terminala bez poświadczeń. W Linux/macOS poniższe polecenia przekazują tylko wskazane zmienne i wyłączają SDK. Wykonuj je z `apps/api-spring`; nie wczytuj `.env`.

Uruchom najpierw dwa istniejące testy jednostkowe jako kontrolę środowiska:

```bash
env -i HOME="$HOME" PATH="$PATH" SENTRY_DSN= SENTRY_ENABLED=false SENTRY_DEBUG=false ./gradlew test --tests com.policylab.api.QuoteServiceTest --console=plain
```

W osobnym terminalu uruchom lokalny serwer z włączonymi scenariuszami demonstracyjnymi i wyłączoną telemetrią:

```bash
env -i HOME="$HOME" PATH="$PATH" SENTRY_DSN= SENTRY_ENABLED=false SENTRY_DEBUG=false ./gradlew bootRun --args='--sentry.enabled=false --sentry.dsn= --sentry.debug=false --demo.scenarios.enabled=true'
```

Test jednostkowy w tej samej oczyszczonej sesji PowerShell:

```powershell
.\gradlew.bat test --tests com.policylab.api.QuoteServiceTest --console=plain
```

```powershell
./gradlew.bat bootRun --args="--sentry.enabled=false --sentry.dsn= --sentry.debug=false --demo.scenarios.enabled=true"
```

Sprawdź start oddzielnie od testów. W PowerShell użyj `curl.exe` zamiast `curl` w przykładach HTTP.

```bash
curl -i http://localhost:8080/actuator/health
```

Zapisz body poprawnego żądania w swoim katalogu dowodów jako `quote.json`:

```json
{"vehicleType":"car","manufactureYear":2022,"coverage":"comprehensive","postalCode":"00-001","driverAge":38}
```

Wyślij je do lokalnej aplikacji:

```bash
curl -i -X POST http://localhost:8080/api/quotes -H "Content-Type: application/json" --data-binary @quote.json
```

W roku 2026 źródło przewiduje składkę **675.00 PLN**, pasmo **MEDIUM**, UUID i HTTP **200**. To oczekiwanie wyliczone ze źródła, nie przechwycona odpowiedź. Serwer używa aktualnego roku; test jednostkowy ma zegar ustawiony na 2026. Nie utrwalaj 675.00 jako wyniku obowiązującego w każdym przyszłym roku.

Zachowuj pełne, bezpieczne odpowiedzi lokalne przed przygotowaniem nowych mocków. Nie dodawaj bibliotek HTTP automatycznie: istnieją JUnit 5, AssertJ, Mockito i MockMvc przez `spring-boot-starter-test`. MockMvc nie dowodzi startu serwera ani ruchu przez prawdziwy port. Do osobnego testu HTTP możesz użyć dostępnego klienta Java 17 bez nowej zależności.

## Nowe zadania testowe

Istnieją już: 2 testy `QuoteServiceTest`, 3 `QuoteApiIntegrationTest`, 4 `DemoScenarioIntegrationTest`, test health, test konfiguracji oraz 3 testy telemetrii. To **14 metod z `@Test` w źródłach**, bez deklaracji ich przejścia. Test integracyjny podstawowej wyceny sprawdza typ ceny, ale nie dokładną wartość. Istniejące testy Angulara korzystają z fikcyjnych odpowiedzi HTTP i nie stanowią dowodu kontraktu backendu.

| Rodzina | Dane do zaprojektowania | Obserwowalny wynik / pytanie |
| --- | --- | --- |
| Granice wieku | 16/17, 24/25, 70/71, 100/101 | Akceptacja na granicy i zmiana ceny; nie łącz wielu błędnych pól w jeden przypadek |
| Granice roku | 1899/1900, rok bieżący, rok bieżący+1, wiek pojazdu 19/20/21 | Walidacja oraz limit wpływu wieku pojazdu na cenę; kontroluj zegar |
| Klasy równoważności | Wszystkie 3 typy pojazdu i 2 zakresy; wielkie litery; spacje; puste/null/brakujące pole | Rozróżnij deserializację, walidację i kalkulację; nie zakładaj trimowania |
| Klasyfikacja ryzyka | Dane prowadzące do wartości poniżej, na i ponad progiem 500 lub 800 | LOW przy <500, MEDIUM przy 500..<800, HIGH przy >=800; sprawdź osiągalność danych przez publiczne wejście |
| Kod pocztowy | `00-001`, `abc`, spacje, pusty, null | Obecny kontrakt sprawdza tylko niepustość; wymaganie formatu wymaga decyzji grupy |
| Ponowienia | Dwa identyczne POST, potem jedno błędne i ponownie poprawne | Równa cena, różne UUID, brak trwałego rekordu i brak zanieczyszczenia następnego wywołania |
| Bramka demonstracyjna | true/false, `SERVER-ERROR`, nieznany nagłówek, nagłówek ze spacjami | Porównaj status z aktywną konfiguracją; błędny rekord ma pierwszeństwo |
| Odporność HTTP | Uszkodzony JSON, brak body, inny typ treści, inna metoda | Najpierw uzgodnij oczekiwanie protokołu; zanotuj rozbieżność handlera zamiast dopasowywać asercję do awarii |

Plan 75-90 min: 10 min rozpoznania, 10 min ustalenia oczekiwań, 30 min nowych testów, 15 min rzeczywistego HTTP, 10-25 min analizy błędów i raportu. Nie naprawiaj aplikacji w ramach zadania na testy. Dla zgłoszonego defektu oddziel reprodukcję od oczekiwanej poprawki.

### REAL-QA01 v1 - mapa i wybór luk

```text
Zakres tego ćwiczenia ma pierwszeństwo przed przykładami i instrukcjami dotyczącymi wcześniejszego kursu: bez automatycznego local Context7/refresh, instalacji, MCP, computer-use ani lekcji Sentry. Raport z ćwiczenia pisz po polsku; język istniejącego kodu zachowaj. Pozostałe zasady bezpieczeństwa i ochrony zmian repozytorium nadal obowiązują.
Pracujemy w osobnym checkoutcie istniejącej aplikacji, nie w repozytorium materiałów. Pomóż mi przetestować apps/api-spring z rewizji f788b15e0ac72bb1f2bb000c6f290dbb6a716326. Najpierw odczytaj lokalne instrukcje, build.gradle.kts, wrapper, kontroler, rekordy, serwis, handler wyjątków i istniejące testy. Nie czytaj .env, poświadczeń, ankiet ani logów zewnętrznych systemów. Nie uruchamiaj usług i nie instaluj zależności bez ustalenia gotowości środowiska.
Przedstaw mapę: metoda HTTP, trasa, konkretna metoda Java, model wejścia/wyjścia, kolejność walidacji, zmiana stanu i wynik. Oddziel zamierzone scenariusze 400/500/slow od podejrzeń defektów. Wskaż różnice między README a deskryptorem. Wybierz trzy nowe przypadki niepokryte istniejącymi asercjami: granicę wieku, granicę roku i dwa identyczne żądania. Zaproponuj dane, oczekiwanie z uzasadnieniem i miejsce testu. Uwzględnij bieżący rok oraz Clock. Nie pisz rozwiązania ani kodu produkcyjnego. Zatrzymaj się na macierzy do mojej oceny; ja ustalam wymagania biznesowe.
```

### REAL-QA02 v1 - nowe testy i dowód wykonania

```text
Zakres tego ćwiczenia ma pierwszeństwo przed przykładami i instrukcjami dotyczącymi wcześniejszego kursu: bez automatycznego local Context7/refresh, instalacji, MCP, computer-use ani lekcji Sentry. Raport z ćwiczenia pisz po polsku; język istniejącego kodu zachowaj. Pozostałe zasady bezpieczeństwa i ochrony zmian repozytorium nadal obowiązują.
Pracujemy w apps/api-spring istniejącej aplikacji. Naszym zakresem są uzgodnione przypadki graniczne wieku/roku oraz ponowienie POST /api/quotes. Odczytaj aktualny kontrakt i istniejące asercje. Nie kopiuj gotowych testów jako nowego rozwiązania, nie zmieniaj produkcji, zależności ani oczekiwań tylko po to, aby uzyskać zielony wynik. Pokaż najpierw plan nowych asercji i uzyskaj moją ocenę oczekiwanych wyników.
Po uzgodnieniu dodaj testy w istniejącym stosie JUnit 5. Oddziel test serwisu ze stałym zegarem, integrację MockMvc i prawdziwy HTTP. Dla nowej funkcjonalności test najpierw ma zawieść; przy charakteryzowaniu istniejącego zachowania nie udawaj etapu Red. Sprawdź czułość ważnej asercji przez kontrolowaną zmianę oczekiwania w teście i przywróć je przed końcem. Nowy mock odpowiedzi wolno utworzyć dopiero z pełnego, świeżego przechwycenia bezpiecznej odpowiedzi lokalnego serwera.
Użyj wrappera projektu, wyłącz Sentry i usuń odziedziczone poświadczenia ze środowiska procesu bez ich wypisywania. Nie wczytuj .env. Nie korzystaj z chmury, produkcji ani Azure do programowania. Jeśli nie ma JDK, zależności lub gotowego serwera, podaj dokładny brak i przygotuj przypadki bez deklaracji wykonania. Raportuj osobno kompilację, liczbę testów wykrytych/wykonanych/pominiętych, start i rzeczywiste odpowiedzi HTTP. Nie commituj i nie pushuj.
```

## Przeglądarka Angular

Domyślnie to pokaz prowadzącego na przygotowanej kopii, nie wymagany etap konfiguracji stanowiska uczestnika. Samodzielną automatyzację wybierz dopiero po sprawdzeniu połączenia i wyłączeniu telemetrii.

[Angular](https://github.com/LucasMatuszewski/sentry-course-06-2026/tree/f788b15e0ac72bb1f2bb000c6f290dbb6a716326/apps/angular) ma Angular `^20.3.0`, CLI `^20.3.29`, TypeScript `~5.9.2`, Sentry `10.62.0` oraz lockfile pnpm. To ekran z przyciskami, bez formularza do ręcznego wpisania wieku czy roku. `QuoteApiService.createQuote` wysyła względny POST `/api/quotes`. `App.requestQuote` przekazuje stałe dane: car, 2018, comprehensive, 00-001, 35.

Przed startem potrzebna jest uzgodniona lokalna kopia z wyłączoną inicjalizacją/wysyłką Sentry oraz działającym połączeniem `/api` do backendu. Oryginalny `src/main.ts` inicjalizuje telemetrię; `angular.json` nie definiuje proxy dev-servera. Sam start na porcie 4200 nie zapewnia dostępu do API na 8080. Nie wymyślaj istniejącego pliku proxy ani zmiennej wyłączającej SDK. Jeśli te warunki nie są spełnione, przejdź do analizy źródeł. Nie otwieraj publicznego wdrożenia zamiast środowiska lokalnego.

Po przygotowaniu kopii i istniejących zależności polecenie z `apps/angular` wynika ze skryptu `start`:

```bash
pnpm start
```

Testy jednostkowe mają skrypt `test`, runner Karma i Jasmine; nie ma tu gotowego zestawu Selenium ani Playwright. Sprawdź gotowy runner grupy przed dodaniem porównania. Backend i jego bramka demonstracyjna muszą działać niezależnie od tego, że strona się wyświetla.

Przebieg 30-40 min: otwórz lokalny ekran, kliknij "Pobierz wycenę", sprawdź status i rzeczywiste POST; wywołaj "Wymuś błąd walidacji", potem ponownie poprawną wycenę. Przy włączonej bramce oczekiwany status błędu to `Błąd: 400`; po ponowieniu powinien zniknąć na rzecz nowej wyceny. Dla stałego roku 2026 cena danych z przycisku wynika ze źródła jako 725.00 PLN, MEDIUM. Sprawdź oba elementy `.status`, ponieważ szablon renderuje ten sam status w dwóch miejscach. Nie wymagaj dwóch miejsc po przecinku w tekście: komponent wyświetla liczbę bez formatera walutowego.

Osobne zadania: "Stress test (8 równoległych)" ma wysłać 8 żądań, z czego 2 ze scenariuszem slow; "Łańcuch 3 wycen" ma wysłać 3 sekwencyjne żądania, drugie slow. Zmierz liczbę i kolejność żądań oraz końcowy status. To lokalne scenariusze poprawności i współbieżności, nie pomiar wydajności produkcji. Nie klikaj "Test Sentry Error" w teście zwykłego użytkownika: to celowy wyjątek.

### REAL-QA03 v1 - rzeczywisty przepływ w przeglądarce

```text
Zakres tego ćwiczenia ma pierwszeństwo przed przykładami i instrukcjami dotyczącymi wcześniejszego kursu: bez automatycznego local Context7/refresh, instalacji, MCP, computer-use ani lekcji Sentry. Raport z ćwiczenia pisz po polsku; język istniejącego kodu zachowaj. Pozostałe zasady bezpieczeństwa i ochrony zmian repozytorium nadal obowiązują.
Pracujemy z istniejącym apps/angular i lokalnym apps/api-spring, rewizja f788b15e0ac72bb1f2bb000c6f290dbb6a716326. Wykorzystaj nasz dostępny stos Selenium w Javie. Playwright może być osobnym porównaniem tylko wtedy, gdy jest już dostępny i wybrany przez grupę. Nie instaluj runnerów automatycznie. Najpierw przeczytaj App, szablon, QuoteApiService i aktualną konfigurację uruchomienia. Nie zakładaj istnienia formularza ani proxy.
Potwierdź wyłączenie telemetrii, lokalny adres UI, trasowanie /api i włączoną bramkę demonstracyjną backendu, bez czytania sekretów. Jeśli czegoś brakuje, nazwij brak i zakończ na planie oraz analizie źródeł. Przy gotowym środowisku zaproponuj test: poprawna wycena, wymuszony błąd walidacji, ponowna poprawna wycena. Przed kodem uzgodnij ze mną oczekiwania. Kontroluj widoczny tekst, cenę zależną od roku, nowe UUID, rzeczywiste żądania i powrót do działania. Nie mockuj sieci w E2E. Uwzględnij dwa elementy statusu i selektory wynikające z prawdziwego DOM. Nie używaj stałych sleepów ani przycisku celowego wyjątku jako zwykłego kroku użytkownika.
Następnie wybierz jedną nową lukę: osiem równoległych wywołań albo trzy sekwencyjne. Oddziel dowód liczby żądań od samego tekstu statusu. Zapisz kroki ręczne, zrzuty ekranu, błędy konsoli i wyniki automatyzacji; oczekiwane błędy 400/500 opisz oddzielnie od nieoczekiwanych wyjątków. Nie poprawiaj produkcji, nie kopiuj źródeł do materiałów, nie commituj i nie pushuj.
```

## Wariant bez instalacji

Otwórz wskazane pliki przez publiczne linki i przygotuj macierz wejście/oczekiwanie/źródło/brak dowodu. W parze przejrzyj istniejące asercje i zaproponuj trzy nowe. Jeśli korzystasz z bezpiecznych odpowiedzi udostępnionych podczas lokalnej demonstracji, podaj ich rewizję i datę; ich analiza nie oznacza wykonania testu na Twoim komputerze. Bez odpowiedzi nie generuj fikcyjnych nagłówków, UUID ani raportu testów.

Filtrowanie testów i raporty: [oficjalna dokumentacja Gradle](https://docs.gradle.org/current/userguide/java_testing.html#test_filtering). Polecenia powyżej są odczytane z projektu i dokumentacji; ich powodzenie dla tej rewizji wymaga lokalnego wykonania.
