# Zgodność przyszłego adaptera REST

Wypełnij na podstawie zatwierdzonej dokumentacji danej instalacji. Nie wpisuj do publicznego repozytorium prywatnych adresów, tokenów, nazw użytkowników, treści ticketów i danych klientów.

| Warunek | Co trzeba potwierdzić | Wynik tego ćwiczenia |
|---|---|---|
| Produkt i wydanie | Jira/Bitbucket, Server/Data Center, wersja i oficjalna dokumentacja odpowiadająca tej wersji | Nie ustalono, nie zgaduj |
| Zasób i metoda | Osobno odczyt issue/PR, diff, komentarz; obsługiwany path, request body i odpowiedź | Nie ustalono |
| Sieć | AirLock/proxy, TLS, allowlist i zatwierdzony punkt uruchomienia | Brak połączeń |
| Tożsamość i uprawnienia | Zatwierdzony mechanizm auth, minimalny zakres, rotacja; bez wartości sekretów | Nie ustalono |
| Odpowiedź źródłowa | Bezpieczne pełne przechwycenie testowego żądania na syntetycznym zasobie przed mockiem | Nie wykonano |
| Paginacja i rozmiar | Udokumentowane limity i znaczniki kolejnych stron; nie obcinaj diffu bez raportu | Do mapowania |
| Zmiana stanu | Odczyt i zapis osobno, idempotencja, duplikaty, uprawnienie do komentarza | Zapis wyłączony |
| Błędy | Dokumentowane kody i body, auth, konflikty, ograniczenia tempa, Retry-After tylko jeśli wspierany | Do mapowania |
| Review i audyt | Człowiek sprawdza wynik; log bez danych/sekretów; osobna zgoda na wysyłkę | Lokalny plik |
| CI | Rzeczywisty runner, komenda, artefakty i sposób przekazania wyniku | Vendor nieustalony |
| MCP / chmura | Czy w ogóle dozwolone i potrzebne | Niewymagane |

Punkty startowe producenta: [Jira platform REST](https://developer.atlassian.com/server/jira/platform/rest-apis/), [Bitbucket REST](https://developer.atlassian.com/server/bitbucket/rest/). To katalogi dokumentacji, nie kontrakt konkretnej instalacji. Brak danych wersji oznacza zatrzymanie integracji sieciowej i dalszą pracę na pliku.
