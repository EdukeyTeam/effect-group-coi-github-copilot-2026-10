# **Program szkolenia**

Dwa dni pracy warsztatowej, około **6 godzin szkoleniowych dziennie**, na jednym projekcie prowadzonym od początku do końca w ciągu 2 dni. Trzeci dzień jest możliwy jako rozszerzenie o moduły dodatkowe, opisane na końcu programu.

# **Dzień 1: fundamenty na codziennym stacku grupy**

Moduły 1.1-1.6: kontekst projektu dla agenta, scenariusze testowe z wymagań, Selenium z agentem, testy integracyjne API i system pracy z wynikami.

## **1.1 Start w Copilocie: wtyczka IntelliJ, Copilot CLI i mapa narzędzi \| ok. 60 min**

* agenci a autocomplete: co zmienia się w codziennej pracy testera,  
* punkt startu: wtyczka Copilot w IntelliJ, czyli tam, gdzie już pracujecie; obok niej Copilot CLI z większymi możliwościami,  
* to samo zadanie w obu interfejsach: gdzie kończy się podpowiedź, a zaczyna agent; kiedy z czego korzystać,  
* demo prowadzącego na tym samym zadaniu: Claude Code i Codex CLI, żeby zobaczyć pełnię możliwości chmurowych,  
* mapa opcji na później: JetBrains AI Assistant i Junie, OpenCode z modelem self-hosted, decyzja co przechodzi za airlockiem; szczegóły według decyzji organizacji,  
* co z dostępności poszczególnych funkcji wynika dla Was: mapowanie na obecną wersję licencji i na wersję na czas szkolenia (tabela ustalana po ankiecie),  
* Ćwiczenie: to samo zadanie testowe wykonane we wtyczce Copilot i w Copilot CLI, z porównaniem przebiegu i wyniku.

## **1.2 Kontekst projektu: repozytorium testów, które agent rozumie \| ok. 45 min**

* jak agent czyta repozytorium i gdzie traci kontekst: struktura, zależności, sposób uruchamiania testów,  
* AGENTS.md i .github/copilot-instructions.md dla repozytorium testów: stack, komendy, konwencje nazewnicze, Definition of Done dla testu,  
* instrukcje ścieżkowe: inne zasady dla testów Selenium, API i aplikacji mobilnej,  
* co psuje jakość generowanych testów: brak konwencji selektorów, przypadkowe dane testowe, brak wzorców asercji,  
* Ćwiczenie: uzupełnienie szablonu AGENTS.md z lukami w projekcie warsztatowym i sprawdzenie, jak zmienia się jakość poleceń agenta.

## **1.3 Od wymagań do scenariuszy testowych generowanych przez agenta \| ok. 55 min**

* agent jako analityk: pytania doprecyzowujące zamiast zgadywania brakujących reguł,  
* generowanie przypadków pozytywnych, negatywnych i brzegowych oraz klas równoważności na przykładzie historii użytkownika,  
* testy behawioralne w formacie Given/When/Then i ich związek z automatyzacją później,  
* traceability: wymaganie, scenariusz, test; docelowy format dopasowany po ankiecie do Waszej Jira (domyślnie tabela z krokami i oczekiwanym wynikiem, gotowa do wklejenia),  
* człowiek jako walidator: przegląd scenariuszy agenta, odrzucanie bezwartościowych i uzupełnianie braków,  
* Ćwiczenie: agent generuje zestaw scenariuszy dla historii z projektu warsztatowego, uczestnicy go walidują i przygotowują w formacie z ankiety; bez importu na żywo do Jira klienta.

## **1.4 Selenium z agentem: flaky testy, lokatory i Page Object Model \| ok. 70 min**

* Selenium jako codzienny stack grupy: jak agent pisze, naprawia i rozbudowuje istniejący zestaw testów,  
* agent jako serwisant: przygotowany flaky test, hipotezy agenta, poprawka waitów albo lokatora i raport przyczyny,  
* strategia lokatorów, którą agent przestrzega z konwencji projektu: stabilne identyfikatory, dostępność, zakaz kruchych xpath,  
* Page Object Model utrzymywany przez agenta: refaktor krok po kroku z kontrolą,  
* Ćwiczenie: naprawa flaky testu Selenium w projekcie warsztatowym z hipotezami i raportem przyczyny gotowym do Jira.

## **1.5 Testy integracyjne API pisane z agentem \| ok. 55 min**

* REST Assured i testy Spring Boot: jak agent buduje testy na kontraktach API, a nie na implementacji,  
* dane testowe i stan aplikacji: przygotowanie skryptem, izolacja i powtarzalność uruchomień,  
* różnica między monolitem a mikroserwisami dla testów integracyjnych i jak agent dostosowuje podejście,  
* ocena wartości testu: które testy agenta zostają, które wymagają poprawki, a które wylatują,  
* Ćwiczenie: zestaw testów API dla jednego endpointu aplikacji warsztatowej, z walidacją kontraktów i review.

## **1.6 System pracy z agentami: repozytorium, skills, wyniki testów \| ok. 45 min**

* struktura repozytorium testów przygotowana pod agentów: konwencje, biblioteka promptów, szablony,  
* skills i komendy wielokrotnego użytku: powtarzalne zadania testera jako gotowe procedury dla agenta,  
* zarządzanie wynikami: raporty przebiegów, dowody (zrzuty, logi), wersjonowanie i miejsce przechowywania,  
* od raportu do zadania: jak wyniki pracy agenta trafiają do Jira jako tickety z dowodami,  
* Ćwiczenie: zebranie pracy z pierwszego dnia w powtarzalny workflow: własny skill albo szablon raportu i reguły zapisu wyników.

# **Dzień 2: przeglądarka, urządzenie mobilne i proces**

Moduły 2.1-2.8: TDD w wersji testerskiej, Selenium i Playwright, testy mobilne bez własnego emulatora, code review, CI/CD i integracja z on-prem Bitbucket, Jira i Confluence.

## **2.1 TDD w wersji testerskiej: padający test jako specyfikacja \| ok. 50 min**

* rekapitulacja dnia pierwszego i kontrola stanu projektu (10 minut),  
* cykl TDD z agentem: najpierw test, który pada, opisujący regułę; potem implementacja albo poprawka w istniejącym kodzie,  
* najczęstsza pułapka: agent zmienia asercję albo test, żeby zrobić się zielono; jak temu zapobiegać i jak to wykrywać przez celowe zepsucie kodu,  
* gdzie testy jednostkowe mają sens w pracy testera, a gdzie to materiał dewelopera; testy charakteryzujące wymieniamy jako narzędzie przed zmianą w legacy,  
* Ćwiczenie: cykl od padającego testu do zielonego na wybranej regule projektu warsztatowego, z review wygenerowanych testów.

## **2.2 Selenium czy Playwright: agenci w przeglądarce \| ok. 70 min**

* porównanie pod kątem agentów: co agenci znają najlepiej, selektory, auto-waits, generowanie kodu, trace i debugowanie,  
* Playwright jako narzędzie agenta: codegen (także w Javie), uruchamianie scenariuszy i eksploracja aplikacji,  
* agent zamiast klikania: eksploracyjne testy przez Playwright MCP, w których agent przechodzi aplikację jak tester manualny i raportuje znaleziska z dowodami,  
* decyzja projektowa: kiedy zostać przy Selenium, a kiedy budować nowy zestaw w Playwright; konwersja testu jako pokaz możliwości,  
* Ćwiczenie: eksploracyjne testy prowadzone przez agenta na aplikacji warsztatowej i raport błędów gotowy do Jira.

## **2.3 Android: testy mobilne z agentem bez własnego emulatora \| ok. 50 min**

* aplikacja warsztatowa uruchomiona w emulatorze w chmurze: podgląd i eksploracja przez przeglądarkę, którą agent może sterować jak tester manualny,  
* agent pisze test mobilny (Appium albo test instrumentacyjny, według przygotowanego środowiska) i uruchamia go na jednym urządzeniu przygotowanym przez prowadzącego: fizycznym, w emulatorze albo na urządzeniu w chmurze Google,  
* diagnostyka: logi, zrzuty ekranu i stan urządzenia jako część pracy agenta,  
* opcja bez własnego urządzenia: uruchomienie testu w chmurze testowej Google, z raportem i dowodami,  
* uczciwie o granicach: agent pisze testy mobilne jak w Selenium, ale mobilnego odpowiednika eksploracyjnych sesji z Playwright MCP dziś nie ma,  
* plan B na wypadek kłopotów z urządzeniem: recenzja gotowego testu i logów na sucho, żeby awaria urządzenia nie zatrzymała grupy,  
* Ćwiczenie: uruchomienie testu mobilnego wygenerowanego przez agenta na przygotowanym urządzeniu, ze zbiorem dowodów i poprawką wskazaną przez agenta.

## **2.4 Perspektywa developera: agent buduje funkcję aplikacji \| ok. 15 min**

* demo: od wymagania do działającej funkcji w aplikacji warsztatowej, z testami z cyklu TDD,  
* gdzie w tym procesie powstają błędy, które potem łapiecie jako testerzy, i jak to zmienia rozmowę z developerami o defectach.

## **2.5 Code review: Copilot na GitHubie (demo) i własny agent na diffie \| ok. 45 min**

* Copilot code review dla pull requestów w przeglądarce i w IntelliJ: co łapie, a czego nie zobaczy; uczciwie o tym, że to funkcja ekosystemu GitHub,  
* własny agent-reviewer na diffie: checklisty dla testów (asercje, brzegi, dane, sekrety, sleep, selektory), uruchamiany gdziekolwiek leży repozytorium,  
* przygotowany pull request w projekcie warsztatowym z celowo słabym testem: review od Copilota i od własnego agenta, uczestnik rozstrzyga uwagi obu,  
* granice zaufania: review AI jako filtr wstępny, a nie zamiast człowieka; kto podejmuje decyzję i jak ją udokumentować,  
* most do modułu on-prem: ten sam agent komentuje zmiany przez REST API Waszego Bitbucket,  
* Ćwiczenie: przegląd gotowego diffu dwoma recenzentami AI i decyzja uczestnika z uzasadnieniem.

## **2.6 CI/CD z perspektywy testera: bramki, triage, agenci w pipeline \| ok. 45 min**

* testy jako bramka jakości: co musi być deterministyczne, a gdzie agent ma nic nie robić,  
* agent jako triage czerwonego builda: gotowy log z pipeline zamieniany w czytelne podsumowanie z listą priorytetów napraw,  
* jeden przykład pipeline dopasowany po ankiecie (Jenkins albo Bitbucket Pipelines): agent dopisuje krok uruchomienia testów i raportu do szablonu, a nie projektuje CI od zera,  
* agent w trybie nieinteraktywnym w pipeline: jak to się w ogóle odpala; uprawnienia, tokeny, limity (krótko, bez ćwiczenia),  
* Ćwiczenie: triage przygotowanego czerwonego builda z agentem i dopisanie brakującego kroku testów do szablonu pipeline.

## **2.7 On-prem bez MCP: Bitbucket, Jira i Confluence przez REST API \| ok. 50 min**

* diagnoza: stare wersje on-prem bez oficjalnych integracji MCP i co z tego faktycznie wynika,  
* ścieżka praktyczna: personal access token, REST API i skill z własnymi skryptami, którymi agent obsługuje Bitbucket, Jira i Confluence,  
* czego wymaga ta ścieżka: wersje API, zakresy uprawnień tokenu, zgody bezpieczeństwa; lista pytań do sprawdzenia w Waszym środowisku przed wdrożeniem,  
* kiedy warto sięgnąć po własny serwer MCP i ile to kosztuje w porównaniu ze skillem (moduł dodatkowy B rozwija ten temat),  
* demo na instancji przygotowanej przez prowadzącego: agent czyta pull request, komentuje go, zakłada zadanie w Jira i zapisuje wyniki testów,  
* Ćwiczenie: konfiguracja tokenu i prostego skilla przez uczestników oraz jedna operacja agenta przez REST API.

## **2.8 Retrospektywa i plan wdrożenia w Waszym środowisku \| ok. 30 min**

* mapa możliwości: co działa od jutra na obecnej wersji licencji, a co czeka na Business, zgody albo modele lokalne,  
* cel efektywności postawiony na spotkaniu (ok. 30 procent): wybór jednego wskaźnika (czas powstawania testu, czas triage czerwonego builda, czas przygotowania scenariuszy) i pomiar w następnym sprincie,  
* plan wdrożenia na pierwsze 30 dni: szybkie zyski, standardy zespołu, automatyzacja triage,  
* licencje i koszty po szkoleniu: co się opłaca utrzymywać, na co uważać w rozliczeniach,  
* materiały poszkoleniowe, follow-up i lista tematów na moduły dodatkowe,  
* Ćwiczenie: każdy uczestnik zapisuje jedną zmianę do wdrożenia od razu i jedną, która czeka na zgodę, i omawiamy wybory na forum.

