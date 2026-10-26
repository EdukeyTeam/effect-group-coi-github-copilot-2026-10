# Test Notebook - pomysł aplikacji do wspólnego dopracowania

Lekki system dla zespołu testerów. Wybierzemy pierwszy zakres razem, a wymagania i decyzje architektoniczne opracujemy podczas warsztatu. Poniższy opis jest punktem wyjścia do rozmowy, podobnie jak przykłady aplikacji do reklamacji i analityki SQL.

## Opis produktu - tekst do skopiowania

Opis jest po angielsku, podobnie jak przykłady reklamacji i analityki SQL. Skopiuj całość od następnego akapitu do końca opisu; omówienie i wspólne decyzje prowadzimy po polsku.

We are considering a small application called **Test Notebook** for a QA team that needs a convenient place to organize test cases and record what was actually tested.

The application could work like this:

- A user creates a **project** with a name and short description. Each project has its own test cases and test runs.
- A form lets the user create and edit a **test case**: title, preconditions, priority, tags, and a list of actions with expected results. Cases can be searched, filtered and reused.
- The user selects cases for a **test run**, adds a release/build label and the tested environment, then records the actual result, a comment and an optional evidence or defect link. Results distinguish **Not Run, Passed, Failed, Blocked and Skipped**. An unfinished run can be saved and resumed.
- Previous runs remain available. Editing a reusable case must not change the steps and expected results shown in an earlier execution. A new attempt records a new result instead of erasing the previous failure.
- A simple **dashboard** shows the number of results in each state, current progress and a small history chart. Untested or blocked cases must remain visible rather than appearing as passed.
- Optional: a **Kanban board** for organizing the work in a selected run. Moving work to Done does not automatically mean that its test passed; execution progress and test outcome are different things.
- Basic **change history** records what changed and when. A typed display label may identify a course exercise, but it is not an authenticated identity.

Start with **SQLite**. A small Next.js application is one possible approach to discuss, not a fixed architecture decision. We will choose the first useful slice and record technology choices in an ADR together.

Authentication is **out of scope for the workshop version**. Possible later extensions include basic email/password login without email sending, migration to PostgreSQL, CSV import/export and importing automated results. They are not prerequisites for the first working flow.

Use a small example project and a few test cases to start. We are preparing an application idea here, not a complete PRD or a finished implementation.

## Od czego możemy zacząć

Projekt -> jeden przypadek z krokami -> jedno wykonanie -> zapisany wynik -> prosty widok podsumowania. Potem wybierzemy z grupą kolejne rozszerzenie.
