# Testowanie istniejących aplikacji

Wybierz z grupą obszar do przetestowania: Java API, przeglądarkę Angular albo natywny Android. Rozwijamy testy istniejącej funkcjonalności. Nie budujemy nowej aplikacji i nie przenosimy jej źródeł do repozytorium materiałów.

## Rozbudowany cel Java i przeglądarki

[Spring Petclinic REST + Angular](PETCLINIC-WORKSHOP.md) to osobny, bogatszy wariant: powiązane zasoby właściciela, zwierzęcia i wizyty, operacje CRUD, walidacja, testy HTTP oraz Selenium/Playwright. Wybierz go do pracy ze stanem i bazą. Poniższy PolicyLab służy węższej analizie wyceny, błędów i ponowień; Angular PolicyLab domyślnie pokazuje prowadzący. Android pozostaje odrębnym celem.

## Wspólna sytuacja biznesowa

Użytkownik chce uzyskać wycenę ubezpieczenia pojazdu, poprawić błędne dane i ponowić zapytanie. Sprawdzamy wynik finansowy, granice walidacji, zachowanie po błędzie oraz powtarzalność. W Angularze sprawdzamy widoczny status i rzeczywiste żądania. Android ma osobny proces: wybór lokalnej polisy, zgłoszenie szkody, usunięcie zgłoszenia i reset danych. Nie jest klientem API wycen.

| Ścieżka | Narzędzia | Wynik pracy | Czas |
| --- | --- | --- | --- |
| [Java API](API-WORKSHOP.md) | IntelliJ IDEA, Copilot, JUnit 5; HTTP w Javie dla uruchomionego serwera | Nowe przypadki graniczne i dowody odpowiedzi | 75-90 min |
| [Angular - pokaz na przygotowanej kopii](API-WORKSHOP.md#przeglądarka-angular) | Selenium w Javie; opcjonalnie porównanie z Playwright | Przejście od błędu do poprawnej wyceny i kontrola żądań | 30-40 min |
| [Natywny Android](ANDROID-WORKSHOP.md) | Android Studio i istniejący runner natywny, jeśli środowisko jest gotowe | Scenariusz lokalnej szkody i testy stanu | 45-60 min |
| Bez instalacji | Publiczne źródła, wspólna analiza, dowody z uzgodnionego lokalnego uruchomienia | Macierz przypadków i raport z jawnymi brakami dowodów | 30-45 min |

Ścieżkę Android wybieramy po pierwszym dniu. Domyślnie zaczynamy od pokazu lub analizy źródeł; testy uczestników dopiero po potwierdzeniu działania konkretnego środowiska. Brak uprawnień instalacyjnych nie blokuje analizy i projektowania testów. Nie potrzebujesz konta Sentry, klucza dostawcy AI, płatnej usługi, Dockera ani nowej maszyny w chmurze. Dostęp do Copilota i zasady pracy na komputerze pozostają zgodne z Twoją organizacją.

## Źródło i pierwszy krok

Pracujemy na [publicznym repozytorium aplikacji, rewizja f788b15](https://github.com/LucasMatuszewski/sentry-course-06-2026/tree/f788b15e0ac72bb1f2bb000c6f290dbb6a716326/apps). Otwórz je w osobnym, uzgodnionym katalogu. Jeżeli masz już kopię z własnymi zmianami, nie nadpisuj jej ani nie przełączaj automatycznie rewizji. [Katalog źródeł](SOURCE-CATALOG.md) wskazuje konkretne pliki i ograniczenia licencyjne.

Sprawdź rewizję w katalogu aplikacji:

```bash
git rev-parse HEAD
```

Oczekiwany wynik: `f788b15e0ac72bb1f2bb000c6f290dbb6a716326`. Inna rewizja oznacza konieczność ponownego porównania tras, modeli i testów.

Przed uruchomieniem odczytaj właściwy deskryptor projektu i sprawdź dostępne środowisko. Backend ma wrapper Gradle; oba katalogi Android go nie zawierają. Nie kopiuj wrappera między aplikacjami. Gotowe testy w źródle są punktem wyjścia do analizy pokrycia, a nie rozwiązaniem nowego zadania.

## Jak zapisujemy dowody

Analiza źródeł potwierdza istnienie metod i testów, ale nie potwierdza ich wykonania. W tym pakiecie nie deklarujemy zakończonego uruchomienia aplikacji ani przejścia testów. Każda para zapisuje osobno:

- Rewizję, środowisko, datę i dokładne polecenie.
- Wynik kompilacji, wynik testów z liczbą wykrytych/wykonanych/pominiętych oraz wynik startu aplikacji.
- Dla HTTP: żądanie i pełną odpowiedź lokalnego serwera, status, nagłówki i treść, bez sekretów. Dla UI: kroki, zrzut ekranu, rzeczywisty wynik i błędy konsoli.
- Oczekiwanie biznesowe uzgodnione przed testem, obserwację oraz status: wykonano, analiza źródła albo niewykonano z podaną przyczyną.

Nie twórz mocka endpointu z przykładowego JSON-a z README. Najpierw przechwyć aktualną, bezpieczną odpowiedź lokalnej aplikacji. Bez takiego uruchomienia przygotuj przypadki i kontrakt do weryfikacji, bez twierdzenia, że test integracyjny przeszedł.
