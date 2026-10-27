# Testy GUI Dolibarr: zasady dla agenta

Projekt testów automatycznych Selenium WebDriver + TestNG (Java 17, Maven) dla aplikacji Dolibarr 24 pod adresem z `TestConfig`. Nie mamy kodu aplikacji: testujemy z zewnątrz, jak użytkownik.

## Polecenia

- Wszystkie testy smoke: `./mvnw -B test` (Windows: `.\mvnw.cmd -B test`).
- Jedna klasa: `./mvnw -B test -Dtest=<Klasa>`.
- Widoczna przeglądarka: `-Ddolibarr.headless=false`.

## Konwencje

- Page Object w `pages/`: lokatory jako prywatne pola `By`, publiczne metody opisują intencję użytkownika (`loginAs`, `errorMessage`).
- Lokatory: id, name, tekst widoczny dla użytkownika, CSS. XPath tylko z uzasadnieniem w komentarzu.
- Tylko jawne oczekiwania (`WebDriverWait`). Bez `Thread.sleep` i bez implicit wait.
- Testy w `tests/`, opis testu zaczyna się od ID przypadku, np. `TC-WEB-04: ...`.
- Konfiguracja tylko przez `TestConfig` (właściwości `-D` lub zmienne środowiskowe). Bez adresów i haseł w testach.
- Środowisko jest współdzielone: każdy test tworzy własne dane z unikalnym znacznikiem i nie zależy od liczników ani danych innych osób.

## Dowody

`EvidenceListener` zapisuje przy porażce `target/evidence/<Klasa>.<metoda>/`: `screenshot.png`, `page.html`, `url.txt`, log konsoli i `failure.json`. Przy analizie porażki zaczynaj od tych plików.

## Granice

- Nie zmieniaj asercji tylko po to, żeby test przeszedł. Rozbieżność z przypadkiem testowym raportuj.
- Nie dodawaj zależności bez pytania. Nie uruchamiaj pakietu `triage` (celowo czerwone testy do ćwiczenia).
- Odpowiadaj po polsku, kod po angielsku.
