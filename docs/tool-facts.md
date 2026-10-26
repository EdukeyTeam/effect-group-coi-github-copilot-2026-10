# Copilot i narzędzia warsztatu

Stan źródeł: **26.10.2026**. Ten plik nie potwierdza wersji, licencji, uprawnień ani instalacji na komputerach uczestników. Własny wynik kontroli zapisz w [raporcie](../course-materials/exercises/coi-java/REPORT-TEMPLATE.md).

- Narzędzia podstawowe: GitHub Copilot w IntelliJ IDEA i Copilot CLI. Sprawdź logowanie, dostępny model i politykę organizacji osobno dla obu powierzchni. Członkostwo w zespole repozytorium nie jest przydziałem licencji Copilot. [Plany i dostęp](https://docs.github.com/en/copilot/get-started/plans).
- [Instalacja CLI](https://docs.github.com/en/copilot/how-tos/copilot-cli/set-up-copilot-cli/install-copilot-cli) opisuje PowerShell 6+ na Windows; warsztat używa istniejącego, zatwierdzonego PowerShell 7. Node.js 22+ dotyczy metody instalacji przez npm, nie każdego sposobu uruchomienia ani testów Java. Niczego nie instalujemy automatycznie.
- [Obsługa instrukcji](https://docs.github.com/en/copilot/reference/custom-instructions-support) różni się między JetBrains, CLI i chmurą. Zaczynamy od `.github/copilot-instructions.md` i jawnego briefu z plikami. Samo istnienie `AGENTS.md` nie dowodzi odczytania przez każdą powierzchnię.
- [Reguły narzędzi CLI](https://docs.github.com/en/copilot/how-tos/copilot-cli/use-copilot-cli/allowing-tools) wymagają weryfikacji w istniejącej wersji. Przykłady launcherów w bibliotece nie są sandboxem i nie zastępują ochrony gałęzi lub polityki danych. Nie aktywujemy ich przez samo sklonowanie.
- Prompt files, skills, custom agents, hooks, MCP i cloud agent są materiałem do świadomego wyboru. Ich dostępność zależy od klienta i polityki. Root ustawienia pozostają neutralne; [przykłady](../course-materials/agent-configs/) nie są wymaganiami instalacyjnymi.
- Azure reviewer ma lokalny warunek `if: false` oraz brak automatycznych triggerów. Nie włączaj go, nie dodawaj sekretów i nie używaj Azure do programowania/code review. Obecność pliku workflow nie jest dowodem uruchomionego CI.
- Java fixture nie potrzebuje klucza OpenRouter ani innego klucza LLM. [Przypięte wersje i dokumentacja](../course-materials/exercises/coi-java/SOURCES.md) dotyczą JUnit/Selenium/REST Assured; rzeczywisty wynik testów zależy od uruchomienia na zatwierdzonym JDK/Maven.
- [Playwright Java](https://playwright.dev/java/docs/intro), browser exploration przez agenta i [natywny Android](../course-materials/Research/android-testing-options-2026-10.md) mają różne granice obserwacji. Nie traktuj screenshotu web jako dowodu testu natywnej Activity.

Nie zakładamy konkretnego CI, wersji Jira/Bitbucket, MCP, kredytów lub dodatkowej subskrypcji. Wariant plikowy jest dostępny dla każdego ćwiczenia; brak uruchomienia zapisujemy wprost.
