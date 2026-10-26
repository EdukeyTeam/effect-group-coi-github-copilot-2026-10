# Nieaktywne przykłady

`claude-maven-review.settings.json` jest przykładem hooka przeglądającego zależności Maven. Nie jest konfiguracją warsztatu Copilot. Nie kopiuj go do `.claude/settings.json` podczas ćwiczeń. Wywołanie dodatkowego agenta wymaga sprawdzenia narzędzia, zakresu, uprawnień i kosztu. Ustawienia w katalogu głównym nie uruchamiają hooków ani providerów. Claude zachowuje listy deny/ask, a Codex aktywny profil course-root z odmową odczytu plików środowiskowych i sekretów oraz reguły komend. Nie ma szerokiej listy automatycznych zgód.

Workflow Azure w `.github/workflows/` ma wyłączony job i nie jest częścią kontroli Java. Nie włączaj go, nie dodawaj sekretów i nie używaj Azure do programowania lub code review. Pozostałe przykłady CI w bibliotece wymagają osobnego przeglądu i dostosowania do rzeczywistego systemu.

`codex-project.config.toml` i `mcp.example.json` zachowują pełne przykłady konfiguracji do osobnej lektury. Nie kopiuj ich do katalogu głównego podczas pierwszego ćwiczenia.
