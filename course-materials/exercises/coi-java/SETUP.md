# Start: dostęp, Maven i przeglądarka

Komputer COI z ograniczonymi prawami jest właściwym miejscem pracy. Sprawdź to, co faktycznie masz. Nie uruchamiaj instalatorów, skryptów z internetu, `sudo`, zmian ExecutionPolicy ani wyłączania zabezpieczeń. Brakujące elementy zgłoś prowadzącemu; wariant plikowy pozwala pracować od razu.

## Copilot w IntelliJ oraz CLI

W IntelliJ sprawdź wersję IDE, widoczność wtyczki GitHub Copilot, stan logowania i dostępność czatu. Otwórz `pom.xml` z tego katalogu jako projekt Maven; sprawdź Project SDK oraz JDK używany przez runner Maven. Jeśli IDE pobiera zależności, musi korzystać z dozwolonej sieci/repozytorium organizacji. Samo posiadanie IntelliJ nie dowodzi dostępności Copilot.

W PowerShell i Linux sprawdź istniejący CLI:

```text
copilot --version
```

```text
copilot --help
```

Po świadomym wyborze katalogu uruchom sesję:

```text
copilot
```

Użyj `/login` w sesji, jeżeli CLI o to prosi. Sprawdź użytkownika oraz uprawnienie do Copilot, nie publikuj kodu logowania, tokenów ani zrzutu ustawień konta. Członkostwo w zespole repozytorium nie przydziela miejsca Copilot. Administrator może ograniczać CLI niezależnie od wtyczki. Dostęp do modelu i limity sprawdź w aktualnym kliencie; ćwiczenia nie wymagają konkretnego płatnego modelu ani dodatkowej subskrypcji.

[Dokumentacja CLI](https://docs.github.com/en/copilot/how-tos/copilot-cli/set-up-copilot-cli/install-copilot-cli) wymienia PowerShell 6+ na Windows; w tym warsztacie używaj zatwierdzonego PowerShell 7, jeśli jest dostępny. Node.js 22+ jest zależnością **instalacji przez npm**, nie tego projektu Java ani każdego sposobu instalacji CLI. Nie instaluj dodatkowego Node tylko do testów Maven.

Instrukcje `.github/copilot-instructions.md` i obsługę `AGENTS.md` sprawdź osobno dla IntelliJ i CLI w [macierzy GitHub](https://docs.github.com/en/copilot/reference/custom-instructions-support). Poproś agenta o wskazanie plików instrukcji i kryterium, ale nie traktuj jego deklaracji jako dowodu izolacji procesu. Nie włączaj własnych agentów, hooków ani MCP do pierwszego zadania. Nie używaj flag zezwalających na wszystkie narzędzia. Czytaj prośby o zgodę i diff.

## Java i Maven

Starter celuje w **JDK 17+**, `--release 17`, Maven **3.9.x lub nowszy**. Sprawdzono JDK 21 i Maven 3.10.0; wcześniejsza linia referencyjna to JDK 17 lub 21 i Maven 3.9.11; dostępność na komputerze potwierdzają dopiero poniższe komendy. Wersje bibliotek są przypięte w `pom.xml`, [uzasadnienie i źródła](SOURCES.md). Nie ma wrappera `mvnw`; nie zakładaj, że istnieje.

```text
java -version
```

```text
javac -version
```

```text
mvn -version
```

Maven powinien wskazywać JDK 17+ i właściwe Java home. Wbudowany Maven IntelliJ można uruchomić z okna Maven, jeśli jest zatwierdzony; zapisz jego rzeczywistą wersję i JDK. Nie zmieniaj globalnego PATH lub JAVA_HOME bez potrzeby. Pierwsze uruchomienie wymaga zatwierdzonego dostępu do repozytorium artefaktów; tryb `mvn clean -o test` zadziała tylko z już kompletną lokalną pamięcią zależności. Nie wyłączaj TLS ani polityki proxy.

Z katalogu głównego repozytorium, PowerShell:

```powershell
Set-Location course-materials/exercises/coi-java
```

Linux:

```bash
cd course-materials/exercises/coi-java
```

Dalej polecenia są takie same:

```text
mvn clean test
```

Baza oczekuje 12 testów, bez błędów i pominięć. Testy same tworzą serwer na losowym porcie `127.0.0.1`, wysyłają prawdziwe HTTP i zamykają serwer. Nie wymagają Chrome, konta AI, bazy danych, Docker ani klucza API. Zapisz wynik z `target/surefire-reports/`; brak startu runnera oznacza brak wyniku testów.

## Opcjonalne profile

REST Assured ma sens jako czytelna kontrola JSON i statusów, obok małego smoke API opartego na JDK. Nie dodajemy Spring Boot, bo fixture go nie używa.

```text
mvn clean -Pcontract test
```

Oczekiwane 14 testów, 0 pominiętych. Przy przechodzeniu między profilami usuń poprzednie wyniki i klasy:

```text
mvn clean
```

Profil Selenium **nie pobiera Chrome ani ChromeDrivera**. IT/prowadzący muszą udostępnić zatwierdzony browser i pasujący driver. Sprawdź ich wersje, używając rzeczywistych ścieżek. Ścieżki poniżej to przykład do zastąpienia, nie zapewnienie instalacji.

PowerShell, po wskazaniu zatwierdzonych plików:

```powershell
mvn clean -Pselenium test '-Dworkshop.chrome=C:\Program Files\Google\Chrome\Application\chrome.exe' '-Dworkshop.chromedriver=C:\approved-tools\chromedriver.exe'
```

Linux, po wskazaniu zatwierdzonego drivera:

```bash
mvn clean -Pselenium test -Dworkshop.chrome=/usr/bin/google-chrome -Dworkshop.chromedriver=/approved-tools/chromedriver
```

Oczekiwane 14 testów, 0 pominiętych i dwa PNG w `target/evidence/`. Brak pliku lub niezgodność drivera ma zakończyć test błędem, nie pominięciem. Nie uruchamiaj instalacji przeglądarek Playwright ani Selenium Manager dla tego ćwiczenia. Nie dodawaj `--no-sandbox` ani przełączników obchodzących politykę komputera.

## Jawne uruchomienie UI i pełna odpowiedź API

Po udanym `mvn clean test` klasy i zasoby są w `target/classes`. Uruchom fixture tylko na czas ćwiczenia:

```text
java -cp target/classes pl.workshop.qa.FixtureServer 8087
```

Otwórz adres wypisany w terminalu, standardowo `http://127.0.0.1:8087/`. Zajęty port? Wybierz np. `8088` i użyj go konsekwentnie; nie zabijaj obcego procesu. Port `0` losuje wolny port i wypisuje go. Po zadaniu **Ctrl+C** kończy proces. Serwer nie uruchamia się przy samym otwarciu repozytorium.

Przed tworzeniem mocka wykonaj świeże żądanie do uruchomionego fixture. W drugim terminalu, w katalogu ćwiczenia, utwórz katalog dowodów. PowerShell:

```powershell
New-Item -ItemType Directory -Force target/evidence
```

```powershell
curl.exe --include --silent --show-error --max-time 5 http://127.0.0.1:8087/api/slots --output target/evidence/slots.http
```

Linux:

```bash
mkdir -p target/evidence
```

```bash
curl --include --silent --show-error --max-time 5 http://127.0.0.1:8087/api/slots --output target/evidence/slots.http
```

Plik ma zawierać status HTTP, **wszystkie** nagłówki i body bez przerabiania. Dopisz w raporcie datę, adres loopback, metodę, wersję JDK/fixture i polecenie, bez poświadczeń. Syntetyczne pliki `evidence/` nie zastępują takiego przechwycenia.

## Brak narzędzi, instalacji lub kredytów

Otwórz [SPEC.md](SPEC.md), [syntetyczne dowody](evidence/README.md) i [WORKSHOP.md](WORKSHOP.md) w istniejącym edytorze lub przeglądarce repozytorium. Przygotuj scenariusze, hipotezy, diff/propozycję testu i raport. Bez Copilot pracuj w parze albo ręcznie z tym samym pełnym promptem jako checklistą. Wynik oznacz "analiza plików, bez wykonania". Nie raportuj fikcyjnych testów, aktywnej licencji, działającej integracji ani urządzenia Android.
