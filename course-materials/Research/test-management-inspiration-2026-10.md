# Zarządzanie testami: inspiracje do własnego małego narzędzia

Sprawdzenie źródeł: 26.10.2026. Na warsztacie nie potrzebujesz konta w żadnym z poniższych systemów. To przykłady do porównania sposobu pracy i wyboru kilku użytecznych funkcji, nie projekt kopiowania całej platformy.

| Narzędzie | Co warto obejrzeć | Co możemy uprościć we własnej aplikacji |
|---|---|---|
| [TestRail](https://support.testrail.com/hc/en-us/articles/7076810203028-Introduction-to-TestRail) | Biblioteka przypadków, zestawy, wykonania i przegląd wyników projektu | Formularz krok/oczekiwany wynik oraz wybór przypadków do wykonania. Rozbudowane przypisania i powiadomienia zostawiamy na później. |
| [Qase](https://docs.qase.io/en/articles/14442829-qase-concepts) | Rozdzielenie projektu, przypadku, planu, wykonania i wyniku. Historyczne wykonanie zachowuje obraz przypadku z czasu testu. | Osobny zapis wyniku i historii, zamiast nadpisywania poprzedniego testu. Pierwszy run może powstać bez osobnej warstwy planów. |
| [Kiwi TCMS](https://github.com/kiwitcms/Kiwi/blob/master/docs/source/guide/introduction.rst) | Model plan -> przypadki -> run -> wykonania, wyniki powiązane z konkretnym buildem | Jawna wersja/build i środowisko przy wynikach. Rozbudowane raportowanie oraz integracje nie są warunkiem pierwszego ćwiczenia. |

Opis ręcznego przebiegu i dashboardu można porównać z [Qase Test Runs](https://docs.qase.io/en/articles/5563702-test-runs), a formularze przypadków i zapis historycznych wyników z [TestRail Test Plans](https://www.testrail.com/test-plans/). Nie utożsamiamy listy funkcji dostawcy z zakresem naszej aplikacji.

## Przykładowe zastosowania zespołu QA

- Zebrać przypadki regresji małego obszaru aplikacji, a potem zapisać obserwacje z konkretnej wersji zamiast dopisywać wynik do samego opisu przypadku.
- Porównać ten sam przepływ przez UI i API. W raporcie zachować rodzaj sprawdzenia oraz rzeczywisty dowód; poprawny HTTP nie dowodzi jeszcze poprawnego interfejsu.
- Oddzielić awarię środowiska lub brak urządzenia Android od rzeczywistego błędu aplikacji.
- Wybrać kilka Failed/Blocked do ponownej próby i zachować wcześniejszy wynik.

[Punkt wyjścia: Test Notebook](../ideas/test-notebook-idea.md) jest krótkim opisem pomysłu do dopracowania z grupą. Nie jest gotowym PRD.

## Skills i narzędzia agenta

- [legacy-codebase-workflows](https://github.com/EdukeyTeam/agent-toolbox/tree/main/skills/legacy-codebase-workflows): procedura rozpoznania kodu, źródłowych dowodów, testów charakteryzacyjnych i małych zmian. Pasuje do jFTP, ale nie jest bazą przypadków testowych.
- [webapp-testing autorstwa Anthropic](https://github.com/anthropics/skills/blob/main/skills/webapp-testing/SKILL.md): przykład pracy z lokalną aplikacją webową przez Python/Playwright. To dodatkowa ścieżka, nie warunek dla zespołu pracującego w Javie i nie narzędzie do natywnego Swing/Androida. Przed użyciem sprawdź źródło, zależności, politykę instalacji i obsługę przez wybranego agenta. Nie instalujemy niczego automatycznie.
- Własny mały skill można opracować razem: wymaganie -> tabela przypadków albo nieudany test -> raport defektu. Najpierw ręcznie sprawdzamy procedurę na rzeczywistym fragmencie kodu, potem oceniamy wynik w świeżej sesji agenta.

System zarządzania testami przechowuje dane i historię. Skill opisuje procedurę pracy agenta. MCP lub wrapper REST daje dostęp do konkretnego uruchomionego systemu; sam nie zastępuje ani przypadków, ani uprawnień, ani walidacji wyniku.
