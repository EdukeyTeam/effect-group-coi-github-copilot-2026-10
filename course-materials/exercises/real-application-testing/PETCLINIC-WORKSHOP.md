# Petclinic: stanowe API Java i testy przeglądarkowe

Opcjonalny cel zaawansowanych ćwiczeń drugiego dnia. Spring Petclinic REST pozwala przejść przez powiązane zasoby: właściciel, zwierzę i wizyta. Osobny klient Angular daje interfejs do testów Selenium oraz porównania z Playwright. Wybierz ten wariant, jeżeli potrzebujesz kilku operacji HTTP, trwałego stanu w czasie działania procesu, walidacji i sprawdzania skutków zmian.

Pracuj w osobnych checkoutach oficjalnych aplikacji, poza repozytorium materiałów. Nie przenoś ich kodu do `app/`. Ćwiczenie nie wymaga chmury, Docker, danych klienta ani klucza dostawcy LLM. Uruchomienie wymaga lokalnych narzędzi i dostępu do zależności dopuszczonych przez IT.

## Wersje i granice weryfikacji

Stan źródeł sprawdzony 26.10.2026, gałąź `master`:

| Repozytorium | Wersja deklarowana w źródłach | Commit do odtworzenia ćwiczenia |
| --- | --- | --- |
| `spring-petclinic/spring-petclinic-rest` | aplikacja 4.0.2, rodzic Spring Boot 4.1.1 | `afc8fc1d3b8ec8de69414482b3bd70a1da78cb95` |
| `spring-petclinic/spring-petclinic-angular` | pakiet 11.2.11, `@angular/core` 22.2.3, CLI 22.2.4, Node.js 24.x | `22ae11ea1f664148a6c6648ed258a0f1cec9c2c6` |

Wersja pakietu frontendowego 11.2.11 nie oznacza Angulara 11. Backend dziedziczy `java.version=17` z opublikowanego POM rodzica; jego CI również deklaruje JDK 17. Maven Enforcer odwołuje się do Maven 3.9.9, a wrapper pobiera Maven 3.10.0. Zachowaj te wersje podczas pierwszej próby, zgłoś problem zamiast samodzielnie migrować zależności. Źródła: [POM backendu][r-pom], [POM rodzica][boot-parent], [wrapper][r-wrapper], [CI backendu][r-ci], [package.json klienta][a-package].

Zweryfikowano źródła, konfigurację, schemat bazy i istniejące testy. Nie wykonano instalacji zależności, kompilacji, testów ani uruchomienia aplikacji. Zgodność tych dwóch commitów w działającym zestawie pozostaje do potwierdzenia. Poniższe statusy HTTP wynikają z kontrolerów i testów źródłowych, a nie z wykonanych żądań.

## Przygotowanie i uruchomienie

W osobnym katalogu roboczym sklonuj backend:

```bash
git clone https://github.com/spring-petclinic/spring-petclinic-rest.git
```

```bash
cd spring-petclinic-rest
```

```bash
git checkout afc8fc1d3b8ec8de69414482b3bd70a1da78cb95
```

Sprawdź Java i wrapper przed uruchomieniem. Wrapper może pobrać Maven i zależności; wymaga dostępu sieciowego do repozytoriów artefaktów.

```bash
java -version
```

```bash
./mvnw -version
```

Komenda z [README backendu][r-readme]:

```bash
./mvnw spring-boot:run
```

Na Windows użyj `mvnw.cmd spring-boot:run` z tego samego checkoutu. Domyślne profile to `h2,spring-data-jpa`, więc nie konfiguruj zewnętrznej bazy. [Konfiguracja aplikacji][r-config] wskazuje port 9966 i kontekst `/petclinic/`; [profil H2][r-h2] wskazuje bazę w pamięci. Skrypty inicjalizacji wczytują schemat i dane przy starcie. Zakończenie procesu i ponowne uruchomienie odtwarza środowisko H2; odświeżenie strony tego nie robi.

Po potwierdzonym starcie wykonaj kontrolne żądanie, zapisz status, nagłówki i treść:

```bash
curl -i http://localhost:9966/petclinic/api/owners
```

Backend nie ma interfejsu użytkownika. Jego README wskazuje [Swagger UI](http://localhost:9966/petclinic/swagger-ui.html) oraz [health check](http://localhost:9966/petclinic/actuator/health). Brak strony aplikacji pod adresem backendu nie przesądza o awarii API.

Frontend przygotuj w drugim terminalu, w osobnym katalogu roboczym:

```bash
git clone https://github.com/spring-petclinic/spring-petclinic-angular.git
```

```bash
cd spring-petclinic-angular
```

```bash
git checkout 22ae11ea1f664148a6c6648ed258a0f1cec9c2c6
```

Jeżeli masz już nvm, [README klienta][a-readme] podaje `nvm use`; `.nvmrc` wskazuje 24. Przy innym zatwierdzonym sposobie instalacji sprawdź dostępność Node.js 24.x. Nie instaluj globalnego Angular CLI.

```bash
node --version
```

Projekt używa npm i ma plik blokady, dlatego zachowaj jego menedżer pakietów:

```bash
npm ci
```

```bash
npm start
```

Otwórz [klienta lokalnego](http://localhost:4200/), następnie przejdź do właścicieli przez menu. Router definiuje m.in. `owners`, `owners/add`, `owners/:id` i `owners/:id/pets/add`. W `src/index.html` ustawiono bazę `/petclinic`; rzeczywiste adresy po nawigacji zapisz z działającej przeglądarki. Nie utożsamiaj ścieżek UI ze ścieżkami API.

Oba pliki `src/environments/environment*.ts` wskazują `http://localhost:9966/petclinic/api/`. Kontrolery v1 mają `@CrossOrigin(exposedHeaders = "errors, content-type")`. To konfiguracja źródłowa, nie dowód udanego połączenia przeglądarki. Gdy UI nie pobiera danych, sprawdź żądania, port, kontekst i CORS, zanim zmienisz testy. Nie zakładaj, że `Location` jest dostępne dla skryptu w przeglądarce przy takim zestawie odsłoniętych nagłówków. Źródła: [konfiguracja klienta][a-env], [router właścicieli][a-routing], [indeks HTML][a-index], [kontroler właścicieli][r-owner].

Domyślnie `petclinic.security.enable=false`. Włączenie tej właściwości aktywuje HTTP Basic i kontrolę ról; CRUD właścicieli, zwierząt i wizyt wymaga `OWNER_ADMIN`. Poniższy scenariusz dotyczy domyślnego, lokalnego wariantu. Nie zakładaj gotowego formularza logowania ani obsługi Basic w kliencie Angular. Źródła: [konfiguracja][r-config], [wyłączone zabezpieczenia][r-security-off], [Basic Authentication][r-security-on].

## Pięć ścieżek do jednego cyklu danych

W tabeli baza to `http://localhost:9966/petclinic/api`. Parametry w klamrach zastępujesz identyfikatorami z rzeczywistych odpowiedzi. Statusy sukcesu i braku zasobu sprawdzono w [kontrolerze właścicieli][r-owner] i [kontrolerze zwierząt][r-pet]; ścieżki i DTO w [OpenAPI][r-openapi].

| Ścieżka względem bazy | Operacje | Oczekiwania ze źródeł |
| --- | --- | --- |
| `/owners` | POST, GET, GET z `?lastName=...` | POST: 201, zapisany właściciel i `Location`. GET: 200 z listą; kontroler zwraca 404 dla pustego wyniku, także wyszukiwania. |
| `/owners/{ownerId}` | GET, PUT, DELETE | GET: 200, brak: 404. PUT i DELETE: 204 przy istniejącym zasobie, 404 przy braku. |
| `/owners/{ownerId}/pets` | POST | 201 ze zwierzęciem i `Location`; nieistniejący właściciel: 404. |
| `/pets/{petId}` | GET, PUT, DELETE | GET: 200 lub 404. PUT i DELETE: 204 lub 404. PUT przyjmuje `Pet`, a zagnieżdżony POST przyjmuje `PetFields`; sprawdź osobne schematy. |
| `/owners/{ownerId}/pets/{petId}/visits` | POST | 201 z wizytą i `Location`. Kontrakt opisuje też 400 i 404; weryfikacja powiązania właściciela ze zwierzęciem wymaga osobnego testu. |

Dla wizyt istnieją również `GET /visits`, `POST /visits` oraz `GET`, `PUT`, `DELETE /visits/{visitId}`. [Kontroler wizyt][r-visit] zwraca dla pojedynczego odczytu 200/404, dla aktualizacji i usunięcia 204/404, dla utworzenia 201, a dla pustej listy 404. Zagnieżdżony POST używa `VisitFields`; bezpośredni `POST /visits` używa `Visit` z `petId`. Nie zamieniaj tych payloadów bez sprawdzenia kontraktu.

Aktualizacje zwracają w kontrolerach status 204 mimo przekazania DTO do `ResponseEntity`. Nie uzależniaj testu od treści odpowiedzi 204: wykonaj osobny GET i sprawdź zapis. `Location` jest konstruowane jako `/api/...`, bez kontekstu `/petclinic`; zbadaj jego użyteczność względem rzeczywistego adresu serwera.

## Zadanie: właściciel, zwierzę, wizyta

Zarezerwuj 60-90 minut po przygotowaniu środowiska. Wykorzystaj wyłącznie dane syntetyczne i osobną instancję H2 dla swojej pary. Pierwsza część to testy API w Javie, druga to scenariusz przez rzeczywisty interfejs.

1. Utwórz właściciela przez `POST /owners`. `OwnerFields` wymaga `firstName`, `lastName`, `address`, `city`, `telephone`. Wybierz fikcyjne imię i nazwisko zgodne z walidacją. Unikalny identyfikator uruchomienia umieść w adresie; cyfry w nazwisku nie przechodzą wzorca. Zachowaj ID zwrócone przez serwer.
2. Odczytaj właściciela po ID, następnie zmień jego telefon przez PUT i odczytaj ponownie. Porównaj wartości oraz potwierdź, że ID pozostało takie samo.
3. Pobierz `GET /pettypes` i wybierz istniejący typ z odpowiedzi, bez zakładania jego numeru. Utwórz zwierzę przez zagnieżdżony POST. `PetFields` wymaga `name`, `birthDate` i `type`; `type` jest obiektem schematu `PetType`, nie samym tekstem. Sprawdź wszystkie wymagania tego schematu i zachowaj ID zwierzęcia.
4. Dodaj wizytę przez zagnieżdżony POST z `date` w formacie `YYYY-MM-DD` i `description`. Zachowaj ID wizyty. Odczytaj wizytę przez `/visits/{visitId}` i sprawdź `petId`. Odczytaj właściciela i potwierdź obecność zwierzęcia oraz wizyty w zagnieżdżonych danych. Nie oceniaj poprawności wyłącznie po statusie POST.
5. Zmień opis wizyty przez PUT na `/visits/{visitId}`, odczytaj ponownie i porównaj. W części przeglądarkowej utwórz drugi zestaw danych przez formularze właściciela, zwierzęcia i wizyty. Sprawdź efekt po przejściu na inny widok i ponownym wejściu.
6. Usuń wyłącznie własne rekordy, od wizyty przez zwierzę do właściciela. Dla każdego DELETE sprawdź 204, a następnie 404 w GET po ID. Powtórny DELETE powinien dać 404 zgodnie z kontrolerami. Sprzątanie ma działać również po błędzie pośrodku scenariusza.

Identyfikatory trzymaj w rejestrze utworzonych zasobów. Wykonuj cleanup w `finally` lub mechanizmie zakończenia testu, z osobnym raportowaniem błędu sprzątania. Nie kasuj całych kolekcji ani rekordów startowych. [Schemat H2][r-schema] ma klucze obce z `ON DELETE CASCADE`; [model Pet][r-model-pet] ma także kaskady JPA, a [repozytorium usuwania zwierzęcia][r-delete-pet] wykonuje własne zapytania usuwające wizyty i zwierzę. Zmiana profilu bazy lub repozytorium wymaga ponownego sprawdzenia skutków. Do testów kaskad użyj odrębnej, jednorazowej instancji, a nie wspólnego serwera grupy.

## Przypadki negatywne i rozbieżności

- Walidacja właściciela: brak telefonu, litery w telefonie, przekroczone długości. OpenAPI wymaga telefonu z 1-20 cyfr; [test kontrolera][r-owner-tests] sprawdza także 400 dla niepoprawnego tworzenia i aktualizacji. Sprawdź brak częściowego zapisu po odrzuceniu żądania.
- Data urodzenia zwierzęcia: jutro, dokładnie dziś minus 50 lat i dzień wcześniej. [PetAgeValidator][r-age] odrzuca przyszłość oraz datę starszą niż 50 lat względem zegara serwera. Nie wpisuj na stałe bieżącego roku do testu.
- Polska pisownia: porównaj imię `Łukasz` w API i formularzu. Schemat API dopuszcza litery Unicode, podczas gdy [formularz właściciela][a-owner-form] używa dla imienia i nazwiska `^[a-zA-Z]*$`. Zapisz osobno regułę kontraktu, ograniczenie UI i wynik wykonania. Interfejs upstream jest angielski; ćwiczenie nie wymaga jego tłumaczenia.
- Powiązanie zasobów: na własnych danych użyj `ownerId` drugiego właściciela i `petId` pierwszego. OpenAPI opisuje 404 dla zwierzęcia nienależącego do właściciela, ale `addVisitToOwner` w kontrolerze nie używa `ownerId` do sprawdzenia tej relacji. To hipoteza defektu do reprodukcji; nie przedstawiaj spodziewanego wyniku jako wykonanego testu. Podobnie sprawdź `updateOwnersPet`, które wyszukuje właściciela i zwierzę osobno.
- Błędy API: [ExceptionControllerAdvice][r-errors] mapuje naruszenie integralności danych na 404, błędy walidacji `MethodArgumentNotValidException` na 400, inne wyjątki na 500. Nie zakładaj automatycznie 409 ani jednego statusu dla wszystkich złych żądań. Zbadaj treść `ProblemDetail` i brak technicznych szczegółów bazy.
- Brak backendu podczas zapisu w UI: zatrzymaj tylko własną instancję i sprawdź, czy użytkownik widzi błąd, a nie sukces. Po ponownym starcie uwzględnij reset H2. Nie symuluj tego przypadku atrapą, jeżeli raportujesz test całego stosu.

W pliku OpenAPI zapisano `openapi: 3.0.1`, mimo opisu OAS 3.1 w README. Przy walidacji kontraktu wskazuj konkretny plik i commit. Pusty wynik wyszukiwania właścicieli daje w kontrolerze 404, choć sekcja GET `/owners` w OpenAPI nie wymienia tego statusu. Raportuj rozbieżność zamiast zmieniać asercję tak, żeby test był zielony.

## JUnit, HTTP, Selenium i Playwright

JUnit uruchamia testy Java. Java HTTP Client lub REST Assured wysyła żądania HTTP do działającego backendu. Selenium WebDriver i Playwright sterują przeglądarką. Sprawdzenie `POST /owners` przez HTTP nie testuje formularza, a kliknięcie przycisku bez potwierdzenia zapisu nie testuje całego cyklu danych.

Główna ścieżka: Java, JUnit i Selenium. REST Assured wybierz, jeśli jest już w zatwierdzonym projekcie testowym; nie dodawaj zależności ani ich wersji na podstawie domysłów. Wariant porównawczy: ten sam scenariusz przeglądarkowy w Playwright. Nie przebudowuj klienta Angular ani backendu, aby przejść ćwiczenie.

Istniejące [testy kontrolera backendu][r-owner-tests] używają MockMvc i `@MockitoBean ClinicService`, więc nie dowodzą rzeczywistego zapisu przez HTTP do bazy. Istniejący [plik testów Playwright][a-e2e] przechwytuje żądania przez `page.route` i zwraca przygotowane odpowiedzi. Jego zielony wynik nie dowodzi współpracy z backendem. [Konfiguracja Playwright][a-playwright] uruchamia frontend, bez backendu. W nowym scenariuszu całego stosu nie przechwytuj API ani nie zastępuj odpowiedzi.

Gdy środowisko jest gotowe, backendowy etap weryfikacji można uruchomić wrapperem jako `./mvnw -B verify`, odpowiadając fazie Maven użytej w CI, bez dodatkowego skanera Sonar. Klient deklaruje `npm run test:ci` i `npm run e2e`. Te polecenia opisują dostępne mechanizmy, nie wyniki wykonania. Nie instaluj automatycznie przeglądarek ani dodatkowych narzędzi na komputerze z ograniczeniami IT. Użyj zatwierdzonej przeglądarki i sprawdź konfigurację runnera dla swojego systemu.

## Pełny prompt do GitHub Copilot

Skopiuj całość po otwarciu właściwego checkoutu aplikacji lub zatwierdzonego projektu testowego:

```text
Przygotuj ze mną testy rzeczywistego Spring Petclinic. Pracuj po polsku i używaj tylko syntetycznych danych. Nie kopiuj gotowych testów, nie zmieniaj kodu aplikacji, zależności ani zabezpieczeń, nie instaluj narzędzi i nie wykonuj commitów ani pushowania.

Backend odniesienia: spring-petclinic/spring-petclinic-rest, commit afc8fc1d3b8ec8de69414482b3bd70a1da78cb95. Klient: spring-petclinic/spring-petclinic-angular, commit 22ae11ea1f664148a6c6648ed258a0f1cec9c2c6. Sprawdź rzeczywiste checkouty, instrukcje agentów i stan Git. Inny commit oznacza ponowną analizę kontraktu, a nie zgadywanie zgodności.

Zakres: jeden cykl właściciel -> zwierzę -> wizyta, jego odczyt, aktualizacja i usunięcie oraz trzy przypadki negatywne. Użyj lokalnego API http://localhost:9966/petclinic/api i klienta pod http://localhost:4200 tylko po potwierdzeniu, że są to nasze odrębne środowiska testowe.

Najpierw przeczytaj OpenAPI, kontrolery v1 właścicieli, zwierząt i wizyt, DTO lub ich schematy, mappery, konfigurację H2 i istniejące testy. Wskaż prawdziwe pola payloadów, statusy HTTP i reguły walidacji. Odróżniaj regułę kontraktu od zaobserwowanego zachowania. Zanim napiszesz test, pokaż krótką macierz scenariuszy i dowody ze źródeł.

Sprawdź dostępne Java, Maven wrapper, projekt JUnit/Selenium i istniejące biblioteki HTTP. Nie zakładaj gotowej konfiguracji REST Assured ani Selenium. Brak narzędzia lub działającego serwera zgłoś z dokładnym błędem i zatrzymaj zależny etap. Nie przedstawiaj analizy źródeł jako udanego uruchomienia.

Po potwierdzeniu środowiska wykonaj prawdziwe żądanie i zapisz pełny status, nagłówki i treść odpowiedzi bez sekretów. Podaj datę, metodę, URL i commit. Nie twórz atrap na podstawie zgadywanej odpowiedzi. W testach całego stosu nie mockuj HTTP ani bazy.

Przygotuj najpierw test API w Javie/JUnit z dostępnym klientem HTTP. Utwórz właściciela, odczytaj i zmień telefon, pobierz typy zwierząt, utwórz zwierzę oraz wizytę. Odczytaj wizytę po ID i właściciela z jego zwierzętami. Zmień opis wizyty i potwierdź zapis osobnym GET. ID pobieraj z odpowiedzi. Nadaj przebiegowi unikalny znacznik w adresie i opisie; nie dodawaj cyfr do nazwiska wbrew wzorcowi.

Potem przygotuj jeden test przeglądarkowy w Selenium dla utworzenia drugiego zestawu danych przez formularze. Playwright jest opcjonalnym porównaniem po moim wyborze. Najpierw obejrzyj działający UI, potem wybierz selektory i oczekiwania. Nie używaj sztywnych opóźnień ani przechwytywania API. Potwierdź zapis po ponownym wejściu w szczegóły właściciela, a nie samym komunikatem sukcesu.

Przypadki negatywne: litery w telefonie, przyszła data urodzenia i próba dodania wizyty z ownerId niepasującym do petId. Dla ostatniego przypadku utwórz obu właścicieli we własnej instancji. Sprawdź również rozbieżność Unicode między kontraktem API a formularzem. Nie zmieniaj asercji na aktualne zachowanie, jeśli przeczy ono kontraktowi; zachowaj reprodukcję i opisz defekt.

Rejestruj wszystkie utworzone ID. W cleanup po sukcesie i porażce usuwaj kolejno własne wizyty, zwierzęta i właścicieli, po czym sprawdzaj GET 404. Nie usuwaj rekordów startowych. Osobno raportuj błąd scenariusza i sprzątania. Sprawdź skutki kaskad dla aktywnego profilu; nie wykonuj eksperymentów na wspólnej instancji.

Pracuj małymi krokami, najpierw test i jego rzeczywisty wynik. Udokumentuj wykryte błędy bez naprawiania aplikacji. Oddziel wyniki: analiza źródeł, kompilacja, uruchomienie serwera, liczba testów odkrytych/wykonanych/pominiętych, ręczna kontrola UI i cleanup. Zrób zrzuty formularzy i widoku ze zwierzęciem oraz wizytą. Zapisz żądania i odpowiedzi potrzebne do reprodukcji oraz ograniczenia, w tym niewykonane kroki. Nie deklaruj sukcesu, gdy etap był tylko opisany.
```

Wynik ćwiczenia: kod własnych testów w osobnym projekcie, macierz scenariuszy, dowody HTTP, zrzuty UI i krótki raport defektów. Raport podaje dokładne commity, aktywne profile, wyniki uruchomienia oraz stan danych po sprzątaniu. Ten materiał nie zawiera rozwiązania.

## Licencje i źródła

Backend ma [LICENSE.txt Apache-2.0][r-license]. Klient deklaruje `MIT` w [package.json][a-package], ale zbadane pliki źródłowe, np. [OwnerService][a-owner-service], zawierają nagłówki Apache-2.0; w przypiętym drzewie nie znaleziono osobnego pliku LICENSE/COPYING/NOTICE. Sama pusta wartość licencji w metadanych GitHub nie rozstrzyga tej rozbieżności. Zachowaj oznaczenia upstream i wyjaśnij licencję przed redystrybucją kodu klienta. Materiał odsyła do aplikacji i nie dołącza ich kodu ani gotowych testów.

[r-readme]: https://github.com/spring-petclinic/spring-petclinic-rest/blob/afc8fc1d3b8ec8de69414482b3bd70a1da78cb95/readme.md
[r-pom]: https://github.com/spring-petclinic/spring-petclinic-rest/blob/afc8fc1d3b8ec8de69414482b3bd70a1da78cb95/pom.xml
[boot-parent]: https://repo.maven.apache.org/maven2/org/springframework/boot/spring-boot-starter-parent/4.1.1/spring-boot-starter-parent-4.1.1.pom
[r-wrapper]: https://github.com/spring-petclinic/spring-petclinic-rest/blob/afc8fc1d3b8ec8de69414482b3bd70a1da78cb95/.mvn/wrapper/maven-wrapper.properties
[r-ci]: https://github.com/spring-petclinic/spring-petclinic-rest/blob/afc8fc1d3b8ec8de69414482b3bd70a1da78cb95/.github/workflows/maven-build-master.yml
[r-config]: https://github.com/spring-petclinic/spring-petclinic-rest/blob/afc8fc1d3b8ec8de69414482b3bd70a1da78cb95/src/main/resources/application.properties
[r-h2]: https://github.com/spring-petclinic/spring-petclinic-rest/blob/afc8fc1d3b8ec8de69414482b3bd70a1da78cb95/src/main/resources/application-h2.properties
[r-openapi]: https://github.com/spring-petclinic/spring-petclinic-rest/blob/afc8fc1d3b8ec8de69414482b3bd70a1da78cb95/src/main/resources/openapi.yml
[r-owner]: https://github.com/spring-petclinic/spring-petclinic-rest/blob/afc8fc1d3b8ec8de69414482b3bd70a1da78cb95/src/main/java/org/springframework/samples/petclinic/rest/controller/v1/OwnerRestControllerV1.java
[r-pet]: https://github.com/spring-petclinic/spring-petclinic-rest/blob/afc8fc1d3b8ec8de69414482b3bd70a1da78cb95/src/main/java/org/springframework/samples/petclinic/rest/controller/v1/PetRestControllerV1.java
[r-visit]: https://github.com/spring-petclinic/spring-petclinic-rest/blob/afc8fc1d3b8ec8de69414482b3bd70a1da78cb95/src/main/java/org/springframework/samples/petclinic/rest/controller/v1/VisitRestControllerV1.java
[r-security-off]: https://github.com/spring-petclinic/spring-petclinic-rest/blob/afc8fc1d3b8ec8de69414482b3bd70a1da78cb95/src/main/java/org/springframework/samples/petclinic/security/DisableSecurityConfig.java
[r-security-on]: https://github.com/spring-petclinic/spring-petclinic-rest/blob/afc8fc1d3b8ec8de69414482b3bd70a1da78cb95/src/main/java/org/springframework/samples/petclinic/security/BasicAuthenticationConfig.java
[r-schema]: https://github.com/spring-petclinic/spring-petclinic-rest/blob/afc8fc1d3b8ec8de69414482b3bd70a1da78cb95/src/main/resources/db/h2/schema.sql
[r-model-pet]: https://github.com/spring-petclinic/spring-petclinic-rest/blob/afc8fc1d3b8ec8de69414482b3bd70a1da78cb95/src/main/java/org/springframework/samples/petclinic/model/Pet.java
[r-delete-pet]: https://github.com/spring-petclinic/spring-petclinic-rest/blob/afc8fc1d3b8ec8de69414482b3bd70a1da78cb95/src/main/java/org/springframework/samples/petclinic/repository/springdatajpa/SpringDataPetRepositoryImpl.java
[r-owner-tests]: https://github.com/spring-petclinic/spring-petclinic-rest/blob/afc8fc1d3b8ec8de69414482b3bd70a1da78cb95/src/test/java/org/springframework/samples/petclinic/rest/controller/OwnerRestControllerV1Tests.java
[r-age]: https://github.com/spring-petclinic/spring-petclinic-rest/blob/afc8fc1d3b8ec8de69414482b3bd70a1da78cb95/src/main/java/org/springframework/samples/petclinic/rest/validation/PetAgeValidator.java
[r-errors]: https://github.com/spring-petclinic/spring-petclinic-rest/blob/afc8fc1d3b8ec8de69414482b3bd70a1da78cb95/src/main/java/org/springframework/samples/petclinic/rest/advice/ExceptionControllerAdvice.java
[r-license]: https://github.com/spring-petclinic/spring-petclinic-rest/blob/afc8fc1d3b8ec8de69414482b3bd70a1da78cb95/LICENSE.txt
[a-readme]: https://github.com/spring-petclinic/spring-petclinic-angular/blob/22ae11ea1f664148a6c6648ed258a0f1cec9c2c6/README.md
[a-package]: https://github.com/spring-petclinic/spring-petclinic-angular/blob/22ae11ea1f664148a6c6648ed258a0f1cec9c2c6/package.json
[a-env]: https://github.com/spring-petclinic/spring-petclinic-angular/blob/22ae11ea1f664148a6c6648ed258a0f1cec9c2c6/src/environments/environment.ts
[a-routing]: https://github.com/spring-petclinic/spring-petclinic-angular/blob/22ae11ea1f664148a6c6648ed258a0f1cec9c2c6/src/app/owners/owners-routing.module.ts
[a-index]: https://github.com/spring-petclinic/spring-petclinic-angular/blob/22ae11ea1f664148a6c6648ed258a0f1cec9c2c6/src/index.html
[a-owner-form]: https://github.com/spring-petclinic/spring-petclinic-angular/blob/22ae11ea1f664148a6c6648ed258a0f1cec9c2c6/src/app/owners/owner-add/owner-add.component.html
[a-owner-service]: https://github.com/spring-petclinic/spring-petclinic-angular/blob/22ae11ea1f664148a6c6648ed258a0f1cec9c2c6/src/app/owners/owner.service.ts
[a-e2e]: https://github.com/spring-petclinic/spring-petclinic-angular/blob/22ae11ea1f664148a6c6648ed258a0f1cec9c2c6/e2e/app.e2e-spec.ts
[a-playwright]: https://github.com/spring-petclinic/spring-petclinic-angular/blob/22ae11ea1f664148a6c6648ed258a0f1cec9c2c6/playwright.config.ts
