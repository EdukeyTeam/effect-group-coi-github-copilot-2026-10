# Dowody syntetyczne do analizy

Wszystkie pliki w tym katalogu zostały przygotowane jako **fikcyjne materiały ćwiczeniowe**. Nie pochodzą z uruchomienia testów, CI lub urządzenia COI. Daty i czasy są elementem scenariusza. Nie używaj ich jako metryk grupy, potwierdzenia niestabilności ani świeżego przechwycenia odpowiedzi do mocka.

| Plik | Do czego służy | Czego nie dowodzi |
|---|---|---|
| slots.synthetic.json | Klasy danych, propozycja asercji kontraktu | Rzeczywistego capture HTTP |
| slots-dom.synthetic.html | Analiza stanu DOM po załadowaniu | Działającej przeglądarki lub wykonanej akcji |
| BrittleSlotTest.java.txt | Jawnie wadliwy, nieaktywny fragment Selenium | Samodzielnego testu lub dobrego wzorca |
| flaky-ci.synthetic.log | Różne symptomy synchronizacji i lokatora | Pomiaru flaky rate lub źródła wady produktu |
| triage-ci.synthetic.log | Rozdzielenie błędu środowiska i brakującej funkcji | Dostawcy/wersji CI klienta |
| weak-test.synthetic.diff | Review osłabienia asercji | Zastosowanej poprawki |
| android-hierarchy.synthetic.xml | Analiza natywnego drzewa UI | Istnienia urządzenia, APK czy sesji |
| android-ci.synthetic.log | Hipotezy o strategii lokatora | Wykonanego testu Android |
| browser-observations.synthetic.md | Wariant eksploracji bez sterowania browserem | Ręcznej kontroli prawdziwej strony |

Żaden plik z tego katalogu nie jest automatycznie stosowany ani odkrywany jako test Maven. Nowe testy dodawaj świadomie w odpowiednim źródle, według wybranego ćwiczenia i SPEC.md. Własne rzeczywiste dowody zapisuj w `target/evidence/`, poza publikowaną biblioteką.
