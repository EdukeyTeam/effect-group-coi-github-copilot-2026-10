# Triage nieudanych testów: model decyzyjny

Nocny przebieg w Jenkinsie kończy się na czerwono. Zanim ktokolwiek założy błąd w Jira, trzeba ustalić, czy zawiniła aplikacja, test, środowisko czy dane. To powtarzalna decyzja z ustaloną listą odpowiedzi: dobre zadanie dla modelu, pod warunkiem że kategorie, sygnały i format wyniku ustala człowiek.

W katalogu [`run-artifacts/`](run-artifacts/) są prawdziwe wyniki przebiegu zestawu `triage-demo` z projektu [`dolibarr-selenium`](../black-box-web/dolibarr-selenium/): `testng-results.xml`, raporty Surefire oraz dowody z każdej porażki (zrzut ekranu, HTML strony, adres, `failure.json`). Cztery testy padły z czterech różnych powodów.

## Kategorie

Te same nazwy stosuje ReportPortal, więc wyniki da się później przenieść do narzędzia.

| Kategoria | Kiedy | Typowe sygnały | Co dalej |
|---|---|---|---|
| Product Bug | Aplikacja działa niezgodnie z wymaganiem | Asercja na treści lub stanie zgodnym z kryterium akceptacji, powtarzalne ręcznie | Błąd w Jira dla zespołu developerskiego, z dowodami |
| Automation Bug | Zawiódł kod testu | `NoSuchElementException`, timeout na lokatorze, zła wyrocznia w teście, brak synchronizacji | Poprawka testu, bez zgłoszenia do developerów |
| System Issue | Zawiodło środowisko | DNS, `ERR_CONNECTION_*`, 502/503, wygasły certyfikat, brak przeglądarki lub drivera | Zgłoszenie do utrzymania środowiska, ponowne uruchomienie |
| No Defect | Wynik oczekiwany lub znany | Test wyłączony, znany i zaakceptowany wyjątek | Zamknięcie z uzasadnieniem |
| To Investigate | Sygnały się wykluczają albo brak danych | Wymaganie niejasne, zachowanie zależy od konfiguracji | Pytanie do analityka, nie błąd |

## Zadanie (35 min)

1. Otwórz `run-artifacts/` w IntelliJ. Zacznij od samych wyników, jak po nocnym przebiegu w Jenkinsie. Kod testów to Wasz kod, więc możesz do niego zajrzeć ([TriageDemoTest.java](../black-box-web/dolibarr-selenium/src/test/java/pl/kurs/qa/dolibarr/triage/TriageDemoTest.java)). Kodu aplikacji nie masz.
2. Użyj promptu **TRIAGE-01** w Copilot Chat (tryb Ask albo Agent). Dołącz katalog z artefaktami i kod testów.
3. Dla każdej porażki sprawdź uzasadnienie modelu na zrzucie ekranu i w `page.html`. Popraw kategorię, jeśli dowód jej nie wspiera.
4. Dla jedynego zgłoszenia, które ma trafić do developerów, użyj **TRIAGE-02**: zgłoszenie po polsku w formacie Jira.
5. Porównaj z sąsiadem: gdzie Wasze kategorie się różnią i jakiej informacji zabrakło, żeby rozstrzygnąć.

Ta sama praca działa w Microsoft 365 Copilot: wgraj `testng-results.xml` i pliki `failure.json`, wklej TRIAGE-01. Nie wgrywaj logów z prawdziwych systemów bez zgody bezpieczeństwa.

## Prompt TRIAGE-01

```text
Jesteś analitykiem triage testów automatycznych. Pracujesz tylko na załączonych artefaktach przebiegu TestNG/Selenium. Nie masz kodu aplikacji.

Dla każdego testu, który nie przeszedł:
1. Wypisz identyfikator testu i opis z TestNG.
2. Zbierz sygnały: klasa wyjątku, pierwsza linia komunikatu, adres i tytuł strony z failure.json, co widać na zrzucie ekranu i w page.html.
3. Przypisz dokładnie jedną kategorię: Product Bug, Automation Bug, System Issue, No Defect, To Investigate.
4. Uzasadnij w maksymalnie 2 zdaniach, wskazując konkretny sygnał i plik.
5. Podaj pewność: wysoka, średnia, niska. Przy niskiej wybierz To Investigate i napisz, jakiej informacji brakuje.
6. Zaproponuj następny krok i adresata: zespół testów, developerzy, utrzymanie środowiska albo analityk.

Zasady:
- Nie zgaduj. Jeśli artefakt czegoś nie pokazuje, napisz "brak w dowodach".
- Kryterium akceptacji z opisu testu traktuj jako wymaganie, ale sprawdź, czy rozbieżność nie wynika z konfiguracji środowiska.
- Nie proponuj zmiany asercji tylko po to, żeby test przeszedł.

Wynik: tabela Markdown (test, kategoria, pewność, sygnał, plik dowodu, następny krok), a pod nią JSON:
[{"test": "...", "category": "...", "confidence": "high|medium|low", "evidence": ["..."], "next_step": "...", "owner": "..."}]
```

## Prompt TRIAGE-02

```text
Na podstawie triage przygotuj zgłoszenie błędu do Jira po polsku dla testu sklasyfikowanego jako Product Bug albo To Investigate.

Pola: Tytuł (maks. 80 znaków, opisuje objaw, nie przyczynę), Środowisko (adres, przeglądarka, wersja aplikacji z dowodów), Kroki odtworzenia (numerowane, możliwe do wykonania ręcznie), Wynik oczekiwany (z kryterium akceptacji), Wynik rzeczywisty (cytat z dowodu), Dowody (nazwy plików), Priorytet z uzasadnieniem, Pytania otwarte.

Nie dopisuj przyczyny w kodzie aplikacji: nie znamy kodu. Jeśli to może być konfiguracja środowiska, napisz to w Pytaniach otwartych.
```

## Rozszerzenie: narzędzie z Copilotem (praca własna)

Część deterministyczną zrób kodem, ocenę zostaw modelowi i człowiekowi. Poproś Copilota w trybie Agent o małą klasę Java w osobnym module, która:

- czyta `testng-results.xml` i katalogi `evidence/`,
- grupuje porażki po klasie wyjątku i znormalizowanym komunikacie (bez liczb, identyfikatorów i adresów),
- wypisuje tabelę Markdown z kolumną "kategoria" do uzupełnienia,
- stosuje proste reguły dla oczywistych przypadków (np. `ERR_NAME_NOT_RESOLVED` to System Issue) i resztę oznacza jako To Investigate.

Reguły trzymaj w pliku, nie w kodzie. Tak powstaje pierwszy, sprawdzalny model decyzyjny, który potem można zastąpić ReportPortalem albo modelem uruchomionym lokalnie.
