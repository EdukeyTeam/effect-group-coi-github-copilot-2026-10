# Źródła i pochodzenie jFTP

Wersja ćwiczenia: 26.10.2026. Linki do kodu przypięto do rewizji, aby dalsze zmiany gałęzi nie przesuwały punktu startowego.

## Wybrana baza

- [Drzewo jftp-modernization na c0e8a0f](https://github.com/LucasMatuszewski/jftp-modernization/tree/c0e8a0fa7f8be99907ccdc50ac2bdc4ad7a98a86), gałąź źródłowa `main`.
- [POM](https://github.com/LucasMatuszewski/jftp-modernization/blob/c0e8a0fa7f8be99907ccdc50ac2bdc4ad7a98a86/pom.xml), [instrukcja Windows](https://github.com/LucasMatuszewski/jftp-modernization/blob/c0e8a0fa7f8be99907ccdc50ac2bdc4ad7a98a86/docs/windows-startup.md), [wcześniejsze dowody startu Windows](https://github.com/LucasMatuszewski/jftp-modernization/blob/c0e8a0fa7f8be99907ccdc50ac2bdc4ad7a98a86/docs/windows-startup-evidence/README.md).
- [Dostarczone testy](https://github.com/LucasMatuszewski/jftp-modernization/blob/c0e8a0fa7f8be99907ccdc50ac2bdc4ad7a98a86/src/test/java/com/myjavaworld/jftp/StartupStateTest.java), [fixture stanu](https://github.com/LucasMatuszewski/jftp-modernization/blob/c0e8a0fa7f8be99907ccdc50ac2bdc4ad7a98a86/src/test/java/com/myjavaworld/jftp/StartupStateProbe.java).

## Wąskie punkty wejścia do ćwiczeń

Wszystkie poniższe pliki są oryginalnymi źródłami aplikacji w wybranej bazie, a nie rozwiązaniami zadań:

| Zakres | Definicja i rzeczywiste użycie |
| --- | --- |
| Filtry | [LocalFileFilter.accept, wiersze 123-142](https://github.com/LucasMatuszewski/jftp-modernization/blob/c0e8a0fa7f8be99907ccdc50ac2bdc4ad7a98a86/src/main/java/com/myjavaworld/jftp/LocalFileFilter.java#L123-L142), [LocalFile.list(Filter), 215-232](https://github.com/LucasMatuszewski/jftp-modernization/blob/c0e8a0fa7f8be99907ccdc50ac2bdc4ad7a98a86/src/main/java/com/myjavaworld/jftp/LocalFile.java#L215-L232) |
| UI filtra | [LocalFileFilterDlg.getFilter i akcje, 132-155](https://github.com/LucasMatuszewski/jftp-modernization/blob/c0e8a0fa7f8be99907ccdc50ac2bdc4ad7a98a86/src/main/java/com/myjavaworld/jftp/LocalFileFilterDlg.java#L132-L155), [FTPSession.setLocalWorkingDirectory, 346-369](https://github.com/LucasMatuszewski/jftp-modernization/blob/c0e8a0fa7f8be99907ccdc50ac2bdc4ad7a98a86/src/main/java/com/myjavaworld/jftp/FTPSession.java#L346-L369) |
| Metadane pliku | [LocalFile(File), 51-68](https://github.com/LucasMatuszewski/jftp-modernization/blob/c0e8a0fa7f8be99907ccdc50ac2bdc4ad7a98a86/src/main/java/com/myjavaworld/jftp/LocalFile.java#L51-L68) |
| Sortowanie | [LocalFileComparator](https://github.com/LucasMatuszewski/jftp-modernization/blob/c0e8a0fa7f8be99907ccdc50ac2bdc4ad7a98a86/src/main/java/com/myjavaworld/jftp/LocalFileComparator.java), [LocalPane.sort, 295-311](https://github.com/LucasMatuszewski/jftp-modernization/blob/c0e8a0fa7f8be99907ccdc50ac2bdc4ad7a98a86/src/main/java/com/myjavaworld/jftp/LocalPane.java#L295-L311) |
| Polecenia konfiguracyjne | [RemoteHost, 150-179](https://github.com/LucasMatuszewski/jftp-modernization/blob/c0e8a0fa7f8be99907ccdc50ac2bdc4ad7a98a86/src/main/java/com/myjavaworld/jftp/RemoteHost.java#L150-L179), [ConnectionDlg](https://github.com/LucasMatuszewski/jftp-modernization/blob/c0e8a0fa7f8be99907ccdc50ac2bdc4ad7a98a86/src/main/java/com/myjavaworld/jftp/ConnectionDlg.java), [FavoritePropertiesDlg](https://github.com/LucasMatuszewski/jftp-modernization/blob/c0e8a0fa7f8be99907ccdc50ac2bdc4ad7a98a86/src/main/java/com/myjavaworld/jftp/FavoritePropertiesDlg.java) |
| Granica wykonania FTP | [FTPSession, 649-662](https://github.com/LucasMatuszewski/jftp-modernization/blob/c0e8a0fa7f8be99907ccdc50ac2bdc4ad7a98a86/src/main/java/com/myjavaworld/jftp/FTPSession.java#L649-L662) |

`Filter`, `RegexFilter` i `DateFilter` z pakietu `com.myjavaworld.util` pochodzą z zależności `com.myjavaworld:ftpapi:3.0.0`. Zbieżność pakietu nie oznacza, że ich definicje są w drzewie jFTP. Przed użyciem nieznanego API sprawdź źródła deklarowanej wersji lub rzeczywisty artefakt, import i wywołanie.

## Historia i licencja

Oryginalny autor: Sai Pullabhotla; projekt [sai-pullabhotla/jftp](https://github.com/sai-pullabhotla/jftp), copyright jMethods, Inc. Historia obu badanych forków zawiera import `76097f31e80109a3fb2921233e5be7da9606e3bd` z 07.05.2012. Oryginalny punkt porównawczy użyty w audycie to `14e62ceba4e371c2a0b955604b10f065f46f4f7d`. Zmiany kursowe mają własne commity; nie przypisujemy autorstwa oryginalnej aplikacji trenerowi ani uczestnikom.

Wybrana baza zachowuje [LICENSE.txt](https://github.com/LucasMatuszewski/jftp-modernization/blob/c0e8a0fa7f8be99907ccdc50ac2bdc4ad7a98a86/LICENSE.txt) i [NOTICE.txt](https://github.com/LucasMatuszewski/jftp-modernization/blob/c0e8a0fa7f8be99907ccdc50ac2bdc4ad7a98a86/NOTICE.txt), Apache License 2.0. Zachowaj je przy dalszym udostępnianiu kodu; licencje zależności sprawdza się osobno przed dystrybucją pakietu. Ten materiał nie kopiuje źródeł aplikacji ani bibliotek.

Porównano również [jftp-gr2/master na 956a867](https://github.com/LucasMatuszewski/jftp-gr2/tree/956a86755dd7e264f89422670c574209d8466cb3). Zawiera już 26 testów charakteryzujących; nie są nowymi zadaniami do rozwiązania. Jego konfiguracja z JUnit 3.8.1 zablokowała wykonanie testów z Maven 3.10.0/Surefire 3.6.0 w próbie 26.10.2026. Dlatego na dzisiejszy start wybieramy bazę z wrapperem i działającymi testami startu. Nie przechodź na inne gałęzie ani warianty wyglądu.
