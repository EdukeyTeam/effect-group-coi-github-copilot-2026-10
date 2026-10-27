# Testy czarnej skrzynki: aplikacja webowa

Testujesz prawdziwą aplikację finansową bez dostępu do jej kodu: Dolibarr 24 (ERP open source, publiczne demo). Masz adres, konto i [przypadki testowe](PRZYPADKI-TESTOWE.md). Copilot pomaga zamienić przypadek w Page Object i test Selenium + TestNG, a Ty pilnujesz wyroczni, danych i dowodów.

Moduł obejmuje noty kosztowe (wydatki pracowników z akceptacją), faktury, bank i projekty. To ten sam typ systemu, który testujecie na co dzień.

## Start (10 min)

1. Sklonuj repozytorium kursu albo pobierz ZIP i otwórz w IntelliJ katalog `course-materials/exercises/black-box-web/dolibarr-selenium` jako projekt Maven.
2. Ustaw JDK 17 lub 21 dla projektu.
3. Uruchom testy startowe:
   - Windows: `.\mvnw.cmd -B test`
   - Linux/macOS: `./mvnw -B test`
   - Albo w IntelliJ: prawy klik na `testng.xml`, Run.
4. Oczekiwany wynik: `Tests run: 2, Failures: 0`. Domyślnie przeglądarka działa w tle. Żeby ją widzieć: `-Ddolibarr.headless=false`.
5. Otwórz demo w przeglądarce i przejdź ręcznie TC-WEB-04. Bez tego nie ocenisz, co wygeneruje Copilot.

Jeśli Maven nie pobiera zależności przez proxy firmowe, pracuj na kodzie bez uruchamiania i oznacz testy jako nieuruchomione.

## Jak Copilot ma zobaczyć aplikację bez kodu

Model nie zna Twojej aplikacji. Daj mu to, co widzi tester:

- przypadek testowy z Jira (kroki i oczekiwany wynik),
- fragment HTML formularza: w Chrome F12, prawy klik na elemencie `<form>` albo tabeli, Copy, Copy outerHTML,
- istniejące Page Objects i konwencje z projektu startowego,
- przy porażce: `target/evidence/<test>/page.html` i `screenshot.png`, które zapisuje listener.

Nie wklejaj HTML z systemów produkcyjnych z danymi osobowymi. W tym ćwiczeniu dane demo są publiczne.

## Ćwiczenie 1: przypadek na test (35 min)

1. Wybierz TC-WEB-03 albo TC-WEB-04. Skopiuj outerHTML formularza nowej noty.
2. Otwórz Copilot Chat w IntelliJ w trybie Agent. Wklej prompt **BB-WEB-01** z przypadkiem i HTML.
3. Przeczytaj plan agenta, zanim zatwierdzisz zmiany. Odrzuć `Thread.sleep`, długie XPath i asercje, których nie ma w przypadku.
4. Uruchom test. Jeśli padnie, daj agentowi dowody z `target/evidence/` zamiast opisu słownego.
5. Wynik: nowy Page Object, test z identyfikatorem TC w opisie, zielony przebieg albo opisany powód porażki.

## Ćwiczenie 2: dane i równoległość (20 min)

TC-WEB-05 pokazuje regułę biznesową, której nie znajdziesz w dokumentacji: dwie noty jednego użytkownika nie mogą mieć wspólnego okresu. Na współdzielonym środowisku to także pułapka dla automatów uruchamianych równolegle.

1. Uruchom swój test TC-WEB-04 drugi raz. Co się stało i dlaczego?
2. Użyj **BB-WEB-02**: niech Copilot zaproponuje strategię danych (unikalny okres, sprzątanie, użytkownik testowy) i przepisze test tak, by był powtarzalny.
3. Zautomatyzuj TC-WEB-05 jako test negatywny.

## Ćwiczenie 3: przegląd testu (15 min)

Wymień się testem z sąsiadem albo daj go drugiej sesji Copilota z promptem **BB-WEB-03**. Recenzent sprawdza lokatory, synchronizację, wyrocznię, dane i dowody. Decyzję o zmianach podejmuje autor testu.

## Ćwiczenie 4: developerzy wydają nową wersję (15 min)

Tak to wygląda w praktyce: zespół developerski wdraża nową wersję na środowisko testowe i przekazuje listę zmian. Kodu nie dostajesz. Dostajesz release notes i musisz zdecydować, co przetestować i co dopisać do regresji.

1. Otwórz prawdziwe [release notes Dolibarr](https://github.com/Dolibarr/dolibarr/blob/develop/ChangeLog), sekcję "ChangeLog for 24.0.2 compared to 24.0.1" (wersja z demo).
2. Użyj promptu **BB-WEB-04**: Copilot wybiera zmiany dotyczące not kosztowych i płatności, a Ty decydujesz o zakresie testów.
3. Wynik: 3-5 nowych przypadków w formacie z [PRZYPADKI-TESTOWE.md](PRZYPADKI-TESTOWE.md), oznaczenie, które automatyzujesz w GUI, które sprawdzasz ręcznie, a które wymagają testu API.

## Pokaz prowadzącego: agent w przeglądarce

Agent z Playwright MCP sam otwiera stronę, czyta drzewo dostępności i klika jak tester manualny. Na tym samym demo pokażemy test eksploracyjny noty kosztowej z raportem znalezisk oraz porównanie z kodem Selenium. Playwright w tym pokazie służy agentowi do eksploracji, a Wasze testy regresji zostają w Selenium.

## Prompt BB-WEB-01

```text
Pracujesz w projekcie testów Selenium + TestNG (Java) dla aplikacji, do której nie mamy kodu źródłowego. Testujemy z zewnątrz, jak użytkownik.

Najpierw przeczytaj: pom.xml, testng.xml, config/TestConfig.java, base/BaseTest.java, pages/*.java, tests/LoginTest.java. Trzymaj się ich konwencji.

Przypadek testowy z Jira:
<wklej wiersz z PRZYPADKI-TESTOWE.md>

HTML ekranu (outerHTML z przeglądarki):
<wklej>

Zadanie:
1. Zaproponuj plan: jakie Page Objects i metody, jakie lokatory (najpierw id/name, potem tekst widoczny dla użytkownika, XPath tylko z uzasadnieniem), jaka asercja odpowiada oczekiwanemu wynikowi.
2. Po mojej akceptacji dodaj Page Object w pages/ i test w tests/. Opis testu zaczyna się od identyfikatora TC.
3. Tylko jawne oczekiwania (WebDriverWait), bez Thread.sleep i bez implicit wait.
4. Dane testowe: własna data z przypadku i opis z unikalnym znacznikiem. Nie zakładaj stanu danych innych osób.
5. Uruchom tylko nowy test: mvnw -B test -Dtest=<Klasa>. Pokaż wynik.
6. Jeśli test pada, przeczytaj target/evidence/<test>/ i powiedz, czy winny jest test, dane, środowisko czy aplikacja. Nie zmieniaj asercji tylko po to, żeby test przeszedł.

Na koniec: lista zmienionych plików, wynik uruchomienia i rzeczy, których nie dało się sprawdzić.
```

## Prompt BB-WEB-02

```text
Mój test <nazwa> przechodzi raz, a drugi raz pada na współdzielonym środowisku testowym. Aplikacja blokuje dwie noty kosztowe jednego użytkownika w nakładającym się okresie. Nie mamy dostępu do bazy ani kodu aplikacji.

1. Wyjaśnij przyczynę na podstawie dowodów z target/evidence/.
2. Zaproponuj 2-3 strategie danych testowych dla środowiska, z którego korzystają też inne osoby i równoległe przebiegi (unikalny okres, sprzątanie przez UI po teście, osobny użytkownik). Dla każdej: koszt, ryzyko, co trzeba ustalić z zespołem środowiska.
3. Wdroż strategię, którą wybiorę, i dodaj test negatywny dla nakładającego się okresu.
4. Uruchom test dwa razy z rzędu i pokaż oba wyniki.
```

## Prompt BB-WEB-03

```text
Zrób przegląd testu Selenium + TestNG jako doświadczony tester automatyzujący. Nie zmieniaj plików, tylko oceń.

Sprawdź:
- czy asercje sprawdzają oczekiwany wynik z przypadku testowego, a nie tylko brak wyjątku,
- lokatory: stabilność, czytelność, zależność od układu strony,
- synchronizację: jawne oczekiwania, brak sleep, ryzyko flaky,
- dane: unikalność, sprzątanie, zależność od danych innych osób,
- dowody przy porażce i czytelność komunikatów asercji,
- zgodność z konwencją projektu (Page Object, konfiguracja, nazwy).

Wynik: tabela (problem, plik:linia, ryzyko, propozycja) posortowana od najważniejszego oraz jedno zdanie werdyktu: gotowy do repozytorium czy nie.
```

## Prompt BB-WEB-04

```text
Developerzy wdrożyli na środowisko testowe nową wersję aplikacji. Nie mam kodu ani dokumentacji technicznej, tylko release notes (wklejam poniżej) i istniejące przypadki testowe (PRZYPADKI-TESTOWE.md).

Release notes:
<wklej sekcję z listą zmian>

1. Wybierz zmiany dotyczące mojego obszaru: noty kosztowe, płatności, faktury. Dla każdej napisz jednym zdaniem, co zmienia się dla użytkownika. Jeśli z opisu nie da się tego ustalić, napisz, o co zapytać developera.
2. Oceń ryzyko regresji (wysokie, średnie, niskie) z uzasadnieniem.
3. Zaproponuj 3-5 nowych lub zmienionych przypadków testowych w formacie z PRZYPADKI-TESTOWE.md.
4. Dla każdego przypadku wskaż sposób sprawdzenia: automat GUI (Selenium), test API, test ręczny. Uzasadnij krótko.
5. Wskaż istniejące testy automatyczne, które warto uruchomić jako regresję.

Nie zgaduj zachowania, którego release notes nie opisują. Oddziel fakty z release notes od własnych hipotez.
```
