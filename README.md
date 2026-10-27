# Agenci AI dla testerów automatycznych - GitHub Copilot

**Effect Group, 26-27.10.2026, online, 09:00-16:00 Europe/Warsaw.**

[Materiały, slajdy i prompty](https://devpowers.com/szkolenia/effect-group/coi-github-copilot-2026-10/) | [Dokładny program](PROGRAM.md) | [Indeks kursu](course-materials/Course%20INDEX%20-%20AI%20in%20Programming.md)

Pracujemy z GitHub Copilot w IntelliJ IDEA i Copilot CLI, a w drugim dniu także z Microsoft 365 Copilot, którego używacie w pracy. Testujemy aplikacje tak jak na co dzień: z zewnątrz, bez dostępu do ich kodu.

## Dzień 2: testy czarnej skrzynki

1. [Web: Dolibarr i Selenium + TestNG](course-materials/exercises/black-box-web/README.md). Prawdziwy system finansowy (noty kosztowe, faktury), [przypadki testowe](course-materials/exercises/black-box-web/PRZYPADKI-TESTOWE.md) i [projekt startowy](course-materials/exercises/black-box-web/dolibarr-selenium/).
2. [Android: Appium bez kodu aplikacji](course-materials/exercises/black-box-android/README.md). APK, [lokatory odczytane z APK](course-materials/exercises/black-box-android/LOKATORY-Z-APK.md) i [projekt startowy](course-materials/exercises/black-box-android/mydemoapp-appium/).
3. [Triage nieudanych testów](course-materials/exercises/failure-triage/README.md). Prawdziwe wyniki przebiegu, kategorie jak w ReportPortal, zgłoszenie do Jira.
4. [Microsoft 365 Copilot: agent testera](course-materials/exercises/m365-copilot/README.md). Gotowy agent z zajęć: [Repository Checker z dwoma skillami](course-materials/exercises/m365-copilot/repository-checker/README.md) ([ZIP](course-materials/exercises/m365-copilot/repository-checker/repository-checker.zip)).
5. [Modele self-hosted: Mistral, OpenCode, Zed, IntelliJ](course-materials/exercises/self-hosted-models/README.md).

## Zacznij tutaj

1. Sklonuj repozytorium albo pobierz ZIP. W IntelliJ otwórz jako projekt sam katalog ćwiczenia, np. `course-materials/exercises/black-box-web/dolibarr-selenium`. Wtedy Copilot czyta instrukcje tego projektu.
2. Sprawdź Copilot Chat w IntelliJ w trybie Agent. Członkostwo w zespole GitHub nie przyznaje licencji Copilot: licencje obsługuje prowadzący.
3. Uruchom testy startowe: `.\mvnw.cmd -B test` (Windows) albo `./mvnw -B test`. Bez dostępu do repozytorium bibliotek pracuj na kodzie i oznacz testy jako nieuruchomione.
4. Nie instaluj niczego z uprawnieniami administratora i nie zmieniaj zabezpieczeń komputera.

## Dzień 1 i biblioteka ćwiczeń z kodem

Ćwiczenia, w których agent czyta kod aplikacji, przydadzą się zespołom z dostępem do kodu albo przy współpracy z developerami: [jFTP](course-materials/exercises/jftp-legacy/README.md), [Spring Petclinic REST + Angular](course-materials/exercises/real-application-testing/PETCLINIC-WORKSHOP.md), [Java API, Angular i Android ze źródeł](course-materials/exercises/real-application-testing/README.md), [mały starter Java QA](course-materials/exercises/coi-java/README.md) oraz [Test Notebook](course-materials/ideas/test-notebook-idea.md).

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
