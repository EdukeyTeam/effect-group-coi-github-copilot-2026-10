# Optional Java QA starter

Use this subtree only after the participant explicitly selects it. Read SPEC.md and the selected WORKSHOP.md exercise before edits. This is a synthetic teaching fixture, not a client application or a complete product. Do not read other course library files automatically.

Use Polish prompts and UI. Verify JDK/Maven first; pom.xml is the command/version source. Default `mvn test` runs only policy and real loopback HTTP tests. `-Pcontract` and `-Pselenium` explicitly add their source sets and dependencies. Never silently skip a missing browser or claim compilation equals execution. Do not install Java, Maven, browsers, drivers or plugins without the participant's authorization and IT policy.

POST /api/reservations intentionally returns 501. Do not complete it unless the selected exercise authorizes one named acceptance case. First add a behavior test and demonstrate the expected failure; report already-passing cases as existing coverage. Never weaken assertions, add sleeps/retries or copy a solved answer. Faulty text examples in evidence/ are inert and must remain outside default discovery.

Tests create and close real local fixtures; do not contact external endpoints. Do not create an endpoint mock before a fresh authorized full-response capture without secrets. Supplied evidence is synthetic, not a real capture. Keep target/ local. Capture status, headers, body, command, test counts, failures and skips in the report. Manual browser QA is separate from Selenium test success. Do not provision native Android or expose ADB. No secrets, private data, hooks, provider profiles, Git publication or CI edits.
