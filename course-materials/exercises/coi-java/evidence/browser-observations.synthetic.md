# Syntetyczny zapis eksploracji

To scenariusz do analizy, nie wykonanie browsera. Własny raport musi rozróżniać te dwa rodzaje dowodu.

| Krok | Syntetyczna obserwacja | Pytanie do weryfikacji na prawdziwej stronie |
|---|---|---|
| Otwórz / | Nagłówek "Wizyty - dane syntetyczne" | Czy strona rzeczywiście wystartowała na loopback? |
| Pobierz terminy | Dwa sloty, SYN-02 wyłączony | Czy API ma 200 i zgodny JSON? |
| Wybierz SYN-01 | selected-slot = SYN-01 | Czy wybór działa myszą i klawiaturą? |
| Wpisz 0 | HTML min=1 może zatrzymać submit | Co zrobi niezależne żądanie API? |
| Wpisz 1 i wyślij | HTTP 501 i komunikat o niegotowym zapisie | Czy odróżniono jawny brak od przypadkowej awarii? |
| Zamknij | Brak rzeczywistego procesu w tym materiale | Czy zamknięto własny rzeczywisty fixture po ćwiczeniu? |
