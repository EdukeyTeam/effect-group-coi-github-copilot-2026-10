# Wytyczne wizualne — gov.pl

Zebrane ze strony głównej [gov.pl](https://www.gov.pl/) 27 października 2026 r. w widocznej sesji Chromium bez trwałego profilu. Pomiary dotyczą desktopowego widoku strony po odrzuceniu opcjonalnych plików cookie; część rozmiarów jest responsywna.

## Zasoby

| Zasób | Plik |
|---|---|
| Zrzut strony głównej | [assets/homepage.png](../assets/homepage.png) |
| Herb używany w logo GOV.PL | [assets/logo.svg](../assets/logo.svg) |
| Favicon | [assets/favicon.png](../assets/favicon.png) |
| Tokeny projektu | [assets/design-tokens.json](../assets/design-tokens.json) |
| Open Sans — 400 | [assets/fonts/open-sans/mem8YaGs126MiZpBA-U1UQ.woff](../assets/fonts/open-sans/mem8YaGs126MiZpBA-U1UQ.woff) |
| Open Sans — 600 | [assets/fonts/open-sans/mem5YaGs126MiZpBA-UNirk-Vg.woff](../assets/fonts/open-sans/mem5YaGs126MiZpBA-UNirk-Vg.woff) |
| Open Sans — 700 | [assets/fonts/open-sans/mem5YaGs126MiZpBA-UN7rg-Vg.woff](../assets/fonts/open-sans/mem5YaGs126MiZpBA-UN7rg-Vg.woff) |
| FontAwesome — font ikon | [assets/fonts/fontawesome/fontawesome-webfont.woff2](../assets/fonts/fontawesome/fontawesome-webfont.woff2) |
| gov-icons — font ikon | [assets/fonts/gov-icons/gov-icons.woff](../assets/fonts/gov-icons/gov-icons.woff) |

## Kolory

| Token | Wartość | Zastosowanie zaobserwowane na stronie |
|---|---|---|
| `brand.primary` | `#0052A5` | Linki do usług i obramowania przycisków |
| `brand.accent` | `#D5233F` | Przycisk wyszukiwania i czerwone akcenty banera |
| `brand.info` | `#006CD7` | Obramowanie komunikatu informacyjnego |
| `text.primary` | `#1B1B1B` | Tekst główny i nagłówki |
| `text.secondary`, `text.muted` | `#656565` | Tekst pomocniczy |
| `text.onDark` | `#FFFFFF` | Tekst na ciemnym, niebieskim obszarze hero |
| `background.default` | `#FFFFFF` | Tło główne |
| `background.light` | `#F1F1F1` | Wyróżniona sekcja „Ważne tematy” |

Kolory błędu, sukcesu i półprzezroczystej nakładki nie wystąpiły w badanym widoku, dlatego nie przypisano im wartości.

## Typografia

Podstawowym krojem jest **Open Sans**, zdefiniowany w serwisie przez `@font-face` dla wag 400, 600 i 700. Pliki `.woff` odpowiadają tym wagom i znajdują się w tabeli zasobów; serwis deklaruje też lokalne warianty systemowe. FontAwesome i gov-icons są fontami ikon, a nie krojem tekstowym interfejsu. Wymiary zebrano z aktualnego widoku: tekst daty `13.755px`, tekst podstawowy `15.72px`, linki kategorii `17.685px`, nagłówki sekcji `23.58px`, a nagłówek hero `25.8258px`. Typowe wagi to 400 (regular), 600 (semibold) i 700 (bold); osobnego wariantu 500 nie zaobserwowano. Wysokość linii tekstu podstawowego wynosi `23.58px` (1.5), a nagłówka sekcji około 1.3.

**Licencje:** przed użyciem lub dalszą dystrybucją Open Sans, FontAwesome, gov-icons, herbu i faviconu zweryfikuj osobno prawa do każdego zasobu. Nie zakładaj, że ich udostępnienie na stronie oznacza zgodę na dowolne użycie.

## Odstępy

Jako praktyczną skalę można stosować bazę `4px` i kroki od `4px` do `32px`. Oryginalny widok używa również dokładniejszych wartości zależnych od typografii i komponentu, m.in. `9.6px`, `10px`, `15.72px`, `19.2px`, `23.58px` i `47.16px`; są one wyszczególnione w `spacing.observed` w pliku tokenów. Nie zastępuj ich automatycznie skalą 4-pikselową, gdy ważne jest wierne odwzorowanie.

## Promienie zaokrągleń

- `0px` — większość powierzchni i krawędzi.
- `3px` — drobne elementy interfejsu.
- `4px` — pola wyszukiwania, obramowany przycisk i aktywna zakładka.
- `50%` — okrągłe wskaźniki karuzeli.
- Dużych i pełnych promieni nie zaobserwowano; nie dodawaj ich bez uzasadnienia.

## Komponenty

- **Nagłówek:** biały, z herbem po lewej i pogrubionym tekstowym wordmarkiem `gov.pl`; znak wordmarku ma około `18.9px`.
- **Nawigacja:** prosta, ciemna typografia; linki usług w treści są niebieskie i zwykle semibold.
- **Główna akcja:** przycisk wyszukiwania ma czerwone tło, biały tekst i łączy się z polem przez zaokrąglenie prawego boku.
- **Akcja drugorzędna:** przycisk „Zobacz wszystkie usługi” ma białe/przezroczyste tło, niebieski tekst i obramowanie `3px`.
- **Pola:** wyszukiwarka ma wysokość `40px`; jej lewe rogi są zaokrąglone, a prawy bok łączy się z czerwonym przyciskiem.
- **Komunikat:** informacja o BIP ma niebieskie obramowanie `2px`, promień `4px` i dodatkowe wcięcie po lewej na ikonę.
- **Baner promocyjny:** widoczny baner bezpieczeństwa jest szerokim obrazem o wysokości `80px`, a nie typowym komponentem tekstowym. Nie zapisano jego zdjęcia/kreacji jako logo.

## Logo

`assets/logo.svg` zawiera sam herb Polski używany przez serwis jako tło linku logo. Wordmark `gov.pl` jest osobnym tekstem HTML: używaj herbu razem z tym tekstem, zamiast przedstawiać plik SVG jako kompletne logo. Zachowaj oryginalne proporcje i kolory herbu. Nie znaleziono odwróconej wersji do ciemnych teł; nie twórz jej przez automatyczne odwracanie kolorów.

## Charakter wizualny

Serwis łączy urzędową, instytucjonalną identyfikację z prostym układem nastawionym na szybkie odnajdywanie usług. Niebieski porządkuje nawigację i działania, a czerwony jest oszczędnym, mocnym akcentem. Open Sans, wyraźna hierarchia nagłówków i głównie jasne powierzchnie wspierają czytelność dużej ilości treści. Zdjęcia i szerokie banery dodają koloru bez zastępowania funkcjonalnej, oszczędnej struktury strony.
