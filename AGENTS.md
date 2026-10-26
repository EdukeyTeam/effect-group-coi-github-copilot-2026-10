# Repository Guidelines

## Project

This is the participant **materials repository** for "Agenci AI dla testerów automatycznych - GitHub Copilot", organized by Effect Group for COI, 26-27 October 2026, online, 09:00-16:00 Europe/Warsaw. Participants use their own COI computers with limited installation rights. Verify readiness; do not assume prepared VMs, administrator access, CI vendor, client API versions or Copilot seats.

The primary tools are GitHub Copilot in IntelliJ IDEA and GitHub Copilot CLI. Java/Selenium QA is the main track. Playwright comparison, agent browser exploration and native Android are separate topics. Codex, Claude Code and other configurations in the library are optional comparisons. No cloud provisioning, real client integrations or additional subscriptions are prerequisites.

Root `app/` stays an unsolved scaffold. Do not build a new application there automatically. Participants may explicitly choose the synthetic optional starter at `course-materials/exercises/coi-java/README.md`; then use its `SPEC.md`, Maven descriptor and scoped agent instructions. A real application stays in its own authorized checkout, with its own PRD, ADR, stack and verified commands. Teaching examples never define the client's architecture.

Chat and participant materials use Polish. Agent policy may use English. `PROGRAM.md` is the exact agreed program: do not rewrite it or create an application PRD for the course schedule. Root settings do not activate example agents, hooks, skills or model providers. Do not use Azure models for programming or review, enable the inactive Azure review workflow, or add secrets to it.

**Key docs** (created during the course - load only when in doubt):
- `docs/PRD.md` - product requirements and acceptance criteria
- `docs/ADR/` - Architecture Decision Records
- `docs/design-guidelines.md` - design system and tokens

---

## Repository Layout

```
app/                 Unsolved scaffold; no default application
assets/              Design tokens, logo, favicon
docs/                PRD, ADR, design system
course-materials/    Full optional library; explicitly selected Java QA starter
```

**Do not read `course-materials/` on your own.** It holds teaching examples from this and earlier courses: sample PRDs, ADRs, prompts and agent instructions. They are not requirements or conventions for this app. Do not browse, search or index it during normal work; open a file there only when the user names it or asks for course material.

---

**`README.md` is the participant entry page.** Do not add an update-notes section, changelog or "what's new" list to its top before the course has started. During the course the trainer adds update notes there; add them only when the trainer asks.

---

## Agent Workflow

### When implementing new or refactoring functionality
1. Read the relevant PRD and ADR files for the affected area.
2. Define the expected behavior from the specification before writing or changing any code.
3. Confirm the actual application checkout and inspect source, build descriptors, call sites and dependencies before proposing commands or APIs. Identify build, test and startup commands from that project, using its wrapper when available; do not assume a framework, build tool or runtime version.
4. Establish missing or ambiguous requirements with the user and record agreed application scope in its PRD before implementation. Routine course-document changes do not need an application PRD or ADR.

### TDD Rules
For every feature and bug fix:
1. Start from the specification, not the existing implementation.
2. Write or extend tests **before** production code.
3. Run the new tests and confirm they fail for the expected reason.
4. Implement the minimum code needed to make them pass.
5. Run the full verification suite for the changed scope.
6. Refactor only while tests stay green.
7. **Manually validate the running application** (see Manual QA below). Automated tests - especially E2E - can produce false passes; a task is not done until the real app has been exercised by hand.

If the area has no suitable test infrastructure yet, add it as part of the task - do not silently skip tests.

Before creating or refreshing a unit/integration mock of an endpoint or database, make a fresh real request/query in an authorized local/test environment using synthetic data. Save the complete response verbatim (HTTP status, headers and body, or database result rows and type/schema information) and derive the mock from that capture, not from documentation that may be stale. Use a response containing no secrets; record its date, target, request/query and relevant service version without credentials. If a safe capture cannot be obtained, report the missing prerequisite instead of inventing a response shape.

For behavior-preserving legacy refactoring, write characterization tests against the original code and get them green before changing it. Assert observable inputs, outputs, state changes and errors rather than private methods or the current class layout. Demonstrate that critical assertions detect a controlled behavioral mutation, then revert that mutation. Existing tests may protect a pure refactor; a new behavior or bug fix still requires Red -> Green -> Refactor.

Investigate production code and fixtures before changing a failing assertion. Do not weaken, disable or delete tests to conceal a defect. Change expected behavior only when the specification supports it, and explain why.

### Manual QA (required after every task that affects the running app)

Use the application's actual runtime and verified commands. The browser flow below applies to web applications; desktop applications need their real UI, and command-line tools need observable CLI checks.

Use **the Playwright CLI**, if already installed and approved, to drive the real running app like a human tester. Otherwise perform and record a manual browser check without installing extra tooling:

1. Start the app with its verified project command and open it in the browser. Record successful startup separately from compilation and test execution.
2. Take a screenshot of every screen you touched.
3. Exercise the real flow end-to-end by hand: for the Java fixture, select a slot, enter party size, submit and verify the documented result; for another application, follow its actual changed flow.
4. Verify the flow actually works: correct navigation, correct data displayed, no console errors, Polish UI text.
5. **For an application with an agreed brand, compare your screenshots against the brand reference** (`assets/homepage.png` + `docs/design-guidelines.md` tokens): colors, typography, spacing, button styles and logo placement must match the application's agreed brand reference. The synthetic Java fixture uses its own plain, Polish UI; do not invent a COI brand reference.
6. Report what you validated (steps + screenshots) in your task summary. If anything looks or behaves wrong, fix it before committing.

### Verification (required before every commit)

Verify only the scope relevant to your change. If the change affects runtime behavior, confirm the app starts correctly.

**Test Strategy:**
| Type | Mocks | Who |
|---|---|---|
| Unit | All deps | be/fe-dev |
| Integration | Only external LLM API | be-dev |
| E2E | NOTHING (real stack) | qa-engineer |

**Verification:** For runtime changes, start the real application and finish with Manual QA. Record compilation/build, test execution and startup as separate checks, including discovery counts and skips. A successful build or E2E run alone does not establish that the app works. Documentation-only changes use diff, link and consistency checks and do not require application startup. Fix errors and warnings introduced by the change; report pre-existing failures without hiding them.

**Env Vars:** The optional Java fixture needs no API key. `.env.example` belongs to optional application examples; never require an LLM provider key for the Java starter.

### Commit Rules
- Commit only after verification passes and the changed scope is in a working state.
- Keep commits focused: one logical change per commit.
- Format: `Area: short summary` (e.g. `Backend:`, `Frontend:`, `Docs:`)
- Do **not** push to remote unless the user explicitly asks.

### Git Worktrees
- Use this checkout by default. Create a worktree only when requested or needed for parallel agent work; ask first when not explicitly requested.
- Remove worktrees with `git worktree remove <path>` and run `git worktree prune` when done.

### Completion Criteria
A task is complete only when:
- Implementation matches the relevant PRD, ADR, and design guidance
- Tests were written first and pass honestly
- Verification for the changed scope passed with no errors or warnings
- Runtime changes passed Manual QA on the actual UI, CLI or API, with screenshots or command/request evidence and the application's own visual reference where applicable; documentation-only work passed its scoped checks
- The commit message is focused and the repository is in a consistent, reviewable state
