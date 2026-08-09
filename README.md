# vocal-harmonys-backend

REST API for the [Vocal Harmony's](../vocal-harmonys) choir site: member accounts and
authentication, the rehearsal répertoire and its audio tracks, donations, "réserver une
prestation" / "rejoindre la chorale" submissions, the CD boutique, and every other piece of
content the site shows (choristers, events, gallery, news, partners).

## Stack

Java 17, Spring Boot 3.3 (Web, Data JPA, Security, Validation), PostgreSQL, Flyway, Maven.
No Lombok — every class has explicit constructors/getters/setters so it reads top to
bottom without knowing an annotation processor. Request/response DTOs are Java `record`s.

## Running it

```bash
docker compose up -d          # starts Postgres on localhost:5432
./mvnw spring-boot:run        # or: mvn spring-boot:run
```

Flyway applies `src/main/resources/db/migration/*.sql` automatically on startup — the
schema and the seed data (choristers, songs, events, CDs, two demo member accounts...)
are ready as soon as the app is up. Demo login: username `marie` or `jean`, password
`gospel2026`.

The API listens on `http://localhost:8080`, CORS-open to the Vite dev server
(`http://localhost:5173`/`5174` by default — see `app.cors.allowed-origins` in
`application.yml`).

Swagger UI is at `http://localhost:8080/swagger-ui.html` (raw OpenAPI spec at
`/v3/api-docs`) — both are public (see `OpenApiConfig` / `SecurityConfig`). Every
protected endpoint is marked with a lock icon; hit `POST /api/auth/login` first, then
click "Authorize" and paste the returned token in to try them from the UI.

## How it's organized

This is a classic **layered** architecture — every package holds one layer, and (almost)
every domain (choristers, songs, donations...) has one file per layer:

```
controller/   HTTP in, HTTP out. Reads the request, calls one service method, returns
              a DTO. No business logic lives here.
service/      The business logic. Validates things the @Valid annotations can't
              (e.g. "does this CD id exist"), maps between entities and DTOs, and is
              the only layer that calls more than one repository when a feature needs it
              (e.g. CdOrderService reads a Cd via CdService before saving a CdOrder).
repository/   Spring Data JPA interfaces — `extends JpaRepository<Entity, Long>` plus a
              few derived-query methods (e.g. `findAllByOrderByDisplayOrderAsc`). No
              implementation to write; Spring generates it.
entity/       The JPA-mapped classes — one per database table, plus the enums they use
              (VoicePart, TrackType, RequestStatus, Role).
dto/          What actually crosses the HTTP boundary. Entities are never returned
              directly — a `*Response` record shapes exactly what the frontend gets, and
              a `*Request` record (with validation annotations) shapes exactly what a
              client can send in. This is also where a "snapshot" like `CdOrder`'s
              price-at-order-time gets computed, in the service that builds the entity
              from the request.
security/     JWT issuing/parsing (JwtService), the filter that reads the
              Authorization header on every request (JwtAuthenticationFilter), and how
              Spring Security loads a Member for a username (MemberUserDetailsService).
config/       SecurityConfig (which routes are public vs. need a token — see the comment
              at the top of that file for the exact list) and CorsConfig.
exception/    ResourceNotFoundException + a @RestControllerAdvice (GlobalExceptionHandler)
              that turns it, a bad-login, a validation failure, or anything unexpected
              into the same ErrorResponse JSON shape.
```

A typical request, e.g. `GET /api/songs`:

1. `JwtAuthenticationFilter` checks the `Authorization` header before the request even
   reaches a controller — `/api/songs` isn't in `SecurityConfig`'s public list, so no
   valid token means a 401 from here, controller never runs.
2. `SongController.listAll()` calls `SongService.listAll()`.
3. `SongService` calls `SongRepository.findAllByOrderByIdAsc()` (which also eager-loads
   each song's tracks, see the `@EntityGraph` on that method) and maps each `Song` entity
   to a `SongResponse` record.
4. Spring serializes the list of `SongResponse` to JSON.

## Database migrations

`db/migration/V1__schema.sql` creates every table; `V2__seed_data.sql` inserts today's
content (this is the same data the frontend used to hardcode in `src/data.ts` before the
split). Flyway tracks which migrations have run in a `flyway_schema_history` table — add a
new `V3__...sql` file for any further schema change, never edit `V1`/`V2` after they've
run against a real database.

## Auth

Stateless JWT: `POST /api/auth/login` checks the password (BCrypt, via Spring Security's
`AuthenticationManager`) and returns a token; every other protected endpoint expects
`Authorization: Bearer <token>`. There's no session/cookie state on the server — each
request re-proves who it is.

## What's public vs. protected

- Public: `POST /api/auth/login`, every `GET` on the showcase content (choristers,
  events, gallery, news, partners, cds), and the four "submit a form" endpoints
  (`POST /api/donations`, `/api/reservations`, `/api/join-applications`, `/api/cd-orders`).
- Everything else needs a token: the répertoire (`/api/songs`), `GET /api/auth/me`,
  every write (`POST`/`PUT`/`DELETE`) to the showcase content, and reading submitted
  forms back (`GET` on those same four submission endpoints).

See `SecurityConfig` for the exact route list.
