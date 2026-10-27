# Lokatory odczytane z APK (My Demo App 2.3.0)

Bez urządzenia też da się zacząć: plik APK zawiera układy ekranów z identyfikatorami (`resource-id`) i opisami dostępności (`content-desc`). Tak samo odczytasz je z wersji testowej Waszej aplikacji, np. przez Android Studio (Build, Analyze APK) albo `apkanalyzer`. Dotyczy to ekranów w klasycznych układach XML. Ekranów w Jetpack Compose nie ma w APK jako XML: ich lokatory odczytasz tylko z działającej aplikacji. W działającej aplikacji zawsze sprawdź lokatory w Appium Inspector lub zrzucie hierarchii, bo część elementów powstaje dynamicznie.

Pakiet: `com.saucelabs.mydemoapp.android`. Pełne id w Appium: `com.saucelabs.mydemoapp.android:id/<nazwa>`.

## Nagłówek (każdy ekran)

| Element | resource-id | content-desc | Tekst |
|---|---|---|---|
| Menu | `menuIV` | View menu | |
| Koszyk | `cartRL` | View cart | |
| Liczba w koszyku | `cartTV` | | np. 1 |
| Sortowanie | `sortIV` | Shows current sorting order and displays available sorting options | |
| Pozycja menu bocznego | `itemTV` | | Catalog, Log In i inne |

## Logowanie

| Element | resource-id | content-desc | Tekst |
|---|---|---|---|
| Tytuł | `loginTV` | | Login |
| Użytkownik | `nameET` | | |
| Hasło | `passwordET` | | |
| Zaloguj | `loginBtn` | Tap to login with given credentials | Login |
| Biometria | `bioMetricIB` | Tap to login using biometric verification | |
| Błąd użytkownika | `nameErrorTV` | | Username is required |
| Błąd hasła | `passwordErrorTV` | | Password is required |
| Podpowiedź: użytkownik 1 | `username1TV` | Tap to use this username for login | bod@example.com |
| Podpowiedź: hasło 1 | `password1TV` | | 10203040 |
| Podpowiedź: użytkownik 2 | `username2TV` | Tap to use this username for login | alice@example.com (locked out) |

## Katalog

| Element | resource-id | content-desc | Tekst |
|---|---|---|---|
| Tytuł | `productTV` | title | Products |
| Lista produktów | `productRV` | Displays all products of catalog | |

## Dane do wysyłki (checkout)

| Pole | resource-id | Komunikat błędu (id i tekst) |
|---|---|---|
| Imię i nazwisko* | `fullNameET` | `fullNameErrorTV`: Please provide your full name. |
| Adres 1* | `address1ET` | `address1ErrorTV`: Please provide your address. |
| Adres 2 | `address2ET` | |
| Miasto* | `cityET` | `cityErrorTV`: Please provide your city. |
| Kod pocztowy* | `zipET` | `zipErrorTV`: Please provide your zip |
| Region | `stateET` | |
| Kraj* | `countryET` | `countryErrorTV` |
| Do płatności | `paymentBtn` (content-desc: Saves user info for checkout) | |
