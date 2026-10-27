# Repository Checker: agent Microsoft 365 Copilot z dwoma skillami

Agent, którego zbudowaliśmy na szkoleniu w Agent Builderze. Zanim sklonujesz, pobierzesz lub uruchomisz repozytorium z GitHub albo GitLab (bibliotekę, skill agenta, serwer MCP), agent ocenia, czy jest bezpieczne i do czego może się przydać w Waszej pracy testerskiej.

[Pobierz paczkę agenta (ZIP)](repository-checker.zip) | [Rozpakowana zawartość](agent/)

## Co jest w środku

| Plik | Do czego służy |
|---|---|
| [`declarativeAgent_0.json`](agent/declarativeAgent_0.json) | Instrukcje agenta, pięć startowych promptów, wyszukiwanie w sieci ograniczone do github.com i gitlab.com, Code Interpreter |
| [`manifest.json`](agent/manifest.json) | Manifest aplikacji Microsoft 365 (nazwa, opis, ikony) |
| [`repository-security-audit`](agent/skills/repository-security-audit/SKILL.md) | Skill: audyt bezpieczeństwa repozytorium bez uruchamiania jego kodu. Prompt injection, wykonywanie poleceń, wyciek danych, telemetria, zależności, CI, ryzyka typowe dla Javy i reputacja projektu. Kończy się jedną z pięciu rekomendacji, od "Do not acquire" do "No material issue found in reviewed scope". |
| [`scripts/main.py`](agent/skills/repository-security-audit/scripts/main.py) | Statyczny skaner wzorców (Python, bez zależności), który agent uruchamia w Code Interpreterze. Wynik to `repository-audit.json` z plikiem, linią i ważnością każdego trafienia. Trafienie to trop do sprawdzenia, nie dowód. |
| [`repository-use-case-advisor`](agent/skills/repository-use-case-advisor/SKILL.md) | Skill: co repozytorium robi, jak działa i gdzie pasuje w Waszej pracy (testy automatyczne, E2E, ręczne, dane testowe, triage, CI). Uwzględnia wynik audytu: nie poleca uruchamiania czegoś, co audyt odradził. |

## Jak zainstalować

1. **Paczka ZIP:** w Teams wybierz Aplikacje, potem Zarządzaj aplikacjami i Przekaż aplikację niestandardową (Upload a custom app), i wskaż `repository-checker.zip`. Jeśli tej opcji nie ma, administrator Microsoft 365 może wdrożyć paczkę w centrum administracyjnym (Integrated apps).
2. **Ręcznie w Agent Builderze:** utwórz agenta, wklej `instructions` i startowe prompty z `declarativeAgent_0.json`, włącz Code Interpreter i dodaj oba foldery skilli, jeśli Twój tenant obsługuje skille w agentach.
3. **Same skille w GitHub Copilot:** foldery z `agent/skills/` to zwykłe skille agenta (`SKILL.md` z opisem i instrukcjami). Skopiuj je do `.github/skills/` w swoim projekcie, aby Copilot CLI mógł z nich korzystać.

## Jak używać

Wybierz jeden ze startowych promptów (np. "Audit a repository") i podaj adres repozytorium. Gdy pytasz o bezpieczeństwo i przydatność naraz, agent najpierw robi audyt, a potem doradza.

Czysty wynik skanera nie dowodzi, że repozytorium jest bezpieczne. Decyzję o pobraniu do sieci firmowej podejmujecie zgodnie z zasadami Waszej organizacji.
