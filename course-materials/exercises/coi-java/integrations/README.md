# Jira i Bitbucket on-prem: adapter zaczyna od pliku

Nie potrzebujesz dostępu do systemów COI. Te pliki są nieaktywną specyfikacją ćwiczenia, bez endpointów, tokenów, numerów wersji API i założenia o dostawcy CI. Nie implementują klienta REST i niczego nie wysyłają.

1. Przeczytaj [syntetyczne ustalenia](findings.synthetic.json).
2. Zbuduj lokalny wynik zgodny z naszym [kontraktem ćwiczenia](adapter-contract.example.json), nie ze zgadywanym request body Atlassian.
3. Przejdź [listę zgodności](compatibility-checklist.md). Zaznacz informacje, których brakuje, bez szukania sekretów i prawdziwych ticketów.
4. Dopiero osobne zadanie z zatwierdzoną dokumentacją konkretnej instalacji może definiować adapter REST i uprawnienia do wysyłki. W tym ćwiczeniu wynik pozostaje plikiem `target/evidence/review-draft.json`, `deliveryStatus=NOT_SENT`.

REST bywa możliwy bez MCP, ale wymaga potwierdzenia obsługiwanych zasobów, wersji, polityki i uwierzytelniania. Nazwa produktu nie gwarantuje zgodności starego on-prem z cloud API. Wyjście agenta jest propozycją do sprawdzenia przez człowieka, nie uprawnieniem do komentarza, commitu, merge lub wdrożenia.
