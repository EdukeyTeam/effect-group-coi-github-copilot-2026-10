# Przypadki testowe: noty kosztowe w Dolibarr

Tak dostajesz zadanie w Jira: adres środowiska, konto testowe i przypadki. Bez kodu aplikacji i bez dokumentacji technicznej.

- Środowisko: publiczne demo Dolibarr 24 (`https://demo.dolibarr.org`), profil "DemoCompanyServiceOnly".
- Konto: `demo` / `demo`. Formularz logowania podpowiada inne hasło: to celowa pułapka danych testowych.
- Środowisko jest współdzielone z innymi osobami z całego świata. Dane zmieniają się w trakcie dnia.
- Dane izolujemy datą: każda osoba używa własnego okresu noty. Numer uczestnika `NN` (01-10) dostajesz od prowadzącego. Data noty: `NN` dzień marca 2031, np. uczestnik 07 używa `03/07/2031` (format daty w demo: MM/DD/YYYY).
- W opisie każdej utworzonej noty wpisz `COI-NN-<znacznik czasu>`, żeby odnaleźć własne dane.

| ID | Tytuł | Warunki wstępne | Kroki | Oczekiwany wynik |
|---|---|---|---|---|
| TC-WEB-01 | Logowanie poprawnymi danymi | Strona wyboru profilu demo | 1. Wybierz profil DemoCompanyServiceOnly. 2. Wpisz `demo` / `demo`. 3. Kliknij Login. | Strona główna, tytuł zaczyna się od "Home". Widać zalogowanego użytkownika. |
| TC-WEB-02 | Logowanie błędnym hasłem | Strona logowania | 1. Zostaw podpowiedziane hasło. 2. Kliknij Login. | Komunikat "Bad value for login or password". Brak dostępu do menu. |
| TC-WEB-03 | Nota bez daty | Zalogowany użytkownik | 1. HRM, Expense reports, New. 2. Nie wypełniaj dat. 3. Create expense report. | Nota nie powstaje. Komunikat "Field 'Date' is required". |
| TC-WEB-04 | Utworzenie szkicu noty | Zalogowany użytkownik, własna data | 1. Nowa nota, data od i do = własna data. 2. Create expense report. | Nota w statusie "Draft". Okres "From <data> to <data>". Kwoty 0.00. |
| TC-WEB-05 | Nakładający się okres | Istnieje Twoja nota z TC-WEB-04 | 1. Utwórz drugą notę z tym samym okresem. | Druga nota nie powstaje. Komunikat o innej nocie w podobnym okresie i link do niej. |
| TC-WEB-06 | Pozycja kosztu z VAT | Szkic z TC-WEB-04 | 1. Dodaj pozycję: dowolny typ, opis `COI-NN`, VAT 20%, cena netto 100, ilość 2. 2. Zapisz pozycję. | Amount (excl. tax) 200.00, Amount tax 40.00, Amount (inc. tax) 240.00. |
| TC-WEB-07 | Zatwierdzenie do akceptacji | Szkic z co najmniej jedną pozycją | 1. Validate. 2. Potwierdź. | Status zmienia się z "Draft" na status oczekiwania na akceptację. Numer noty nie jest już tymczasowy. |
| TC-WEB-08 | Nota bez pozycji | Pusty szkic | 1. Spróbuj zatwierdzić notę bez pozycji. | Do ustalenia z analitykiem: zapisz faktyczne zachowanie i zgłoś pytanie. |

TC-WEB-01 i TC-WEB-02 są już zautomatyzowane w projekcie startowym. Pozostałe automatyzujesz z Copilotem.
