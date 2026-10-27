# Microsoft 365 Copilot w pracy testera

To narzędzie macie dziś w pracy. Nie widzi repozytorium ani IDE, ale dobrze przygotowany agent z instrukcjami i plikami wiedzy przejmuje sporo powtarzalnej pracy: przypadki testowe z wymagań, szkice Page Objects z HTML, triage wyników i zgłoszenia do Jira.

## Co działa w której wersji (stan: październik 2026)

| Funkcja | Copilot Chat (wersja podstawowa) | Microsoft 365 Copilot (licencja) |
|---|---|---|
| Czat z wgranymi plikami (`.xml`, `.log`, `.csv`, `.java`, `.md`) | Tak | Tak |
| Własny agent z instrukcjami i plikami wiedzy (Agent Builder) | Tak, jeśli administrator włączył | Tak |
| Code Interpreter w agencie (Python na wgranych plikach) | Zależnie od ustawień | Tak |
| Analyst (wieloetapowa analiza danych w Pythonie) | Nie | Tak |
| Zaplanowane prompty (np. codziennie o 8:00) | Nie | Tak, do 10 |
| Pages i Notebooks | Tak, w ograniczonym zakresie | Tak |
| Dostęp do poczty, Teams i SharePoint bez wgrywania | Nie | Tak |

Szczegóły zależą od ustawień Waszego tenanta. Sprawdź u siebie: w czacie Copilota wybierz "Utwórz agenta" (Create agent). Jeśli tej opcji nie ma, administrator jej nie włączył.

## Ćwiczenie: agent "Asystent testera" (20 min)

1. Utwórz agenta. Nazwa: Asystent testera. Opis: przypadki testowe, szkice testów Selenium + TestNG, triage wyników.
2. Wklej instrukcje z sekcji poniżej.
3. Dodaj pliki wiedzy: szablon przypadku testowego Waszego zespołu (tu: [PRZYPADKI-TESTOWE.md](../black-box-web/PRZYPADKI-TESTOWE.md)), konwencje automatyzacji (tu: [LoginPage.java](../black-box-web/dolibarr-selenium/src/test/java/pl/kurs/qa/dolibarr/pages/LoginPage.java) i [LoginTest.java](../black-box-web/dolibarr-selenium/src/test/java/pl/kurs/qa/dolibarr/tests/LoginTest.java)) oraz [kategorie triage](../failure-triage/README.md).
4. Sprawdź agenta na trzech zadaniach:
   - "Przygotuj przypadki testowe dla: zatwierdzenie noty kosztowej przez przełożonego. Uwzględnij przypadki brzegowe i negatywne." Oceń, które przypadki są wartościowe.
   - Wklej outerHTML formularza noty z Dolibarr i poproś o Page Object zgodny z konwencją z plików wiedzy. Porównaj z tym, co zrobił Copilot w IntelliJ.
   - Wgraj `testng-results.xml` i pliki `failure.json` z [run-artifacts](../failure-triage/run-artifacts/) i użyj promptu TRIAGE-01.
5. Zapisz, czego agentowi brakowało: to lista uzupełnień instrukcji.

Nie wgrywaj danych osobowych, danych obywateli ani logów z produkcji bez zgody bezpieczeństwa. Copilot w pracy działa w ramach ochrony danych firmowych Microsoft 365, ale decyzję o klasie danych podejmuje Wasza organizacja.

## Instrukcje agenta

```text
Jesteś asystentem zespołu testów automatycznych. Zespół testuje aplikacje webowe i mobilne z zewnątrz, bez dostępu do kodu aplikacji. Stack: Java, Selenium WebDriver, TestNG, Appium, Page Object Model, przypadki testowe w Jira.

Zasady:
- Odpowiadaj po polsku. Kod i nazwy techniczne po angielsku.
- Przypadek testowy ma: ID, tytuł, warunki wstępne, kroki, oczekiwany wynik, dane testowe, priorytet. Każdy oczekiwany wynik musi być obserwowalny dla użytkownika.
- Przy generowaniu przypadków dodawaj przypadki brzegowe i negatywne oraz pytania do analityka, gdy wymaganie jest niejasne. Nie wymyślaj reguł biznesowych.
- Kod testów: Page Object, lokatory w polach prywatnych, kolejność lokatorów: id, name, tekst widoczny dla użytkownika, CSS, XPath tylko z uzasadnieniem. Tylko jawne oczekiwania (WebDriverWait), bez Thread.sleep. Opis testu zaczyna się od ID przypadku.
- Nie masz dostępu do repozytorium. Gdy brakuje klasy bazowej lub konwencji, zapytaj albo poproś o wklejenie pliku.
- Triage: kategorie Product Bug, Automation Bug, System Issue, No Defect, To Investigate. Każda decyzja z dowodem z pliku. Przy braku dowodu wybierz To Investigate.
- Zgłoszenie błędu: tytuł opisuje objaw, kroki da się powtórzyć ręcznie, wynik oczekiwany pochodzi z wymagania.
- Nigdy nie proś o hasła, tokeny ani dane osobowe.
```

## Zaplanowany prompt (licencja Microsoft 365 Copilot)

Po zadaniu pytania najedź na prompt i wybierz "Zaplanuj ten prompt". Przykład na poranny przegląd:

```text
Przejrzyj wiadomości z Jenkinsa z ostatnich 24 godzin o nieudanych przebiegach testów w moim folderze "CI". Dla każdego przebiegu podaj: nazwę joba, liczbę porażek, powtarzające się komunikaty błędów i czy wyglądają na problem środowiska. Zakończ listą trzech rzeczy do sprawdzenia rano.
```

Działa tylko na danych, do których Copilot ma dostęp w Microsoft 365 (np. poczta z powiadomieniami z Jenkinsa). Nie łączy się z Jenkinsem ani z Jira bez konektora zatwierdzonego przez organizację.

## Gdzie kończy się Microsoft 365 Copilot

- Nie uruchamia Waszych testów i nie widzi wyniku kompilacji.
- Nie czyta repozytorium: każdą klasę trzeba wgrać lub wkleić.
- Nie klika w aplikacji. Do eksploracji przez przeglądarkę lub urządzenie potrzebny jest agent z narzędziami (GitHub Copilot w IntelliJ z MCP, Copilot CLI).

Dlatego dobry podział na dziś: Microsoft 365 Copilot do analizy, przypadków i raportów, GitHub Copilot w IDE do kodu testów i uruchomień.
