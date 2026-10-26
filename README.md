# Agenci AI dla testerów automatycznych - GitHub Copilot

**Effect Group, 26-27.10.2026, online, 09:00-16:00 Europe/Warsaw.**

[Materiały, slajdy i prompty](https://devpowers.com/szkolenia/effect-group/coi-github-copilot-2026-10/) | [Dokładny program](PROGRAM.md) | [Główne ćwiczenia jFTP](course-materials/exercises/jftp-legacy/README.md) | [Android: możliwości i granice](course-materials/Research/android-testing-options-2026-10.md)

Pracujemy z GitHub Copilot w IntelliJ IDEA oraz Copilot CLI nad testami istniejących aplikacji. Główna ścieżka pierwszego dnia to jFTP: poznanie kodu Java, projektowanie przypadków, testy charakterystyki i kontrolowana refaktoryzacja. Selenium i API omawiamy na osobnym przykładzie webowym. Drugiego dnia rozwijamy testy API i przeglądarki, porównujemy Selenium z Playwright oraz wybieramy wariant natywnego Androida. Review, triage CI i integracje on-prem odnosimy do tych zadań.

## Zacznij tutaj

1. Otwórz repozytorium na swoim komputerze COI. Sprawdź zgodę organizacji na użycie agenta i przekazywany kontekst. Używamy wyłącznie danych syntetycznych.
2. Sprawdź logowanie i dostęp do Copilot osobno w IntelliJ oraz CLI. Członkostwo w zespole GitHub nie przyznaje licencji Copilot. Konta, zaproszenia i licencje obsługuje prowadzący; ustawienia organizacji mogą ograniczać funkcje.
3. Przeczytaj [start i kontrolę narzędzi](course-materials/exercises/coi-java/SETUP.md). Nie instaluj niczego z uprawnieniami administratora ani nie zmieniaj zabezpieczeń komputera. Gdy brakuje narzędzi, wybierz dostarczoną ścieżkę analizy plików.
4. Otwórz [instrukcję jFTP](course-materials/exercises/jftp-legacy/README.md) i utwórz osobną kopię aplikacji na wskazanej rewizji. Materiały i kod aplikacji mają różne katalogi.
5. Świadomie wybierz [opcjonalny starter Java QA](course-materials/exercises/coi-java/README.md). Nie wymaga on zbudowania całej aplikacji przed testowaniem. `app/` w katalogu głównym pozostaje pustym szkieletem.

Starter to mały, celowo niekompletny model rezerwacji wizyt z syntetycznymi danymi. Nie przedstawia systemu COI. Zawiera testy JUnit i lokalnego API, opcjonalne Selenium/REST Assured oraz jawne zadania do wykonania. [Specyfikacja i pokrycie](course-materials/exercises/coi-java/SPEC.md) oddzielają działającą bazę od brakującego zachowania. Każde [ćwiczenie](course-materials/exercises/coi-java/WORKSHOP.md) podaje pliki, polecenie, pełny prompt, czas i wariant bez instalacji lub kredytów.

## Realne aplikacje i propozycje warsztatowe

- [jFTP: start, źródła i 5 pełnych promptów QA](course-materials/exercises/jftp-legacy/README.md). Dostarczona baza ma 3 testy startowe. Nowe zadania obejmują filtry, sortowanie, kontrakt poleceń, dokumentację i refaktoryzację pod ochroną własnych testów. Swing wymaga innych narzędzi UI niż Selenium.
- [Spring Petclinic REST + Angular: stanowe CRUD](course-materials/exercises/real-application-testing/PETCLINIC-WORKSHOP.md). Właściciel -> zwierzę -> wizyta, testy HTTP i przeglądarki, izolacja oraz sprzątanie danych. Źródła sprawdzone; uruchomienie wymaga próby w dopuszczonym środowisku.
- [Java API, Angular i Android](course-materials/exercises/real-application-testing/README.md). Osobne aplikacje, przypięte źródła i jawne ograniczenia uruchomienia. Kotlin Android ma lokalne polisy i szkody; nie jest klientem backendu wycen.
- [Test Notebook: krótki pomysł aplikacji](course-materials/ideas/test-notebook-idea.md). Projekty, przypadki, wyniki, formularze i dashboard; opcjonalny Kanban. SQLite, bez logowania w pierwszej wersji. Zakres, wymagania i ADR ustalimy razem. Nie jest to gotowy PRD ani obowiązkowe zadanie budowania aplikacji.
- [Inspiracje: TestRail, Qase i Kiwi TCMS](course-materials/Research/test-management-inspiration-2026-10.md). Funkcje do porównania z codzienną pracą testerów, bez wymogu zakładania kont.

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
