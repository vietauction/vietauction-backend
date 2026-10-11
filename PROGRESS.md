# Progress

### 2026-10-11 — Local backend cloud database launcher

- Status: Complete.
- Changes:
  - Added a secret-free `scripts/run-dev-cloud.sh` launcher for the local `dev` backend profile.
  - Added the ignored `.env.local` convention and a non-secret `.env.local.example` template.
  - Documented the JDK 21 and cloud database local-run workflow.
- Validation:
  - Backend started locally with the cloud datasource, and the frontend BFF reached it successfully.
  - Local `DB_AUCTION` schema was removed and the local MySQL service was stopped.
- Remaining limitations:
  - The cloud MySQL port is publicly reachable; it should eventually be restricted to approved network paths.
- Next action:
  - Use `./scripts/run-dev-cloud.sh` for future local backend sessions.
