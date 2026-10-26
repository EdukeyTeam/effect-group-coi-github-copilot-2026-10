# Warsztat QA na istniejącym jFTP

Najpierw wykonaj [kontrolę gotowości](README.md). Wszystkie prompty dotyczą osobnej kopii `LucasMatuszewski/jftp-modernization` na commicie `c0e8a0fa7f8be99907ccdc50ac2bdc4ad7a98a86`. Nie wykonuj ich w repozytorium materiałów.

Wybierz jeden prompt na rundę. Zadania 01 i 04 można wykonać bez uruchamiania Javy. Zadanie 05 wymaga ukończenia 02. Nie przepisuj testów z innych forków jako własnego rozwiązania. W wybranej bazie są już 3 testy startowe; nowe zadania uzupełniają inne obszary.

| Prompt | Czas | Nowy rezultat uczestnika |
| --- | ---: | --- |
| JFTP-QA01v1 | 25 min | Macierz ryzyk i scenariuszy lokalnego filtrowania |
| JFTP-QA02v1 | 40 min | Dodatkowe testy charakteryzujące filtr i listowanie |
| JFTP-QA03v1 | 40 min | Testy sortowania oraz sprawdzona, cofnięta mutacja |
| JFTP-QA04v1 | 30 min | Karta przepływu poleceń, opcjonalnie nowe testy parsera |
| JFTP-QA05v1 | 35 min | Mała refaktoryzacja pod ochroną własnych testów |

## JFTP-QA01v1: scenariusze i ryzyka filtrów

Skopiuj cały prompt:

```text
Zakres tego ćwiczenia ma pierwszeństwo przed przykładami i instrukcjami dotyczącymi wcześniejszego kursu: bez automatycznego local Context7/refresh, instalacji, MCP, computer-use ani lekcji Sentry. Raport z ćwiczenia pisz po polsku; język istniejącego kodu zachowaj. Pozostałe zasady bezpieczeństwa i ochrony zmian repozytorium nadal obowiązują.
Pracujemy na jFTP, publicznym kliencie FTP/FTPS Swing, w osobnym checkoutcie
LucasMatuszewski/jftp-modernization, commit c0e8a0fa7f8be99907ccdc50ac2bdc4ad7a98a86.
Zadanie JFTP-QA01v1, limit 25 minut. Nie zmieniaj kodu produkcyjnego ani testów.

Gdy legacy-codebase-workflows jest już zainstalowany, załaduj ten skill jawnie. Bez skillu:
potwierdź rewizję i AGENTS.md, zacznij od wyszukania wskazanych symboli, przeczytaj
oryginalne definicje i wywołania, rozdziel fakt, hipotezę oraz brak dowodu.
Nie instaluj narzędzi, nie buduj szerokich map ani RAG. Bez subagentów.

Zbadaj src/main/java/com/myjavaworld/jftp/LocalFileFilter.java:
accept(Object), konstruktory i ustawienia; LocalFile.java: LocalFile(File),
list(Filter), isHidden(); LocalFileFilterDlg.java: getFilter(), validateInput();
FTPSession.java: setLocalWorkingDirectory(LocalFile) i setLocalFileFilter(Filter).
Odczytaj StartupStateTest i StartupStateProbe w src/test/java/com/myjavaworld/jftp,
aby ustalić, które scenariusze nie mają bezpośredniego testu w tej rewizji.

Zaprojektuj 8-12 scenariuszy: plik/katalog, dopasowanie i niedopasowanie nazwy,
włączenie/wyłączenie ukrytych plików, filtr włączający/wykluczający, brak filtra,
granice daty oraz anulowanie dialogu. Wybierz co najmniej trzy interakcje warunków.
Nie wpisuj zgadywanego wyniku: każdy przewidywany wynik uzasadnij kodem albo oznacz
jako pytanie do eksperymentu. Sprawdź rzeczywiste pochodzenie Filter, RegexFilter
i DateFilter; nie zakładaj, że ich definicje są w repozytorium aplikacji.

Zapisz docs/qa/JFTP-QA01v1.md: ID, ryzyko, stan początkowy, dane syntetyczne,
czynności, oczekiwany wynik, źródło wyroczni, warstwa testu i ograniczenie OS.
Użyj cytowań oryginalny plik:wiersze + symbol + commit, nie samego streszczenia
agenta ani mapy. Dla zależności podaj źródło/artefakt i ograniczenie weryfikacji.
Nie wymyślaj HTTP/API. Swing wymaga pulpitu i kontroli natywnej, nie Selenium.
Bez obcych serwerów, prawdziwych profili, haseł i danych użytkowników.

Kontrola końcowa: każda pozycja ma obserwowalny wynik lub jawne pytanie;
odróżnij plan testu od wykonanego testu. Bez instalacji wykonaj tę samą analizę
na przypiętych źródłach przez przeglądarkę. Oddaj raport, nie odpowiedzi w kodzie.
Nie commituj ani nie pushuj.
```

## JFTP-QA02v1: dodatkowe testy charakteryzujące

```text
Zakres tego ćwiczenia ma pierwszeństwo przed przykładami i instrukcjami dotyczącymi wcześniejszego kursu: bez automatycznego local Context7/refresh, instalacji, MCP, computer-use ani lekcji Sentry. Raport z ćwiczenia pisz po polsku; język istniejącego kodu zachowaj. Pozostałe zasady bezpieczeństwa i ochrony zmian repozytorium nadal obowiązują.
Pracujemy na jFTP w osobnym checkoutcie LucasMatuszewski/jftp-modernization,
commit c0e8a0fa7f8be99907ccdc50ac2bdc4ad7a98a86. Zadanie JFTP-QA02v1, 40 minut.
Celem jest poznanie aktualnego zachowania, bez jego poprawiania.

Jawnie użyj legacy-codebase-workflows, jeśli jest dostępny. W przeciwnym razie:
potwierdź rewizję i AGENTS.md, przeczytaj oryginalne definicje oraz wywołania,
cytuj plik:wiersze, symbol i commit, oddziel obserwację od hipotezy.
Nie instaluj skillu, mapperów, RAG ani zależności poza zatwierdzoną konfiguracją.
Bez subagentów, połączeń FTP, prawdziwego profilu i danych logowania.

Przeczytaj src/test/AGENTS.md, docs/regression-test-design.md, docs/windows-startup.md,
pom.xml i istniejące StartupStateTest/StartupStateProbe. Potwierdź 3 testy bazy.
Brak kompilacji lub zależności jest blokadą środowiska, nie Red.

Zakres źródeł src/main/java/com/myjavaworld/jftp/: LocalFileFilter.accept(Object),
LocalFile.list(Filter), LocalFile(File), LocalFile.isHidden(), a jako wywołanie
FTPSession.setLocalWorkingDirectory(LocalFile). Sprawdź deklaracje/importy
RegexFilter i DateFilter oraz rzeczywiste sygnatury w zależności FTPAPI 3.0.0
przed użyciem. Gdy brakuje źródła API, zgłoś brak zamiast wymyślać konstruktor.

Utwórz src/test/java/com/myjavaworld/jftp/LocalFilteringQAExerciseTest.java.
Wybierz co najmniej 5 nowych przypadków z macierzy QA01: uwzględnij interakcję
filtra nazwy z katalogiem, wykluczenie oraz co najmniej jedną granicę daty.
Użyj rzeczywistych plików w nowym katalogu tymczasowym. Ustaw datę przed utworzeniem
LocalFile; sprawdź, jak klasa odczytuje metadane. Zweryfikuj faktyczny atrybut hidden
na danym OS i rozdziel brak możliwości jego ustawienia od błędu aplikacji.
Nie testuj prywatnych pól, kolejności wywołań ani kolejności listowania bez kontraktu.
Izoluj stan przed załadowaniem klas; wzoruj granice izolacji na dostarczonych testach.

Uruchom własną klasę przez wrapper z -Dtest=LocalFilteringQAExerciseTest test;
w bash użyj sh ./mvnw i profilu -Pwindows-bootstrap, na Windows .\mvnw.cmd.
Potem uruchom test jako pełne test. Zapisz komendy, liczby testów i pominięcia.
Nowy test charakteryzujący może od razu przejść na oryginalnym kodzie. Nie zmieniaj
asercji, aby ukryć zaskakujący wynik, i nie ogłaszaj naprawy defektu.

Oddaj diff nowych testów i docs/qa/JFTP-QA02v1.md z wyroczniami oraz dowodami.
Przy dostępnym pulpicie porównaj jeden przypadek przez realny lokalny panel Swing,
zapisz kroki i zrzut; bez pulpitu oznacz GUI jako niewykonane. Selenium/Playwright
nie sterują tym oknem. Bez możliwości uruchomienia oddaj projekt testów z adnotacją
niewykonane, bez deklaracji Green. Nie zmieniaj produkcji, nie commituj i nie pushuj.
```

## JFTP-QA03v1: sortowanie i siła asercji

```text
Zakres tego ćwiczenia ma pierwszeństwo przed przykładami i instrukcjami dotyczącymi wcześniejszego kursu: bez automatycznego local Context7/refresh, instalacji, MCP, computer-use ani lekcji Sentry. Raport z ćwiczenia pisz po polsku; język istniejącego kodu zachowaj. Pozostałe zasady bezpieczeństwa i ochrony zmian repozytorium nadal obowiązują.
Zadanie JFTP-QA03v1, limit 40 minut, osobny checkout jftp-modernization,
commit c0e8a0fa7f8be99907ccdc50ac2bdc4ad7a98a86. Sprawdź AGENTS.md i stan drzewa.
Jawnie użyj legacy-codebase-workflows, jeżeli jest dostępny. Bez niego stosuj
analizę wskazanych źródeł i wywołań, cytowania plik:wiersze + symbol + rewizja,
rozdzielenie obserwacji, hipotez i braków. Bez instalacji i subagentów.

Przeczytaj src/main/java/com/myjavaworld/jftp/LocalFileComparator.java:
compare(Object,Object), compareByName, compareBySize, compareByDate, compareByType;
LocalPane.java: setData(LocalFile,LocalFile[]) oraz sort(int);
LocalFile.java: konstruktor, getDisplayName(), getType(), getSize(), getLastModified().
Prywatne metody czytaj jako implementację, testuj publiczne compare i wynik sortowania.
Sprawdź istniejące testy: sam test startu nie dowodzi poprawności sortowania.

Zapisz plan, następnie dodaj src/test/java/com/myjavaworld/jftp/LocalSortingQAExerciseTest.java.
Wybierz co najmniej 5 obserwowalnych przypadków: rozmiary równe i różne, daty równe
i różne, katalog i plik, kierunek rosnący/malejący. Sprawdź znak compare, równość,
antysymetrię i przynajmniej jeden wynik sortowania listy trzech elementów.
Twórz syntetyczne pliki przed LocalFile. Zapisz locale i OS; nie zakładaj, że opis
typu lub nazwa widoczna w systemie są wszędzie identyczne. Nie narzucaj nowego
porządku, gdy aktualne zachowanie jest zaskakujące.

Uzyskaj Green na oryginalnym kodzie. Następnie, wyłącznie przy rozpoznanym czystym
zakresie pliku, zachowaj dokładne bajty LocalFileComparator.java i jego hash.
Wprowadź jedną małą kontrolowaną mutację zmieniającą wynik porównania w wybranej
gałęzi. Uruchom nowy test i pokaż porażkę z powodu asercji zachowania, bez błędu
kompilacji lub fixture. Przywróć oryginalne bajty w bloku finally także po awarii.
Nie stosuj reset --hard ani odtwarzania całego drzewa. Sprawdź hash i diff po
przywróceniu. Ponownie uruchom własną klasę i pełne testy.

Użyj wrappera: Windows .\mvnw.cmd, bash sh ./mvnw -Pwindows-bootstrap;
wybór klasy: -Dtest=LocalSortingQAExerciseTest test; pełna kontrola: test.
Oddaj testy i docs/qa/JFTP-QA03v1.md z trzema wynikami: Green, wykryta mutacja,
Green po odtworzeniu. Mutacja jest eksperymentem siły testu, nie odkrytym defektem.
Nie pozostawiaj mutacji ani nowych reguł sortowania w kodzie produkcyjnym.
Przy dostępnym GUI porównaj sortowanie kolumny przez dwa kliknięcia nagłówka
i zrzut ekranu. Bez GUI zaznacz brak kontroli natywnego Swing.
Bez możliwości wykonania testów oddaj tylko plan i nie mutuj kodu.
Bez sieci FTP, danych użytkowników, commitów i pushowania.
```

## JFTP-QA04v1: dokumentacja przepływu poleceń

```text
Zakres tego ćwiczenia ma pierwszeństwo przed przykładami i instrukcjami dotyczącymi wcześniejszego kursu: bez automatycznego local Context7/refresh, instalacji, MCP, computer-use ani lekcji Sentry. Raport z ćwiczenia pisz po polsku; język istniejącego kodu zachowaj. Pozostałe zasady bezpieczeństwa i ochrony zmian repozytorium nadal obowiązują.
Zadanie JFTP-QA04v1, limit 30 minut. Pracuj na osobnym checkoutcie
LucasMatuszewski/jftp-modernization, c0e8a0fa7f8be99907ccdc50ac2bdc4ad7a98a86.
Użyj jawnie legacy-codebase-workflows, jeśli dostępny; bez niego sprawdź AGENTS.md,
rewizję, oryginalne definicje, wywołania i testy, z cytowaniami plik:wiersze,
symbol i commit. Bez instalacji, szerokich map, RAG i subagentów.

Udokumentuj wyłącznie przepływ dodatkowych poleceń po zalogowaniu:
src/main/java/com/myjavaworld/jftp/RemoteHost.java: setCommands(String),
setCommands(String[]), getCommands(), getCommandsAsString();
ConnectionDlg.java: connectButtonPressed(); FavoritePropertiesDlg.java: okButtonPressed();
FTPSession.java: connect(RemoteHost) i fragment używający getCommands().
Potwierdź nazwy metod w źródle przed cytowaniem i wskaż dokładne miejsce wywołania.
Sprawdź StartupStateTest/StartupStateProbe: czy testują parser poleceń bezpośrednio?

Zapisz docs/qa/JFTP-QA04v1.md: wejście z dialogu, typy i przeciążenia, rozdzielanie
linii, białe znaki, zachowanie kolejności, zapis tekstowy oraz miejsce wykonania.
Każdą istotną tezę poprzyj krótkim cytatem i linkiem do oryginalnego źródła na
przypiętym commicie. Oddziel udowodnione przetwarzanie konfiguracji od niewykonanego
logowania i wykonania na FTP. Nie wymyślaj HTTP, klienta REST ani funkcji SFTP.

Zaprojektuj tabelę co najmniej 6 przypadków: tekst pusty, puste linie, same spacje,
LF/CRLF/CR, powtórzone polecenie, spacje w linii. Zapisz, które wejścia są osiągalne
z dialogu, a które są tylko pytaniem o kontrakt publicznej metody. Nie nazywaj
każdego null-pointera defektem bez potwierdzenia wymagań i wywołania.

Jeśli środowisko działa i pozostaje czas, dodaj własne testy charakteryzujące
w src/test/java/com/myjavaworld/jftp/RemoteHostCommandsQAExerciseTest.java.
Nie nawiązuj połączenia, nie mockuj serwera i nie wykonuj poleceń sieciowych.
Użyj wyłącznie obiektu konfiguracji i publicznych wyników. Przed kodem przeczytaj
instrukcje testów, uruchom bazę, potem własną klasę i pełne testy przez wrapper
(Windows .\mvnw.cmd; bash sh ./mvnw -Pwindows-bootstrap).
Oddaj kartę, źródła, luki i ewentualne komendy z liczbami testów. Bez instalacji
wykonaj tylko dokumentację i projekt przypadków na źródłach w przeglądarce.
Nie zmieniaj produkcji, nie commituj ani nie pushuj.
```

## JFTP-QA05v1: mała refaktoryzacja z zachowaniem działania

```text
Zakres tego ćwiczenia ma pierwszeństwo przed przykładami i instrukcjami dotyczącymi wcześniejszego kursu: bez automatycznego local Context7/refresh, instalacji, MCP, computer-use ani lekcji Sentry. Raport z ćwiczenia pisz po polsku; język istniejącego kodu zachowaj. Pozostałe zasady bezpieczeństwa i ochrony zmian repozytorium nadal obowiązują.
Zadanie JFTP-QA05v1, 35 minut, osobny checkout jftp-modernization oparty na
c0e8a0fa7f8be99907ccdc50ac2bdc4ad7a98a86 z własnymi testami z JFTP-QA02v1.
Użyj jawnie legacy-codebase-workflows, jeśli dostępny; bez niego: AGENTS.md,
oryginalne definicje i wywołania, cytowania plik:wiersze + symbol + commit,
fakty oddzielone od hipotez. Nie instaluj skillu, narzędzi ani nowego stosu.

Przeczytaj aplikacyjne docs/qa/JFTP-QA05v1.md i odpowiednie instrukcje.
Przed kodem zapisz w istniejącym planie aplikacji mały zakres bez zmiany zachowania
i uzgodnij go z uczestnikiem/trenerem. Jeśli zasady checkoutu wymagają wcześniejszej
zgody lub dodatkowych testów, wykonaj to wymaganie przed zmianą. Nie twórz nowego
pełnego PRD dla całej aplikacji ani nie zmieniaj programu szkolenia.

Zakres produkcyjny: tylko src/main/java/com/myjavaworld/jftp/LocalFileFilter.java,
metoda public boolean accept(Object). Przeczytaj też LocalFile.list(Filter),
LocalFileFilterDlg.getFilter() i FTPSession.setLocalWorkingDirectory(LocalFile).
Wybierz jedną powtarzalność lub nieczytelność w accept i zaproponuj małe uproszczenie.
Zachowaj podpisy, kolejność obserwowalnych sprawdzeń, wyjątki, obsługę katalogów,
hidden, filtrów i wykluczenia. Bez zmiany zależności, formatu danych i GUI.

Warunek wejścia: własne nowe testy z QA02 przechodzą na niezmienionej produkcji,
obejmują zmieniany warunek, a kontrolowana mutacja została wykryta i cofnięta
z przywróceniem identycznych bajtów. Jeśli brakuje dowodu siły testu, wykonaj go
jak w QA03 z LocalFileFilter.java; przywracaj w finally, bez resetu całego drzewa.
Dostarczone 3 testy startu nie wystarczą jako ochrona tej refaktoryzacji.

Wprowadź minimalną refaktoryzację, uruchom te same testy i całość przez wrapper
(Windows .\mvnw.cmd; bash sh ./mvnw -Pwindows-bootstrap). Nie zmieniaj asercji,
aby zaakceptować regresję. Oddziel ewentualny rzeczywisty defekt do raportu;
nie naprawiaj go przy okazji. Przejrzyj diff i wymagane aktualizacje dokumentacji
checkoutu. Zachowaj kodowanie i zakończenia linii oryginalnego pliku.

Na prawdziwym pulpicie uruchom aplikację z syntetycznym profilem według instrukcji
startowej. Sprawdź w panelu lokalnym przypadek dopasowany, niedopasowany i katalog;
zapisz kroki, wyniki, log i zrzuty. Bez Selenium/Playwright i zewnętrznego FTP.
Brak pulpitu lub działających testów oznacza: oddaj analizę i projekt diffu,
nie wprowadzaj refaktoryzacji jako ukończonej. Dokumentacja to nadal użyteczny wynik.
Oddaj docs/qa/JFTP-QA05v1.md, diff, porównanie przed/po i dowody. Bez subagentów,
realnych profili lub haseł, commitów i pushowania.
```

## Szablon dowodów

Wklej i uzupełnij w raporcie w checkoutcie aplikacji. Nie wklejaj sekretów, danych uczestników ani zawartości własnego katalogu domowego.

```text
Zadanie i wersja:
Commit bazowy i lista własnych zmian:
OS, JDK, Maven, locale:
Pytanie / uzgodnione zachowanie:
Zakres oryginalnych źródeł: plik:wiersze, symbol, rewizja:
Dane syntetyczne i izolacja:
Scenariusze / wyrocznia / luki:
Kompilacja: dokładna komenda, wynik, ostrzeżenia lub niewykonane:
Testy: dokładna komenda, wykonane/failures/errors/skipped lub niewykonane:
Mutacja: hipoteza, porażka asercji, przywrócenie hash, Green lub nie dotyczy:
GUI: osobna komenda startu, widoczne okno, kroki, wynik, zrzut lub niewykonane:
Co potwierdzono, co jest hipotezą, czego nie sprawdzono:
Diff i pozostałe ograniczenia:
```
