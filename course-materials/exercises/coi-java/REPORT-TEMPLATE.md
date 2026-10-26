# Raport ćwiczenia Java QA

- Ćwiczenie i kryterium: COI-QA-..., AC-...
- Data, commit/wersja własnej kopii, katalog pracy:
- Tryb: rzeczywiste wykonanie / analiza syntetycznych plików / demonstracja prowadzącego.
- Narzędzia: JDK, Maven i jego JDK, IntelliJ/wtyczka, CLI, Chrome/driver (jeżeli użyte).
- Kontekst agenta i zgody: konkretne pliki, dozwolone polecenia, co było niedostępne.
- Dane: wyłącznie syntetyczne; brak sekretów i systemów klienta.

| Kryterium | Wejście | Oczekiwany wynik | Test / plik | Faktyczny wynik i dowód |
|---|---|---|---|---|
| AC-... | ... | ... | ... | ... |

## Oddzielne sprawdzenia

| Sprawdzenie | Dokładne polecenie / kroki | Odkryto / wykonano / pominięto | Wynik / ścieżka dowodu |
|---|---|---|---|
| Kompilacja | ... | nie dotyczy | ... |
| Baza przed zmianą | ... | ... / ... / ... | ... |
| Red nowego zachowania | ... | ... / ... / ... | oczekiwany błąd asercji, nie brak środowiska |
| Green po zmianie | ... | ... / ... / ... | ... |
| Pełny zmieniony zakres | ... | ... / ... / ... | ... |
| Start aplikacji | polecenie i wypisany port | nie dotyczy | osobne potwierdzenie startu |
| Ręczna kontrola UI/API | lista kroków | nie dotyczy | PNG, status/nagłówki/body, konsola |
| Zamknięcie fixture | Ctrl+C / close w teście | nie dotyczy | brak pozostawionego procesu |

Gdy kryterium już działa, zamiast Red wpisz "istniejące pokrycie" i wskaż test. Gdy nie uruchomiono runnera, wpisz "nie wykonano", nie "0 testów zakończonych sukcesem". Artefakty z `evidence/` są syntetyczne; nie podpisuj ich jako własnego wykonania.

## Ocena

- Zaobserwowany fakt i dowód:
- Hipoteza, której jeszcze nie sprawdzono:
- Zmiana: pliki i powód, mały diff:
- Co nadal nie działa lub jest poza zakresem:
- Czy test wykrywa kontrolowaną zmianę zachowania (jeśli sprawdzano), jak ją cofnięto:
- Review: ryzyko, uwagi przyjęte/odrzucone z uzasadnieniem:
- Brakujący warunek dostępu lub narzędzie i ścieżka bez instalacji:
- Publikacja: tylko za osobnym poleceniem; raport sam nie daje zgody na push/merge.
