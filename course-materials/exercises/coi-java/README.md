# Java QA z GitHub Copilot

**Opcjonalny starter do świadomego wyboru.** Pracujemy na syntetycznych wizytach, nie na systemie COI. Nie trzeba tworzyć nowej aplikacji, kont ani infrastruktury. Dane są deterministyczne, a zapis rezerwacji celowo pozostaje niegotowy.

1. [Sprawdź dostęp i środowisko](SETUP.md). Gdy nie możesz uruchamiać kodu, wybierz analizę dostarczonych plików.
2. Przeczytaj [kryteria i macierz pokrycia](SPEC.md). Uruchom bazę i zapisz rzeczywisty wynik.
3. Wybierz jedno [ćwiczenie i pełny prompt](WORKSHOP.md). W IntelliJ otwórz ten katalog jako projekt Maven; w CLI podaj ten sam katalog i dokładne pliki.
4. Zapisz wynik według [szablonu raportu](REPORT-TEMPLATE.md). Oddziel dane syntetyczne od wykonanych sprawdzeń.

## Co działa, co zostaje do zrobienia

Polityka liczby osób i pojemności, odczyt dwóch terminów przez HTTP oraz prosta polska strona są bazą do testowania. POST `/api/reservations` zwraca **501 NOT_IMPLEMENTED**, a lista rezerwacji jest pusta. Ćwiczenie TDD dodaje jeden wybrany przypadek zapisu. Formularz nie udaje sukcesu i nie przechowuje danych osoby. To fixture edukacyjny, bez zabezpieczeń i kompletności wymaganych od produkcji.

| Polecenie w tym katalogu | Oczekiwana liczba testów bazowych | Zakres |
|---|---:|---|
| `mvn test` | 12, 0 pominiętych | 8 polityki + 4 prawdziwego lokalnego HTTP |
| `mvn -Dtest=AppointmentPolicyTest test` | 8, 0 pominiętych | Reguły zachowania |
| `mvn -Dtest=ApiSmokeTest test` | 4, 0 pominiętych | API bez mocków |
| `mvn -Pcontract test` | 14, 0 pominiętych | Baza + 2 REST Assured |
| `mvn -Pcontract -Dtest=SlotContractTest test` | 2, 0 pominiętych | Tylko kontrakt odczytu |
| `mvn -Pselenium test` z jawnymi ścieżkami Chrome/drivera | 14, 0 pominiętych | Baza + 2 Selenium |
| `mvn -Pcontract,selenium test` z tymi ścieżkami | 16, 0 pominiętych | Wszystkie opcjonalne zestawy |

To **oczekiwania wynikające z dostarczonych testów, nie protokół wykonania na Twoim komputerze**. Samo policzenie `@Test` nie dowodzi odkrycia przez runner. Zapisz faktyczne liczby z Surefire. Nie ma testów `@Disabled`, domyślnych wadliwych przykładów ani ukrytego omijania braku przeglądarki. Po dopisaniu własnych testów liczby wzrosną. Przy zmianie profili użyj najpierw `mvn clean`, aby usunąć skompilowane klasy poprzedniego profilu; inaczej Maven może nadal je odkryć.

`evidence/` zawiera **syntetyczne materiały do analizy**, nie raport z uruchomienia kursowego. Wadliwy kod ma rozszerzenie `.java.txt`, a słaby diff nie jest stosowany automatycznie. Żaden skrypt nie wysyła danych do Jira/Bitbucket ani nie uruchamia płatnych usług.

Wynik przygotowania i jawne ograniczenia: [sprawdzenie startera](VERIFICATION.md). Własne uruchomienie nadal zapisz osobno.

## Pliki

- `src/main/java/pl/workshop/qa/`: reguły i lokalny serwer.
- `src/main/resources/`: dwa sloty oraz prosta strona HTML.
- `src/test/java/`: baza JUnit i HTTP.
- `src/contract/java/`: profil REST Assured.
- `src/selenium/java/`: profil Selenium, systemowy Chrome i zatwierdzony ChromeDriver.
- [Integracje on-prem](integrations/README.md), [źródła i wersje](SOURCES.md), [Android bez urządzenia](../../Research/android-testing-options-2026-10.md).

Pełne repozytorium pozostaje biblioteką materiałów. Wybranie tego startera nie ustala architektury aplikacji klienta ani nie uruchamia `app/` w katalogu głównym.
