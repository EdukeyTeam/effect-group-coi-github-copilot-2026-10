# Sprawdzenie startera

Przebieg przygotowania materiałów: **26.10.2026**. Sprawdzono źródła startera na Linux x64, JDK Temurin 21.0.13+9, Maven 3.10.0 oraz dopasowanych Chrome i ChromeDriver 156.0.8078.47. Kod kompilowano z `release=17`. Nie jest to potwierdzenie konfiguracji komputerów COI ani próby na każdym obsługiwanym JDK.

| Przebieg | Odkryte i wykonane testy | Błędy / porażki / pominięcia |
|---|---:|---|
| Baza JUnit i rzeczywisty lokalny HTTP | 12 | 0 / 0 / 0 |
| Profile contract i selenium razem | 16 | 0 / 0 / 0 |

Po doprecyzowaniu asercji liczby miejsc ponownie przeszło 16 testów. Kontrolowana zmiana wyświetlanej pojemności na 9 w osobnej kopii spowodowała oczekiwaną porażkę jednego testu Selenium; starter pozostał bez tej zmiany.

Zachowano raporty Surefire i dwa zrzuty z Selenium. Osobna próba działającego UI w Chrome obejmowała listę dwóch terminów, wyłączony pełny termin, wybór SYN-01, walidację zera w formularzu oraz jawny komunikat dla niegotowego zapisu. Sprawdzono szerokości 1440 i 390 px, rzeczywiste odpowiedzi HTTP oraz brak nieobsłużonych wyjątków strony. To weryfikacja fixture, a nie zakończenie ćwiczenia implementacji.

## Komunikaty i granice

Selenium 4.38.0 przy tym Chrome zgłaszało `Unable to find CDP implementation matching 156`. Dostarczone testy używają standardowego WebDriver i przeszły. Funkcji DevTools/CDP nie sprawdzono; przed takim rozszerzeniem trzeba dobrać i zweryfikować zgodne zależności. Nie wyciszano ostrzeżenia ani nie wyłączano piaskownicy Chrome.

POST `/api/reservations` nadal zwraca zamierzone 501. Po wysłaniu formularza Chrome może pokazać komunikat zasobu o tym statusie; UI wyjaśnia, że zapis pozostaje zadaniem. AC-05/06/08 nie są ukończone w starterze. Dane, stan początkowy i brak zapisu pozostają opisane w [SPEC.md](SPEC.md).

Logowanie i licencje Copilot, uprawnienia komputerów COI, uruchomienie ćwiczeń przez Copilota, Android oraz integracje on-prem wymagają osobnych prób w rzeczywistym środowisku. Dostarczone syntetyczne logi służą analizie; nie przedstawiają przebiegu na urządzeniu lub serwerze klienta. Własny wynik zapisz w [szablonie raportu](REPORT-TEMPLATE.md).
