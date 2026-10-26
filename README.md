# Agenci AI dla testerów automatycznych - GitHub Copilot

**Effect Group / COI, 26-27.10.2026, online, 09:00-16:00 Europe/Warsaw.**

[Materiały, slajdy i prompty](https://devpowers.com/szkolenia/effect-group/coi-github-copilot-2026-10/) | [Dokładny program](PROGRAM.md) | [Start ćwiczeń Java QA](course-materials/exercises/coi-java/README.md) | [Android: możliwości i granice](course-materials/Research/android-testing-options-2026-10.md)

Pracujemy z GitHub Copilot w IntelliJ IDEA oraz Copilot CLI, przede wszystkim nad testami Java/Selenium. Pierwszego dnia sprawdzamy dostęp, kontekst repozytorium i zgody, projektujemy scenariusze, uruchamiamy testy zachowania oraz diagnozujemy Selenium i API. Drugiego dnia porównujemy Selenium z Playwright, badamy aplikację przez przeglądarkę, omawiamy Android oraz review, triage CI i integracje on-prem. Kolejność i tempo dopasowujemy do pytań grupy.

## Zacznij tutaj

1. Otwórz repozytorium na swoim komputerze COI. Sprawdź zgodę organizacji na użycie agenta i przekazywany kontekst. Używamy wyłącznie danych syntetycznych.
2. Sprawdź logowanie i dostęp do Copilot osobno w IntelliJ oraz CLI. Członkostwo w zespole GitHub nie przyznaje licencji Copilot. Konta, zaproszenia i licencje obsługuje prowadzący; ustawienia organizacji mogą ograniczać funkcje.
3. Przeczytaj [start i kontrolę narzędzi](course-materials/exercises/coi-java/SETUP.md). Nie instaluj niczego z uprawnieniami administratora ani nie zmieniaj zabezpieczeń komputera. Gdy brakuje narzędzi, wybierz dostarczoną ścieżkę analizy plików.
4. Świadomie wybierz [opcjonalny starter Java QA](course-materials/exercises/coi-java/README.md). Nie wymaga on zbudowania całej aplikacji przed testowaniem. `app/` w katalogu głównym pozostaje pustym szkieletem.

Starter to mały, celowo niekompletny model rezerwacji wizyt z syntetycznymi danymi. Nie przedstawia systemu COI. Zawiera testy JUnit i lokalnego API, opcjonalne Selenium/REST Assured oraz jawne zadania do wykonania. [Specyfikacja i pokrycie](course-materials/exercises/coi-java/SPEC.md) oddzielają działającą bazę od brakującego zachowania. Każde [ćwiczenie](course-materials/exercises/coi-java/WORKSHOP.md) podaje pliki, polecenie, pełny prompt, czas i wariant bez instalacji lub kredytów.

## Jak korzystać z agentów

Agent czyta instrukcje w rzeczywistym katalogu pracy. [Instrukcje Copilot](.github/copilot-instructions.md) i [reguły repozytorium](AGENTS.md) określają granice; obsługę formatów w IntelliJ i CLI sprawdź osobno. [Copilot: uruchomienie i zgody](course-materials/agent-configs/copilot/README.md) zawiera opcjonalne przykłady. Skopiowanie repozytorium nie włącza własnych agentów, hooków, profili modeli ani MCP. Nieaktywny workflow review Azure nie jest weryfikacją tego kursu i nie wolno go włączać.

Przed zmianą kodu ustal kryterium, potem uruchom test. Już działające zachowanie zapisuj jako istniejące pokrycie. Dla brakującego zachowania pokaż uczciwe Red -> Green -> Refactor. Nie osłabiaj asercji, nie pomijaj testów i nie dodawaj retry, aby uzyskać zielony raport. Oddziel wyniki kompilacji, testów, startu aplikacji i ręcznej kontroli. [Szablon raportu](course-materials/exercises/coi-java/REPORT-TEMPLATE.md) pomaga zebrać dowody.

## Biblioteka dodatkowa

Pełna biblioteka `course-materials/` pozostaje dostępna do samodzielnego wyboru. Agent nie powinien jej przeszukiwać podczas pracy nad aplikacją bez Twojego polecenia. Przykładowe PRD, ADR i konfiguracje nie ustalają wymagań projektu. Dla własnej aplikacji użyj jej źródeł, specyfikacji, stosu i poleceń.

- [Pełne prompty](course-materials/Prompt%20examples/) oraz [dane syntetyczne](course-materials/exercise-data/).
- [Konfiguracje narzędzi](course-materials/agent-configs/), [hooki i zgody](course-materials/hooks-example/), [Context7](course-materials/hooks-example/context7/).
- [CI i code review](course-materials/cicd-headless/), [agent review](course-materials/cicd-headless/agent-review/), [PR-Agent i Qodo](course-materials/cicd-headless/PR-Agent-Qodo/).
- [Stan narzędzi i źródła](docs/tool-facts.md). Materiały porównawcze o Codex i Claude Code są opcjonalne; nie wymagają dodatkowych subskrypcji uczestnika.

Nie zakładamy gotowego CI, dostępu do Jira/Bitbucket ani urządzenia Android. Ćwiczenia mają wariant plikowy. Przykłady integracji są nieaktywne; nie wpisuj prawdziwych endpointów ani tokenów. Klucze odpowiedzi prowadzącego nie należą do tego repozytorium.

Trener: Łukasz Matuszewski | [LinkedIn](https://www.linkedin.com/in/lukaszmatuszewski/) | [X](https://x.com/mrLumatic)
