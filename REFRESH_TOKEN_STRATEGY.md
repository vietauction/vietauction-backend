# VietAuction Refresh Token Strategy

## Decision

Use short-lived access tokens with rotating, one-time refresh tokens. The browser communicates only with same-origin Next.js Route Handlers; both tokens remain in `HttpOnly`, `Secure` cookies and are never exposed to React state or `localStorage`.

Recommended lifetimes:

- Access token: 10–15 minutes.
- Refresh token: 14 days absolute lifetime.
- Inactivity timeout: 7 days, enforced from the stored `last_used_at` value.

## Target flow

1. Login or registration creates one server-side session and returns an access token plus refresh token.
2. Next.js stores both values in `HttpOnly`, `Secure`, `SameSite=Lax` cookies. The refresh cookie is scoped to `/api/auth`.
3. A protected BFF request that receives HTTP 401 calls `POST /api/auth/refresh` once.
4. The BFF sends the refresh token to Spring Boot through `POST /api/v1/auth/refresh-token`.
5. Spring validates the token and session, revokes the presented refresh token, creates a new refresh token in the same token family, and returns a new access/refresh pair.
6. Next.js replaces both cookies and retries the original request once. A second 401 clears the cookies and returns an unauthenticated response.

## Backend model

Replace raw refresh-token persistence with a session record containing:

- `id` or JWT `jti`.
- `user_id`.
- `family_id` shared by all rotations from one login.
- `refresh_token_hash` using SHA-256 or HMAC-SHA-256; never persist the raw token.
- `expires_at`, `last_used_at`, `revoked_at` and `replaced_by_id`.
- `created_at`, IP address and normalized device information.

Keep multiple sessions per user so logging in on one device does not revoke every other device. Provide explicit “logout current device” and “logout all devices” operations.

## Rotation transaction

`AuthService.refreshToken` must run in one transaction:

1. Parse and validate signature, issuer, audience, token type, `jti`, subject and expiration.
2. Lock the matching active session row to prevent two concurrent rotations.
3. Compare the hash of the presented token with the stored hash.
4. If the token is expired, revoked or already replaced, revoke the whole family as reuse detection and return HTTP 401.
5. Revoke the presented token, generate a new refresh token and access token, and link the old row to the replacement.
6. Commit before returning the new pair.

The refresh response must use the existing typed `ApiResponse<AuthResponse>` envelope. Validation failures return HTTP 401; malformed requests return HTTP 400; unexpected failures return the shared safe error response.

## Frontend/BFF behavior

- Add `POST /api/auth/refresh`; it reads the refresh cookie server-side and never accepts a browser-provided token body.
- Centralize authenticated backend calls in one server-only client.
- On access-token HTTP 401, use a single-flight refresh promise so concurrent requests perform only one rotation.
- Retry the failed request exactly once after a successful refresh.
- Never refresh login, register, refresh or logout requests recursively.
- Clear both cookies when refresh fails or reuse is detected.
- Apply an Origin/Host check to state-changing BFF routes in addition to `SameSite` cookies.

## Security requirements

- Move signing keys and token lifetimes out of source files and into environment variables.
- Prefer a separate refresh-token signing key or an opaque cryptographically random 256-bit refresh token.
- Never log token values, authorization headers or cookie contents.
- Rate-limit login and refresh endpoints by account and IP.
- Keep HTTPS mandatory between browser, Vercel and backend.
- Schedule cleanup of expired/revoked sessions while retaining enough metadata for security audit and reuse detection.

## Delivery sequence

1. Add the session migration and repository queries, including row locking and family revocation.
2. Implement backend rotation and current/all-device logout with focused unit and integration tests.
3. Add the Next.js refresh Route Handler and one-retry server client.
4. Test normal rotation, concurrent requests, expired refresh, revoked refresh, reuse detection, logout and multi-device sessions.
5. Roll out behind an environment flag, observe refresh failure rate, then remove the current raw-token path.
