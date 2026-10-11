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

### 2026-10-11 — Public auction catalogue read API

- Status: Source complete; awaiting CI verification.
- Changes:
  - Replaced the empty public auction list and detail payloads with the typed `AuctionCatalogItemResponse` contract.
  - Added pagination, keyword, auction-state, product-category, date-range and supported sort filters to `GET /api/v1/auctions`.
  - Added `GET /api/v1/auctions/{id}` with the shared not-found error contract.
  - Exposed only public catalogue data: auction prices and timing, bid count, and a compact product/category/image summary; staff, seller, registration and transaction information remain private.
  - Added focused service tests for mapping, pagination limits and the not-found path.
- Validation:
  - Ran `git diff --check` only. Maven test/build and live API verification were intentionally not run under the current no-build/no-runtime-verification instruction.
- Remaining limitations:
  - Create, state update and delete auction endpoints are still legacy placeholders and are not part of this read-only catalogue slice.
  - The frontend still consumes mock catalogue data until its adapter task is implemented.
- Next action:
  - Add the frontend catalogue adapter and replace landing/list/detail mock reads while preserving loading, empty and error states.

### 2026-10-11 — Seed public auction catalogue and categories

- Status: Source complete; awaiting CI verification.
- Changes:
  - Extended `seedData.sql` with deterministic jewellery catalogue categories, 12 assets, local-image references, auctions across waiting/ongoing/finished/paused states, and bid history.
  - Replaced the broken read-only product-category placeholders with typed, database-backed list and detail endpoints.
  - Added focused tests for category mapping and the category not-found contract.
- Validation:
  - Maven test/build and live database execution were intentionally not run under the current no-build/no-runtime-verification instruction.
- Remaining limitations:
  - The seed data is development-only and must be intentionally applied to a non-production database; it is not an automatic migration.
  - The legacy category create/delete endpoints remain placeholders and are outside the read-only catalogue scope.
- Next action:
  - Implement the frontend HTTP catalogue repository, then migrate the landing, list and detail reads from fixtures to the public API.
