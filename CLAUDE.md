# CLAUDE.md

This file provides guidance to Claude Code (claude.ai/code) when working with code in this repository.

## Project overview

TwoGether is a Spring Boot server-rendered (Thymeleaf) web app for couples to share calendar schedules, track shared expenses, and log date "records" (memories). Authentication is session-based via Spring Security; a couple is formed when one member's invite code is redeemed by another member.

## Commands

```bash
# Run the app locally (uses the `local` profile: MySQL on localhost:3306, db `think_different`)
./gradlew bootRun --args='--spring.profiles.active=local'

# Build (produces build/libs/think_different-0.0.1-SNAPSHOT.jar)
./gradlew build

# Build without running tests (used in CI/deploy)
./gradlew clean build -x test

# Run all tests
./gradlew test

# Run a single test class
./gradlew test --tests "com.think_different.think_different.ThinkDifferentApplicationTests"
```

There is currently only a Spring context-load smoke test (`ThinkDifferentApplicationTests`); there is no meaningful automated test coverage over domain logic yet.

Local dev requires a MySQL instance matching `src/main/resources/application-local.yaml` (`jdbc:mysql://localhost:3306/think_different`, user `root`/`root`). `spring.jpa.hibernate.ddl-auto` is `update`, so schema is auto-migrated from entities — no separate migration tool (e.g. Flyway/Liquibase) is used.

Production runs via Docker: `Dockerfile` copies the built jar and starts it with `--spring.profiles.active=prod` (see `application-prod.yaml` for prod datasource/config). Deployment is via the manually-triggered GitHub Actions workflow `.github/workflows/deploy.yml`, which builds the jar and ships it to an EC2 host over SCP/SSH (kills the previous `java -jar` process and restarts).

## Architecture

### Package-by-domain structure

Code under `src/main/java/com/think_different/think_different/` is organized by business domain, not by technical layer. Each domain package (`calendar`, `couple`, `expense`, `record`, `member`, `dashboard`, `statistics`, `transaction`) follows the same internal shape:

```
<domain>/controller/   # @Controller, returns Thymeleaf view names (server-rendered, not REST/JSON)
<domain>/service/      # @Service @Transactional, business logic
<domain>/domain|entity/ # @Entity JPA classes
<domain>/repository/   # Spring Data JPA repositories
<domain>/dto/          # request/response DTOs used by controllers/services
```

Cross-cutting config lives in `config/webSecurity` (Spring Security + `CustomUserDetails`/`CustomUserDetailsService`) and `config/webConfig`. `common/file/FileUploadService` handles image uploads (used by record images / profile images), writing to `uploads/` (served statically and permitted anonymously in `SecurityConfig`).

Note: some domains use `domain/` for entities (e.g. `couple`, `expense`, `transaction`) and others use `entity/` (e.g. `member`, `calendar`, `record`) — this is inconsistent in the existing code, not a convention to infer meaning from. Also note the `couple` service package is misspelled `servicce` (typo, not a typo to "fix" incidentally while touching unrelated code).

### Couple-centric data model

The core relationship is `Member` (account/login identity) → `CoupleMember` (join table with per-couple nickname/profile image) → `Couple` (the shared space). Almost every domain entity (`Expense`, `Calendar`/schedule, `DateRecord`, etc.) is scoped to a `Couple`, not directly to a `Member`. The standard pattern in services is:

```java
CoupleMember coupleMember = coupleMemberRepository.findByMember(member)
        .orElseThrow(() -> new IllegalArgumentException("커플 연결 정보가 없습니다."));
Couple couple = coupleMember.getCouple();
// ...query/mutate using `couple`, not `member`, from here on
```

When adding a feature that reads or writes couple-scoped data, resolve `Couple` through `CoupleMemberRepository` first rather than assuming a direct `Member` association exists.

Couple formation: one member calls `CoupleService.createInviteCode` to get/reuse an unused `InviteCode`; the partner calls `connectCouple(member, code)`, which validates the code (not self-used, not already used, inviter not already connected), creates a `Couple` + two `CoupleMember` rows, and marks the code used. There's no "leave couple" flow currently in `CoupleService`.

### Auth / request identity

`CustomUserDetails` wraps the `Member` entity as the Spring Security principal; controllers pull the current user via `@AuthenticationPrincipal CustomUserDetails customUserDetails` then `customUserDetails.getMember()`. Login is form-based (`/members/login`, custom `loginId`/`password` params, success redirect `/main`), configured in `SecurityConfig`. Static assets (`/css/**`, `/js/**`, `/images/**`, `/uploads/**`) bypass the security filter entirely via `WebSecurityCustomizer`. Most non-public routes fall through to `.anyRequest().authenticated()` — new routes are authenticated by default unless explicitly listed as `permitAll()`.

### Rendering

Views are Thymeleaf templates under `src/main/resources/templates/<domain>/*.html`, composed with `layout/default.html` and `layout/login.html` via `thymeleaf-layout-dialect`, plus shared `fragments/header.html`. Controllers return view name strings (e.g. `"expense/expense"`), not JSON — this is not a REST API backend. Per-page CSS/JS lives under `static/css/<domain>/` and `static/js/<domain>/`, loaded by the corresponding template.
