# Ćwiczenia Java QA z Copilot

## Mapa ćwiczeń strony i repozytorium

[Ćwiczenia na stronie kursu](https://devpowers.com/szkolenia/effect-group/coi-github-copilot-2026-10/cwiczenia.html) mają krótkie identyfikatory QA. Poniższe pełne zadania repozytorium używają identyfikatorów COI-QA. Wybierz zakres przed uruchomieniem agenta: prompt strony nie upoważnia automatycznie do wszystkich działań pełnego zadania. QA04 obejmuje pracę nad testem; implementacja jednego AC-05 w COI-QA-05 wymaga osobnej decyzji.

| Strona | Pełne zadanie w repo |
|---|---|
| QA01 | COI-QA-00 |
| QA02 | COI-QA-00 |
| QA03 | COI-QA-01 |
| QA04 | COI-QA-02 / COI-QA-05 |
| QA05 | COI-QA-03 |
| QA06 | COI-QA-04 |
| QA07 | COI-QA-10 |
| QA08 | COI-QA-06 |
| QA09 | COI-QA-07 |
| QA10 | COI-QA-08 |
| QA11 | COI-QA-09 |
| QA12 | COI-QA-10 |


Pełne prompty są przeznaczone do wklejenia w IntelliJ Copilot lub Copilot CLI po świadomym wybraniu tego startera. Każdy ma stały ID i wersję 1.0. Ścieżki `src/...` odnoszą się do katalogu `course-materials/exercises/coi-java/`, nie do root `app/`. Najpierw [SETUP](SETUP.md), potem [SPEC](SPEC.md). Czasy są budżetem ćwiczenia; nie zastępują godzin i programu zajęć.

Do każdego wyniku dołącz [raport](REPORT-TEMPLATE.md). Gdy brakuje instalacji, dostępu do modelu lub kredytów, wykonaj wariant plikowy ręcznie albo w parze. Nie musisz kupować dostępu. Materiały w `evidence/` są syntetyczne. Nie kopiuj ich do raportu jako rzekomo wykonanych testów.

## COI-QA-00 v1.0: dostęp, kontekst i pierwsza mała prośba

**Czas:** 35 minut. **Efekt:** lista rzeczywiście dostępnych narzędzi i jeden raport odczytu bez zmian kodu. **Start:** `SETUP.md`, `AGENTS.md`, `SPEC.md`, `pom.xml`, root `.github/copilot-instructions.md`. **Polecenie startowe:**

```text
copilot --version
```

**Baza:** brak założenia o licencji, gotowości IDE lub wykonaniu testów. Uruchom `java -version`, `javac -version` i `mvn -version` zgodnie z SETUP, osobno sprawdź Copilot w IntelliJ. **Granice:** odczyt, bez instalacji, kont, hooków, profili, kluczy lub cloud agenta. **Bez instalacji/kredytów:** uzupełnij tabelę narzędzi ręcznie, przeczytaj instrukcje i wskaż brakujące warunki.

**Pełny prompt:**

```text
Pracujemy po polsku w opcjonalnym starterze course-materials/exercises/coi-java. To materiały syntetyczne, nie system COI. W tej prośbie nie zmieniaj plików i nie uruchamiaj instalacji ani usług. Przeczytaj tylko AGENTS.md i SPEC.md tego startera, jego pom.xml, SETUP.md oraz root .github/copilot-instructions.md. Nie przeszukuj reszty course-materials i nie buduj app/.
Wskaż: katalog projektu, źródło wymagań, granicę między działającym odczytem i niegotowym zapisem oraz dokładne polecenie testu bazowego wynikające z pom.xml. Wyjaśnij, które instrukcje klient mógł odczytać, a których automatycznej obsługi nie potwierdzono. Zaproponuj jeden bezpieczny krok odczytu do mojej akceptacji. Nie twierdź, że narzędzie, licencja lub sandbox działa, dopóki nie ma dowodu z mojego środowiska. Zakończ krótkim raportem faktów, braków i ryzyka przekazywania danych. Bez prawdziwych danych, endpointów, tokenów i zmian konfiguracji.
```

## COI-QA-01 v1.0: scenariusze i śledzenie wymagań

**Czas:** 25 minut. **Efekt:** 6-8 scenariuszy z normalnym wejściem, granicami i błędami, powiązanych z AC. **Start:** `SPEC.md`, `src/test/java/pl/workshop/qa/AppointmentPolicyTest.java`, `evidence/slots.synthetic.json`, `REPORT-TEMPLATE.md`. **Polecenie, jeśli środowisko działa:**

```text
mvn clean -Dtest=AppointmentPolicyTest test
```

**Baza:** 8 testów polityki; endpointy zapisu nadal 501. **Granice:** tylko projekt testów, bez zmiany implementacji lub kryteriów. **Bez instalacji/kredytów:** przeczytaj te cztery pliki; przygotuj tabelę ręcznie, oznacz brak wykonania.

**Pełny prompt:**

```text
Na podstawie SPEC.md startera Java QA oraz AppointmentPolicyTest.java zaprojektuj 6-8 scenariuszy rezerwacji wizyty. Użyj wyłącznie SYN-01, SYN-02 i liczb syntetycznych. Uwzględnij przypadek pozytywny, granice 1 i 4, brak miejsc, przekroczenie miejsc, niepoprawny format liczby oraz nieznany slot. Nie dodawaj wymagań poza SPEC.md. Każdy wiersz ma zawierać ID AC, warunki, wejście, obserwowalny wynik, poziom testu, istniejący test lub lukę i źródło dowodu. Oddziel działającą czystą politykę od brakującej integracji POST, która obecnie zwraca 501. Zwróć uwagę, że poprawna walidacja w HTML nie dowodzi walidacji API. Nie pisz implementacji. Jeśli nie wykonaliśmy testów, nazwij wynik analizą plików; nie przypisuj syntetycznych danych do rzeczywistego systemu. Podaj jedną rzecz wymagającą decyzji człowieka przed rozszerzeniem zakresu.
```

## COI-QA-02 v1.0: istniejące pokrycie i uczciwe testy JUnit

**Czas:** 35 minut. **Efekt:** jeden nowy test zachowania albo udokumentowane istniejące pokrycie. **Start:** `AppointmentPolicy.java`, `AppointmentPolicyTest.java`, `SPEC.md` AC-02/03/04/10. **Polecenie:**

```text
mvn clean -Dtest=AppointmentPolicyTest test
```

**Baza:** 8 testów. Przypadek `partySize=-1, remaining=2` nie ma osobnego testu, ale dostarczony kod już go odrzuca. Nowy test może więc od razu być zielony. **Granice:** najpierw tylko testy; nie psuj kodu, żeby wymusić Red. **Bez instalacji/kredytów:** napisz propozycję testu i wskaż, co sprawdziłby runner, bez deklarowania wyniku.

**Pełny prompt:**

```text
Przeczytaj SPEC.md AC-02/03/04/10, AppointmentPolicy.java i AppointmentPolicyTest.java. Pracuj wyłącznie nad obserwowalnym wynikiem evaluate, bez testów prywatnych metod i bez zmiany układu klas. Najpierw uruchom istniejący test JUnit, jeśli JDK i Maven są dostępne, oraz zapisz faktyczną liczbę testów, błędów i pominięć. Zaproponuj jeden brakujący przypadek brzegowy; wybierz ujemną liczbę osób przy poprawnym remaining, o ile nie jest już pokryta. Dodaj tylko ten test z konkretną asercją zachowania. Uruchom go, a potem pełny zmieniony zakres. Jeżeli test już przechodzi, opisz istniejące zachowanie i nie zmieniaj implementacji ani nie fabrykuj Red. Jeżeli wykryje sprzeczność ze specyfikacją, pokaż dowód i poproś o wybór dalszego kroku przed zmianą zakresu. Nie osłabiaj asercji, nie pomijaj testu, nie instaluj zależności poza pom.xml i nie publikuj zmian. Bez runtime przygotuj diff jako propozycję, z jawnym brakiem wykonania.
```

## COI-QA-03 v1.0: Selenium, niestabilny test i Page Object

**Czas:** 40 minut. **Efekt:** diagnoza poparta wskazanymi liniami dowodów, mały Page Object/test lub propozycja diffu. **Start:** `src/selenium/java/pl/workshop/qa/SlotsBrowserTest.java`, `src/main/resources/index.html`, `evidence/BrittleSlotTest.java.txt`, `evidence/flaky-ci.synthetic.log`, `evidence/slots-dom.synthetic.html`. **Polecenie bazowe Linux:**

```bash
mvn clean -Pselenium -Dtest=SlotsBrowserTest test -Dworkshop.chrome=/usr/bin/google-chrome -Dworkshop.chromedriver=/approved-tools/chromedriver
```

PowerShell, zastąp ścieżki faktycznymi:

```powershell
mvn clean -Pselenium -Dtest=SlotsBrowserTest test '-Dworkshop.chrome=C:\Program Files\Google\Chrome\Application\chrome.exe' '-Dworkshop.chromedriver=C:\approved-tools\chromedriver.exe'
```

**Baza:** 2 testy Selenium, 0 pominiętych, jeżeli zatwierdzone pliki i sandbox Chrome działają. Wadliwy `.java.txt` jest fragmentem do analizy, nie domyślnym testem ani samodzielną klasą do uruchomienia. **Granice:** nie stosuj automatycznie fragmentu, nie zmieniaj API, nie dodawaj sleep/retry i nie wyłączaj sandboxa. **Bez instalacji/kredytów:** diagnoza na podstawie syntetycznego DOM/logów i propozycja Page Object; bez potwierdzenia stabilności przez wykonanie.

**Pełny prompt:**

```text
Zdiagnozuj bounded problem Selenium Java, korzystając tylko z SlotsBrowserTest.java, index.html, SPEC.md AC-07 i evidence/BrittleSlotTest.java.txt, flaky-ci.synthetic.log, slots-dom.synthetic.html. Dowody są jawnie syntetyczne, nie są uruchomieniem w COI. Nie zakładaj, że syntetyczny log da się odtworzyć identycznie na szybkim komputerze. Oddziel hipotezę dotyczącą synchronizacji od błędnego lokatora i od niezaimplementowanego POST 501. Dla każdej podaj fakt, linię dowodu i sposób falsyfikacji. Zaproponuj najmniejszą zmianę: Page Object dla wyboru slotu i asercję wyniku użytkownika. Nie dawaj Thread.sleep, retries, ignorowania wyjątków, mieszaniny implicit/explicit wait ani osłabionych asercji. Najpierw porównaj propozycję z istniejącą działającą bazą; jeśli mamy browser i driver, uruchom dwa testy bazowe i zapisz ich wyniki. Po moim wyborze przenieś jeden fragment do nowego testu w src/selenium/java i pokaż, jaki warunek ma wykryć. Nie zmieniaj domyślnego mvn clean test ani nie aktywuj wadliwego pliku automatycznie. Zapisz PNG i raport lub oznacz, że to wyłącznie analiza plików. Nie instaluj browsera/drivera i nie zmieniaj uprawnień.
```

## COI-QA-04 v1.0: kontrakt REST Assured i dowód odpowiedzi

**Czas:** 40 minut. **Efekt:** świeży zapis odpowiedzi lokalnego API i jeden dodatkowy test kontraktu albo jednoznacznie opisany brak dostępu. **Start:** `src/contract/java/pl/workshop/qa/SlotContractTest.java`, `src/main/resources/slots.json`, `SPEC.md` AC-01, `SETUP.md` sekcja przechwycenia HTTP. **Polecenie:**

```text
mvn clean -Pcontract -Dtest=SlotContractTest test
```

**Baza:** 2 testy kontraktu odczytu, prawdziwy HTTP bez mocków. Pierwsze użycie profilu wymaga zatwierdzonego pobrania bibliotek. **Granice:** lokalny adres loopback; brak realnych usług, tokenów i zmyślonych odpowiedzi. **Bez instalacji/kredytów:** `evidence/slots.synthetic.json` służy do propozycji asercji; nie nazywaj go przechwyceniem ani gotowym mockiem.

**Pełny prompt:**

```text
Przeczytaj SPEC.md AC-01, pom.xml, SlotContractTest.java i slots.json. Ustal, co faktycznie sprawdza REST Assured, a co tylko mały smoke API. Jeśli narzędzia są dostępne, uruchom dwa testy kontraktu. Potem uruchom fixture zgodnie z SETUP.md na loopback i zapisz świeżą odpowiedź GET /api/slots: status, wszystkie nagłówki i niezmienione body. Do metadanych raportu dopisz datę, dokładne żądanie, port, wersję JDK i fixture bez poświadczeń. Zakończ proces po przechwyceniu. Nie twórz mocka na podstawie dokumentacji ani pliku syntetycznego; bez prawdziwego bezpiecznego capture zgłoś brak warunku. Zaproponuj i dodaj jedną asercję semantyczną kontraktu, której jeszcze nie ma, np. format startu lub typ remaining, uzasadnioną SPEC.md i capture. Nie kopiuj całej implementacji do oczekiwanego JSON i nie akceptuj dowolnego statusu. Sprawdź dokumentację dokładnie wybranej wersji. Jeżeli kryterium już przechodzi, raportuj istniejące pokrycie. Bez runtime dostarcz analizę i propozycję testu, bez deklaracji wykonania. Nie dodawaj Spring Boot do projektu, który go nie używa, i nie łącz się z systemami klienta.
```

## COI-QA-05 v1.0: mały pokaz TDD, zapis jednej rezerwacji

**Czas:** 35 minut, po wyborze przez grupę; nie jest warunkiem pozostałych ćwiczeń. **Efekt:** jeden pionowy wycinek AC-05 z Red -> Green -> Refactor. **Start:** `SPEC.md` AC-05, `ApiSmokeTest.java`, `FixtureServer.java`, `AppointmentPolicy.java`. Utwórz własny `src/test/java/pl/workshop/qa/ReservationWriteTest.java`. **Polecenie po napisaniu testu:**

```text
mvn clean -Dtest=ReservationWriteTest test
```

**Baza przed zmianą:** `mvn clean test` ma 12 testów; nowa klasa nie istnieje. Nowy test powinien wykazać **501 zamiast 201**, a nie błąd kompilacji lub połączenia. **Granice:** tylko jeden syntetyczny sukces AC-05, bez całego produktu i bez deklarowania AC-06/08 jako gotowych. **Bez instalacji/kredytów:** zapisz scenariusz, proponowany test i przewidywaną obserwację; nie nazywaj przewidywania Red z wykonania.

**Pełny prompt:**

```text
Realizujemy wyłącznie jeden sukces AC-05 z SPEC.md startera Java QA. Nie budujemy nowej aplikacji w app/ ani całej platformy wizyt. Przeczytaj FixtureServer.java, AppointmentPolicy.java i ApiSmokeTest.java. Najpierw potwierdź bazę. Utwórz ReservationWriteTest.java: uruchom własny FixtureServer na porcie 0, wyślij prawdziwy POST application/x-www-form-urlencoded slotId=SYN-01&partySize=1, sprawdź 201 i pola odpowiedzi zgodne z AC-05, następnie GET listy i zmianę remaining z 2 na 1. Zamknij fixture również przy błędzie. Uruchom nowy test przed zmianą produkcji i pokaż oczekiwaną porażkę 501 vs 201. Brak runtime albo błędy kompilacji nie są Red zachowania. Dopiero po tym dodaj minimalny stan i obsługę dla tego wycinka, używając istniejącej polityki bez osłabiania testów. Uruchom nowy test i pełną bazę; refaktoruj tylko przy zielonych testach. Pokaż dokładny diff, wyniki i ograniczenia. Nie udawaj obsługi błędów AC-06, współbieżności ani kompletnego UI AC-08. Jeżeli dotykasz UI, wykonaj prawdziwy przepływ, zapisz PNG i sprawdź konsolę. Bez JDK/Maven zatrzymaj się na propozycji testu i raporcie braku wykonania; nie instaluj narzędzi i nie publikuj zmian.
```

## COI-QA-06 v1.0: Selenium, Playwright i eksploracja agenta

**Czas:** 30 minut. **Efekt:** porównanie na jednym przepływie, lista rzeczywistych obserwacji i granic. **Start:** `src/main/resources/index.html`, `SlotsBrowserTest.java`, `evidence/slots-dom.synthetic.html`, `SOURCES.md`. Po działającym `mvn clean test` polecenie startu:

```text
java -cp target/classes pl.workshop.qa.FixtureServer 8087
```

**Baza:** dwa terminy, jeden dostępny, zapis 501. Selenium jest głównym stosem testów. Playwright Java to alternatywna biblioteka; Playwright CLI to narzędzie eksploracji dla agenta, nie zamiennik testów JUnit. **Granice:** nie dodajemy drugiego frameworka Maven, nie instalujemy przeglądarek, nie wnioskujemy o natywnym Androidzie. **Bez instalacji/kredytów:** porównaj DOM i syntetyczny zapis obserwacji; raport bez rzeczywistego sterowania.

**Pełny prompt:**

```text
Porównaj Selenium Java z Playwright Java na istniejącym przepływie wyboru SYN-01, bez migracji testów i bez dodawania zależności. Przeczytaj index.html, SlotsBrowserTest.java, SPEC.md AC-07/08 oraz SOURCES.md. Podaj różnice w lokatorach, czekaniu, asercjach i artefaktach diagnostycznych, korzystając z aktualnych oficjalnych źródeł. Nie generuj przykładów TypeScript dla projektu Java. Oddziel bibliotekę Playwright Java od Playwright CLI używanego przez agenta do eksploracji przeglądarki. Jeżeli prowadzący udostępnił zatwierdzone narzędzie browser-control, najpierw sprawdź jego istniejącą wersję i pomoc, potem na działającym lokalnym fixture: otwórz stronę, zaczekaj na Gotowe, wybierz SYN-01, sprawdź niedostępność SYN-02, wyślij formularz, zapisz komunikat o niegotowym zapisie, PNG i błędy konsoli/sieci. Oczekiwany POST 501 to jawny brak funkcji, nie przypadkowy błąd Selenium. Zakończ fixture po zadaniu. Nie instaluj narzędzi ani browserów i nie deklaruj wykonania, jeśli widzisz tylko syntetyczny DOM. Browser działa na HTML DOM; nie potwierdza zachowania natywnej Activity, uprawnień Android ani Appium.
```

## COI-QA-07 v1.0: Android bez urządzenia

**Czas:** 25 minut. **Efekt:** 3 hipotezy z dowodami, propozycja lokatorów i warunki przyszłego testu. **Start:** [Android: opcje](../../Research/android-testing-options-2026-10.md), `evidence/android-hierarchy.synthetic.xml`, `evidence/android-ci.synthetic.log`. **Polecenie odczytu PowerShell:**

```powershell
Get-Content evidence/android-ci.synthetic.log
```

Linux:

```bash
cat evidence/android-ci.synthetic.log
```

**Baza:** nie ma urządzenia, APK, Appium ani sesji. Materiały są syntetyczne i nie są dowodem wykonania natywnego testu. **Granice:** bez emulatorów, provisioningu, ADB w sieci i płatnych sesji. **Bez instalacji/kredytów:** to pełnoprawna analiza plikowa, możliwa ręcznie.

**Pełny prompt:**

```text
Pracujemy bez telefonu i emulatora. Przeczytaj tylko android-hierarchy.synthetic.xml, android-ci.synthetic.log i ../../Research/android-testing-options-2026-10.md. Są to dowody syntetyczne. Wskaż element pola liczby osób, przycisku rezerwacji i komunikatu w natywnym drzewie. Zaproponuj stabilne lokatory Appium po resource-id lub accessibility id, uzasadniając je wyłącznie istniejącymi atrybutami; nie używaj CSS dla NATIVE_APP. Oddziel błędną strategię lokatora, problem sesji i brak warunku oczekiwania. Dla każdej hipotezy podaj linię dowodu i dodatkowy artefakt potrzebny do potwierdzenia. Naszkicuj odpowiedzialności Page Object Java, bez twierdzenia, że go uruchomiono. Wyjaśnij, dlaczego kliknięcie strumienia emulatora w przeglądarce ani mobilny viewport Playwright nie dowodzą automatyzacji natywnej. Zapisz warunki przyszłego testu: zatwierdzony APK, driver i wersje, urządzenie, izolacja, reset, bezpieczne połączenie i artefakty. Nie instaluj niczego, nie wystawiaj ADB/Appium i nie uruchamiaj płatnej usługi. Bez modelu ten sam raport przygotuj ręcznie.
```

## COI-QA-08 v1.0: review słabego testu i triage CI

**Czas:** 30 minut. **Efekt:** review z konkretnym scenariuszem wykrywanego błędu i klasyfikacja 3 niepowodzeń. **Start:** `evidence/weak-test.synthetic.diff`, `evidence/flaky-ci.synthetic.log`, `evidence/triage-ci.synthetic.log`, `AppointmentPolicyTest.java`, `ApiSmokeTest.java`. **Polecenie bazowe po usunięciu klas opcjonalnych profili:**

```text
mvn clean test
```

**Baza:** 12 testów. Dostarczone logi nie pochodzą z CI klienta, vendor pozostaje nieokreślony. Diff jest jawnie słabą propozycją do odrzucenia lub korekty, nie zmianą zastosowaną w kodzie. **Granice:** odczyt diffu; bez stosowania patcha, bez wyłączania testów i wymyślonej naprawy CI. **Bez instalacji/kredytów:** review na plikach i raport hipotez.

**Pełny prompt:**

```text
Wykonaj review wyłącznie na podstawie SPEC.md, AppointmentPolicyTest.java, ApiSmokeTest.java oraz evidence/weak-test.synthetic.diff i dwóch logów flaky-ci.synthetic.log, triage-ci.synthetic.log. Nie stosuj patcha. Dla każdego istotnego osłabienia testu podaj lokalizację, konkretną wadę produktu, którą przestałby wykrywać, i minimalną poprawkę lub odrzucenie. Nie oceniaj stylu bez wpływu na zachowanie. Potem sklasyfikuj zdarzenia z logów: brak zależności/środowiska, problem synchronizacji/lokatora i rzeczywiste brakujące zachowanie. Oddziel fakty od hipotez i poproś o konkretny brakujący artefakt zamiast zgadywać dostawcę CI lub jego konfigurację. Dowody są syntetyczne, więc nie wnioskuj o realnej częstości flaky ani o metrykach COI. Nie dodawaj retry, catch ignorującego porażkę, @Disabled ani tolerancji dowolnego statusu. Jeśli środowisko jest gotowe, porównaj z bazowym mvn clean test i raportuj rzeczywiste odkrycie/wykonanie/pominięcia. Bez runnera zaznacz brak wykonania. Nie publikuj review w zewnętrznej usłudze.
```

## COI-QA-09 v1.0: on-prem Jira/Bitbucket, wynik najpierw w pliku

**Czas:** 25 minut. **Efekt:** lokalny szkic wyniku review i lista warunków zgodności REST, bez wysyłki. **Start:** `integrations/README.md`, `integrations/compatibility-checklist.md`, `integrations/findings.synthetic.json`, `integrations/adapter-contract.example.json`. **Polecenie odczytu PowerShell:**

```powershell
Get-Content integrations/adapter-contract.example.json
```

Linux:

```bash
cat integrations/adapter-contract.example.json
```

**Baza:** łącznik jest nieaktywną specyfikacją, `transport=file`, `enabled=false`, bez adresu, wersji REST i tokenu. **Granice:** plik lokalny, bez HTTP, sekretów, realnych ticketów, zgadywania API lub MCP. **Bez instalacji/kredytów:** przygotuj treść komentarza i mapowanie ręcznie.

**Pełny prompt:**

```text
Zaprojektuj plikowy wynik review dla starszych on-prem Jira/Bitbucket bez łączenia się z nimi. Przeczytaj tylko integrations/README.md, compatibility-checklist.md, findings.synthetic.json i adapter-contract.example.json. Nie zgaduj endpointu, wersji API, schematu komentarza, mechanizmu uwierzytelniania ani CI. Przekształć syntetyczne ustalenia w propozycję lokalnego review-draft.json zgodnie z naszym własnym kontraktem plikowym: ticketRef, sourceFile, findings z criterionId, severity, evidence, recommendation; dodaj dryRun=true i deliveryStatus=NOT_SENT. To nie jest request body API Atlassian. Wskaż pola wymagające mapowania dopiero po uzyskaniu zatwierdzonej dokumentacji konkretnej wersji. Przedstaw przepływ przyszłego adaptera: plik -> walidacja -> ręczny przegląd -> osobno zatwierdzona wysyłka, z idempotencją i obsługą 401/403, 404, 409 oraz 429 tylko jeśli dana instalacja je definiuje. Nie zakładaj hosted Jenkins, cloud agenta ani MCP. Nie twórz skryptu wysyłającego, nie szukaj sekretów i nie wykonuj żądań. Bez modelu opracuj ten sam szkic ręcznie.
```

## COI-QA-10 v1.0: powtarzalna instrukcja i retrospektywa

**Czas:** 20 minut. **Efekt:** mała, przenośna instrukcja oceny dowodów, bez automatycznej aktywacji. **Start:** własny wypełniony raport, `REPORT-TEMPLATE.md`, `SPEC.md`, root `.github/copilot-instructions.md`. **Polecenie przeglądu własnych zmian:**

```text
git diff --stat
```

**Baza:** brak własnego aktywnego skilla/agenta. **Granice:** propozycja instrukcji jako zwykły dokument, bez kopiowania do katalogów aktywnych i bez zmiany narzędzi. **Bez instalacji/kredytów:** krótka checklista ręczna na tym samym raporcie.

**Pełny prompt:**

```text
Na podstawie mojego raportu z jednego ćwiczenia i REPORT-TEMPLATE.md zaproponuj krótką instrukcję powtarzalnej oceny dowodów QA. Ma rozróżniać testy wykonane, analizę plików syntetycznych, istniejące pokrycie, Red zachowania i brak środowiska. Podaj wejścia, kroki, wynik i warunki zatrzymania; uwzględnij zakaz osłabiania testów i prywatnych danych. Przetestuj instrukcję myślowo na jednym raporcie z brakującymi wynikami oraz jednym z prawdziwym capture, nie dopisując brakujących faktów. Zapisz propozycję jako zwykły tekst do przeglądu. Nie instaluj skilla, nie twórz aktywnego profilu agenta ani hooka, nie wybieraj providera i nie zmieniaj konfiguracji repozytorium. Nazwij jedną rzecz, którą warto stosować w pracy jutro, oraz jedno ograniczenie wymagające decyzji zespołu. Nie publikuj dokumentu.
```
