# Specyfikacja startera: wizyty syntetyczne

To wybrany, opcjonalny model do nauki QA, bez danych osobowych i bez odwzorowania systemu COI. Dane są stałe: `SYN-01` (2026-10-28 09:00, Europe/Warsaw, 2 miejsca), `SYN-02` (10:00, 0 miejsc). Nie ma zegara systemowego, kont, produkcyjnego wdrożenia ani trwałej bazy. Restart przywraca stan początkowy.

## Kryteria i pokrycie

| ID | Obserwowalny przypadek | Baza / punkt wyjścia | Zadanie uczestnika |
|---|---|---|---|
| AC-01 | GET /api/slots zwraca 200, JSON, dwa sloty w stałej kolejności i strefę Europe/Warsaw | ApiSmokeTest (status, typ, identyfikatory); opcjonalny SlotContractTest (pola i typy) | Uzupełnić kontrakt, nie kopię implementacji |
| AC-02 | Liczba osób 1..4, włącznie z granicami | AppointmentPolicyTest: 1 i 4 akceptowane przy 4 miejscach | Dodać brakujące klasy równoważności |
| AC-03 | 0 i 5 osób są niepoprawne nawet przy nadmiarze miejsc | Dwa testy polityki | Sprawdzić ujemną liczbę osób i raport istniejącego pokrycia |
| AC-04 | Gdy osób jest więcej niż miejsc, wynik FULL; dokładnie tyle samo jest dozwolone | Trzy testy: równość, brak miejsc, za mało miejsc | Dodać kombinacje bez zmiany wymagań |
| AC-05 | Docelowo POST /api/reservations z formularzem slotId=SYN-01&partySize=1 zwraca 201, id rezerwacji, slotId i partySize; GET /api/reservations zawiera zapis; remaining spada z 2 do 1 | **Niezaimplementowane**: POST zwraca 501 NOT_IMPLEMENTED, GET listy zwraca [] | Najpierw nowy test kontraktu, następnie jeden pionowy wycinek |
| AC-06 | Docelowo POST: brak/niecałkowite/poza 1..4 partySize lub brak slotId -> 400 INVALID_REQUEST, nieznany slot -> 404 SLOT_NOT_FOUND, przekroczenie miejsc -> 409 FULL; błąd nie zmienia stanu | **Niezaimplementowane**, 501 | Kolejne osobne testy i minimalne zmiany; nie wszystko w jednym zadaniu |
| AC-07 | UI ładuje sloty, pokazuje "Gotowe", wybór terminu jest dostępny po stabilnym identyfikatorze | Dwa opcjonalne testy Selenium: lista i wybór | Zdiagnozować osobny wadliwy przykład; rozwinąć Page Object |
| AC-08 | Docelowo po udanej rezerwacji UI pokazuje id oraz nową liczbę miejsc; błąd ma czytelny komunikat | Formularz pokazuje obecnie "Funkcja ćwiczeniowa: zapis nie jest gotowy." dla 501 | Po AC-05/06 dodać test prawdziwego przepływu UI |
| AC-09 | Nieznana ścieżka -> 404 NOT_FOUND; niedozwolona metoda -> 405 METHOD_NOT_ALLOWED i Allow | Dwa testy API | Rozszerzyć przypadki metod bez osłabiania asercji |
| AC-10 | Ujemne remaining oznacza wadliwe dane fixture i IllegalArgumentException | Jeden test polityki | Odróżnić problem danych od odrzucenia poprawnego żądania |

`POST` przyjmuje docelowo `application/x-www-form-urlencoded`, odpowiedzi API są JSON. Docelowy sukces: `{"id":"SYN-R001","slotId":"SYN-01","partySize":1}`. Kolejne id są lokalne i deterministyczne, nie są danymi osoby. Błędy mają pojedyncze pole `error` z kodem z tabeli. Dla nieznanej ścieżki pierwszeństwo ma 404; dla znanej ścieżki najpierw metoda (405), potem obsługa. Nie implementujemy współbieżności, uwierzytelniania, odwołania rezerwacji ani integracji klienta w tym wycinku. To ograniczenia ćwiczenia, nie wzorzec produkcyjny.

## Decyzje startera

JDK 17+, Maven, JUnit Jupiter 5.13.4; mały serwer HTTP z JDK zamiast Spring Boot. Dzięki temu nie trzeba tworzyć całego produktu ani pobierać frameworka przed testem. REST Assured 5.5.6 i Selenium 4.38.0 są osobnymi profilami opt-in; nie instalują przeglądarki. Nie ma domyślnych wadliwych testów, sekretów, hooków ani agentów. Tylko 127.0.0.1; testy używają portu losowanego przez system i zamykają fixture. Spring Boot omawiamy w odniesieniu do rzeczywistego projektu, bez fikcyjnej konfiguracji klienta.

## Rejestr ustaleń

2026-10-26, COI-QA-01: zamiast odziedziczonego projektu nowej aplikacji TypeScript wybrano opcjonalny fixture Java QA i pracę nad istniejącym zachowaniem. Powód: profil Java/Selenium i ograniczenia instalacji. Root app/ pozostaje szkieletem, PROGRAM.md bez zmian.

2026-10-26, COI-QA-02: zapis rezerwacji pozostaje jawnie niegotowy, zamiast dostarczenia rozwiązania. Uczestnik wybiera kryterium i dodaje własny test; istniejące zielone pokrycie jest prawidłowym wynikiem analizy.
