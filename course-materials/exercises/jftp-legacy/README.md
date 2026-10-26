# jFTP: testowanie i poznawanie istniejącej aplikacji Java

Pracujemy na prawdziwym kliencie FTP/FTPS z interfejsem Swing. Celem jest analiza zachowania, projektowanie scenariuszy, dodatkowe testy charakteryzujące, dokumentacja i mała refaktoryzacja. Nie budujemy nowej aplikacji.

**Baza na 26 października 2026:** [LucasMatuszewski/jftp-modernization](https://github.com/LucasMatuszewski/jftp-modernization), gałąź `main`, przypięty commit `c0e8a0fa7f8be99907ccdc50ac2bdc4ad7a98a86`. Pracuj w osobnym katalogu aplikacji, obok materiałów. Szczegóły pochodzenia i odnośniki do kodu: [SOURCES.md](SOURCES.md).

## Co jest już dostarczone

- Maven Wrapper, konfiguracja kompilacji z `release=8` oraz profil `windows-bootstrap` omijający stary adapter Apple. Nie jest to nowe zadanie uczestnika.
- JUnit 4.13.2, Surefire 3.6.0 oraz 3 testy w `StartupStateTest`: pierwszy zapis preferencji, stan odczytany w osobnym JVM, zasoby startowe.
- Fixture `StartupStateProbe`, który tworzy syntetyczny profil do uruchomienia okna bez własnych preferencji i danych logowania.

**Nowa praca uczestników** zaczyna się w [WORKSHOP.md](WORKSHOP.md). Dostarczone testy nie obejmują bezpośrednio macierzy filtrów lokalnych, kontraktu sortowania ani parsowania poleceń po zalogowaniu. Ich ponowne uruchomienie jest kontrolą bazy, nie nowym testem ani etapem Red.

## Gotowość w 15 minut

0-5 minut: wybierz katalog roboczy i sprawdź Git, JDK 21 oraz możliwość otwarcia projektu Maven w IntelliJ IDEA. Używaj dostępnego GitHub Copilot w IDE lub Copilot CLI. Dostęp do Copilota przygotowuje trener. Pobieranie narzędzi odbywa się wyłącznie zgodnie z zasadami komputera. Nie zakładamy praw administratora ani przygotowanej VM.

5-10 minut: pobierz i otwórz aplikację. Polecenia wykonuj kolejno w terminalu, w wybranym katalogu nadrzędnym:

```sh
git clone https://github.com/LucasMatuszewski/jftp-modernization.git jftp-qa
```

```sh
cd jftp-qa
```

```sh
git checkout --detach c0e8a0fa7f8be99907ccdc50ac2bdc4ad7a98a86
```

```sh
git rev-parse HEAD
```

Ostatni wynik musi być pełnym hashem podanym wyżej. Przed własnymi zmianami utwórz lokalną gałąź ćwiczenia:

```sh
git switch -c warsztat/testy-jftp
```

Jeśli taka gałąź już istnieje, wybierz inną nazwę; nie nadpisuj cudzych zmian.

W IntelliJ wybierz Open i `pom.xml` z katalogu `jftp-qa`, otwórz go jako projekt i ustaw JDK 21 jako Project SDK oraz JDK importera/runnera Maven. Przeczytaj aplikacyjne `AGENTS.md`, `README.md` i `docs/windows-startup.md`. Nie aktywuj automatycznie znalezionych konfiguracji agentów, hooków, MCP ani dostawców modeli.

10-15 minut: sprawdź dostarczony test bazowy. Wrapper może pobrać Maven 3.10.0 i zależności do cache użytkownika. Przed zajęciami sprawdź na stanowisku dostęp do zadeklarowanego repozytorium jmethods.com/mvn-repo oraz rozszerzenia wagon-ftp; firmowy mirror/proxy może je blokować. Nie kopiuj binarnego cache jako rozwiązania blokady. Pobieranie wymaga zgody wynikającej z zasad komputera; nie obchodź blokad proxy, certyfikatów ani instalacji.

Windows, PowerShell, w katalogu aplikacji:

```powershell
java -version
```

```powershell
.\mvnw.cmd -B -ntp -Dtest=StartupStateTest test
```

Linux/WSL, bash, w katalogu aplikacji:

```sh
java -version
```

```sh
sh ./mvnw -B -ntp -Pwindows-bootstrap -Dtest=StartupStateTest test
```

W Linux/WSL jawnie wybieramy istniejący profil, aby wyłączyć `OSXAdapterOld.java`. To obejście kompilacji dla laboratorium, nie dowód obsługi wszystkich systemów. Nie stosuj go jako deklaracji zgodności z macOS. WSL bez dostępu do pulpitu wystarcza do tych testów, lecz nie do pełnej kontroli okien Swing. macOS nie został zweryfikowany w tym ćwiczeniu.

Wynik bazowy: 3 wykonane testy, 0 failures, 0 errors, 0 skipped. Zapisz własny wynik i wersje. Przy braku JDK, sieci, uprawnień lub działającego importu przejdź do wariantu bez instalacji. Naprawa środowiska nie jest etapem Red.

## Co rzeczywiście sprawdzono

| Warstwa | Dowód i ograniczenie |
| --- | --- |
| Źródła | POM, definicje, wywołania i oba pliki testowe odczytano na przypiętym commicie. |
| Kompilacja 26.10.2026 | WSL/Linux, Temurin 21.0.13+9, zewnętrzny Maven 3.10.0, jawny profil `windows-bootstrap`: kompilacja przeszła. Zachowano ostrzeżenia o starym celu Java 8, deprecated i unchecked. |
| Testy 26.10.2026 | `StartupStateTest`: 3 wykonane, 0 failures, 0 errors, 0 skipped. Uruchomiono Maven dostarczony trenerowi, nie ponawiano dziś pobrania przez wrapper. |
| Start GUI | Nie wykonywano nowej kontroli GUI 26.10.2026. Repozytorium aplikacji zawiera wcześniejsze dowody Windows 11/JDK 21.0.10 w `docs/windows-startup-evidence/`; nie dowodzą działania na komputerze uczestnika. |
| Sieć i dystrybucja | Nie sprawdzono transferów FTP/FTPS, pakietów instalacyjnych ani starych launcherów dystrybucji. |

Pierwsze próby lokalne zatrzymały się na ścieżce ustawień, dostępie do cache i brakującej zależności offline. Dopiero przebieg z dostępem do zadeklarowanych zależności zakończył się powyższym wynikiem. Te problemy nie były defektami zachowania aplikacji.

## Osobne uruchomienie okna na Windows

To dodatkowa kontrola, po testach i przy dostępie do pulpitu. Każde polecenie wykonaj w tej samej sesji PowerShell w katalogu aplikacji. Przerwij po niezerowym kodzie zakończenia przygotowania. Używamy nowego katalogu tymczasowego, bo klasy legacy zapamiętują `user.home` przy ładowaniu.

```powershell
.\mvnw.cmd -B -ntp test org.apache.maven.plugins:maven-dependency-plugin:3.8.1:copy-dependencies -DincludeScope=test
```

```powershell
if ($LASTEXITCODE -ne 0) { throw 'Testy lub przygotowanie zależności nie powiodły się' }
```

```powershell
$profilePath = Join-Path ([System.IO.Path]::GetTempPath()) ('jftp-qa-' + [guid]::NewGuid().ToString('N'))
```

```powershell
New-Item -ItemType Directory -Path $profilePath | Out-Null
```

```powershell
java "-Duser.home=$profilePath" -cp 'target\test-classes;target\classes;target\dependency\*' com.myjavaworld.jftp.StartupStateProbe desktop-seed
```

```powershell
if ($LASTEXITCODE -ne 0) { throw 'Nie uruchamiaj GUI: przygotowanie profilu nie powiodło się' }
```

```powershell
java "-Duser.home=$profilePath" -cp 'target\classes;target\dependency\*' com.myjavaworld.jftp.JFTPApplication
```

Fixture wyłącza automatyczne sprawdzanie aktualizacji w syntetycznych preferencjach, ustawia angielski interfejs i tworzy `files/hello.txt`. Zachowaj istniejący język aplikacji; po polsku pisz raport. Sprawdź widoczne okno, lokalny plik, otwarcie i anulowanie dialogu oraz normalne zamknięcie. Zrób zrzut ekranu i zapisz błędy. Nie łącz się z serwerem FTP.

**Swing jest natywnym GUI. Selenium i Playwright nie sterują tym oknem.** Przeglądarka może służyć do czytania źródeł; kontrola aplikacji wymaga człowieka na pulpicie lub zatwierdzonych narzędzi natywnych. jFTP nie dostarcza w tym ćwiczeniu endpointów HTTP.

## Wariant bez instalacji

Otwórz przypięte źródła przez linki w [SOURCES.md](SOURCES.md), przygotuj scenariusze JFTP-QA01v1 i kartę dokumentacyjną JFTP-QA04v1. Agent może analizować wyłącznie udostępniony publiczny kod. Testy zapisz jako projekt do późniejszego uruchomienia; oznacz je jako niewykonane. Przy blokadzie GitHub pracuj w parze na zatwierdzonym stanowisku lub na kopii źródeł dostarczonej przez trenera z tym samym hashem. Nie instaluj samodzielnie dodatkowego stosu.

Skill `legacy-codebase-workflows` jest opcjonalną instrukcją pracy agenta. Załaduj go jawnie tylko w środowisku, w którym jest już dostępny. W przeciwnym razie użyj procedury podanej w promptach; nie instaluj go ani dodatkowych mapperów automatycznie.
