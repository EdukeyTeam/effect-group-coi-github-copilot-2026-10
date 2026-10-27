# Modele self-hosted: jak to działa

Pokaz prowadzącego (ok. 40 min). Cel: zrozumieć elementy, z których składa się agent na własnym modelu, zanim Wasza organizacja postawi serwer. Stan: październik 2026. Wersje i licencje sprawdź przed zakupem sprzętu.

## Trzy warstwy

1. **Model**: plik z wagami, np. Devstral Small 2 (Mistral, UE).
2. **Serwer modelu**: udostępnia model przez API zgodne z OpenAI (`/v1/chat/completions`). Dla zespołu: vLLM. Na jeden komputer albo pilotaż: Ollama lub LM Studio.
3. **Narzędzie agenta**: czyta pliki, uruchamia polecenia i wysyła zapytania do serwera modelu. OpenCode, Mistral Vibe, Zed, IntelliJ.

Agent jest tak dobry, jak model potrafi wywoływać narzędzia. Bez obsługi tool calling dostajesz czat, nie agenta.

## Modele z UE i alternatywy

| Model | Rozmiar | Licencja | Sprzęt (orientacyjnie) | Do czego |
|---|---|---|---|---|
| Devstral Small 2 (Mistral) | 24B | Apache 2.0 | 1 GPU 24 GB (kwantyzacja Q4) albo 48 GB | Agent kodujący, testy, Page Objects |
| Devstral 2 (Mistral) | 123B | Modified MIT, warunek przychodu | Kilka GPU klasy H100 | Najmocniejszy model Mistral do agentów |
| Mistral Small 4 | 119B MoE, ok. 6,5B aktywnych | Apache 2.0 | ok. 70-125 GB VRAM | Agent, rozumowanie i obrazy w jednym modelu |
| Ministral 3 | 3B-14B | Apache 2.0 | Laptop lub mała karta | Szybkie, proste zadania |
| Bielik v3 (SpeakLeash, PL) | 11B | Apache 2.0 | 1 GPU | Teksty po polsku: przypadki, raporty, zgłoszenia |
| gpt-oss-120b (OpenAI) | 117B MoE | Apache 2.0 | 1 GPU 80 GB | Triage, rozumowanie |
| Qwen3-Coder (Alibaba) | 30B-480B | Apache 2.0 | od 1 GPU 24 GB | Silne kodowanie, model spoza UE |

Codestral 22B ma licencję nieprodukcyjną (MNPL): nie nadaje się do pracy zawodowej bez umowy z Mistral.

Uczciwie: najlepsze modele komercyjne są wyraźnie mocniejsze w długich zadaniach agentowych. Model lokalny dobrze radzi sobie z zadaniami o jasnym zakresie: przypadek na test, Page Object z HTML, klasyfikacja porażki według reguł.

## Serwer modelu

vLLM z obsługą narzędzi dla modeli Mistral:

```bash
vllm serve mistralai/Devstral-Small-2-24B-Instruct-2512 --tokenizer-mode mistral --config-format mistral --load-format mistral --enable-auto-tool-choice --tool-call-parser mistral
```

Ollama na jednym komputerze:

```bash
ollama run devstral-small-2
```

W sieci odciętej od internetu wagi i obraz serwera przenosi się z maszyny przejściowej. Dostęp do serwera tylko z sieci wewnętrznej, logi zapytań według zasad bezpieczeństwa.

## OpenCode z własnym serwerem

Plik `opencode.json` w projekcie albo `~/.config/opencode/opencode.json`:

```json
{
  "$schema": "https://opencode.ai/config.json",
  "provider": {
    "vllm": {
      "npm": "@ai-sdk/openai-compatible",
      "name": "Serwer wewnętrzny",
      "options": { "baseURL": "http://serwer-modeli:8000/v1", "apiKey": "sk-local" },
      "models": {
        "mistralai/Devstral-Small-2-24B-Instruct-2512": {
          "name": "Devstral Small 2",
          "limit": { "context": 131072, "output": 16384 }
        }
      }
    }
  },
  "model": "vllm/mistralai/Devstral-Small-2-24B-Instruct-2512"
}
```

Bez internetu wyłącz pobieranie listy modeli i aktualizacje: `OPENCODE_DISABLE_MODELS_FETCH=true`, `OPENCODE_DISABLE_AUTOUPDATE=true`.

## ACP: ten sam agent w edytorze

Agent Client Protocol (ACP) to otwarty protokół Zed i JetBrains: edytor wyświetla rozmowę i zmiany, a agent działa jako osobny proces z własnym modelem.

- OpenCode: `opencode acp`
- Mistral Vibe: `vibe-acp`
- GitHub Copilot CLI: `copilot --acp`

**Zed** (Windows, Linux, macOS): Agent Panel, External Agents, Add Custom Agent. Model lokalny bezpośrednio w Zed:

```json
{
  "language_models": {
    "ollama": {
      "api_url": "http://localhost:11434",
      "auto_discover": false,
      "available_models": [
        { "name": "devstral-small-2", "display_name": "Devstral Small 2", "max_tokens": 65536, "supports_tools": true }
      ]
    }
  }
}
```

**IntelliJ**: AI Chat, menu więcej akcji, dodanie własnego agenta. IDE tworzy `~/.jetbrains/acp.json`:

```json
{
  "default_mcp_settings": {},
  "agent_servers": {
    "OpenCode": { "command": "opencode", "args": ["acp"] }
  }
}
```

Agentów ACP można używać w IntelliJ bez subskrypcji JetBrains AI. Organizacja może ograniczyć dozwolonych agentów.

**GitHub Copilot w IntelliJ** obsługuje własny model (BYOK) i Ollama, ale nadal wymaga logowania do GitHub. Do pracy w pełni odciętej od internetu lepiej pasują OpenCode, Mistral Vibe albo Continue.

## Dokumentacja bez internetu

Agent pisze lepszy kod, gdy ma aktualną dokumentację bibliotek (Selenium, TestNG, Appium). Opcje on-prem:

- Context7 w wersji on-prem (komercyjna): indeks dokumentacji publicznej i wewnętrznej przez MCP.
- Własny serwer dokumentacji MCP, który indeksuje lokalne katalogi HTML, Markdown i Javadoc.
- Javadoc z lokalnego repozytorium Maven (Nexus, Artifactory) jako źródło sygnatur metod.

## Pokaz: to samo zadanie, dwa modele

Prompt BB-WEB-01 z ćwiczenia web na modelu komercyjnym i na Devstral Small 2. Porównujemy: czy model trzyma się konwencji projektu, ile poprawek wymaga test, czy uruchomił test i poprawnie odczytał wynik.

## Źródła

- [Modele Mistral](https://docs.mistral.ai/getting-started/models/)
- [Mistral Vibe](https://github.com/mistralai/mistral-vibe)
- [OpenCode: dostawcy modeli](https://opencode.ai/docs/providers)
- [Zed: model lokalny](https://zed.dev/docs/ai/use-a-local-model) i [agenci zewnętrzni](https://zed.dev/docs/ai/external-agents)
- [JetBrains: ACP](https://www.jetbrains.com/help/ai-assistant/acp.html)
- [Agent Client Protocol](https://agentclientprotocol.com)
- [Bielik](https://huggingface.co/speakleash)
