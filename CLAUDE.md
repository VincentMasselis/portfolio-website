# CLAUDE.md

This file provides guidance to Claude Code (claude.ai/code) when working with code in this repository.

## Project Overview

Kotlin Multiplatform (KMP) portfolio website built with Compose Multiplatform. Targets Android, iOS, and WebAssembly (browser). Content is in French.

## Build Commands

```bash
# Web development server
./gradlew composeApp:wasmJsBrowserDevelopmentRun      # Wasm dev server

# Web production build
./gradlew composeApp:wasmJsBrowserProductionWebpack   # Wasm production bundle

# Tests
./gradlew composeApp:wasmJsBrowserTest                # Wasm browser tests
./gradlew composeApp:allTests                         # All platform tests

# Android
./gradlew androidApp:assembleDebug                    # Build Android debug APK
```

Web build artifacts go to `composeApp/build/dist/`.

## Architecture

**Gradle modules:**
- `composeApp` — Shared KMP library containing all Compose UI, data, navigation, and theme
- `androidApp` — Thin Android wrapper that depends on `composeApp`
- `iosApp/` — iOS app (Swift/Xcode, not a Gradle module)

**Source sets in `composeApp/src/`:**
- `commonMain` — All shared code: screens, components, theme, data, navigation
- `wasmJsMain` — Web entry point (`main.kt` using `ComposeViewport`), `index.html`, `styles.css`. Handles URL hash-based routing
- `iosMain` — `MainViewController` for SwiftUI integration
- `androidMain` — Currently empty (no platform-specific code needed)

**Package structure (`com.masselis.portfolio`):**
- `App.kt` — Root composable with navigation host, drawer, scaffold
- `data/PortfolioData.kt` — Static data object (skills, timeline, projects, contacts, repos)
- `ui/screens/Route.kt` — `sealed interface Route : Screen` base; each screen defines its route (`data object`) and `State` class in the same file as its composable and presenter
- `ui/screens/` — LandingScreen, AboutScreen, ProjectsScreen, ContactScreen
- `ui/components/` — TopNavBar, Footer, NavigationDrawerContent, ProjectCard, RepoCard, ContactCard, SkillBar, TimelineItem, Section
- `ui/theme/` — PortfolioTheme (dark color scheme, monospace typography), WindowSizeClass (Compact/Medium/Expanded via CompositionLocal)

**Key patterns:**
- Navigation: **Circuit** (Slack) — `Route : Screen` sealed interface, `Presenter<S>` with `@AssistedInject`, `@CircuitInject` on the factory and on the screen composable
- DI: **Metro** (`dev.zacsweers.metro`) — `@DependencyGraph`, `@BindingContainer`, `@Provides`, `@SingleIn(AppScope::class)`
- Responsive layout: `WindowSizeClass` enum (Compact < 600dp, Medium 600–1200dp, Expanded > 1200dp) via `LocalWindowSizeClass` CompositionLocal
- Web routing: URL paths (`/`, `/about`, `/projects`, `/contact`) synced with Circuit navigator in `wasmJsMain/main.kt`

**Design references** are in `doc/designs/`.

## Key Versions

Kotlin 2.3.10, Compose Multiplatform 1.10.0, AGP 9.0.0, Gradle 9.3.1, Material 3 1.11.0-alpha02, Navigation Compose 2.9.2, kotlinx-serialization 1.10.0. Android min SDK 24, target/compile SDK 36, JVM target 17. All versions managed in `gradle/libs.versions.toml`.

## Coding Style

### Navigation & DI (Circuit + Metro)
- Route + State: defined in the same file as the screen and presenter — `public data object Foo : Route` with inner `public data class State(...) : CircuitUiState`
- Presenter: `@AssistedInject` constructor taking `@Assisted screen` and `@Assisted navigator`; inner `@AssistedFactory interface Factory`; `@CircuitInject(Foo::class, AppScope::class)` on the factory
- Screen composable: `internal`, `@CircuitInject(Foo::class, AppScope::class)`, params `(state: Foo.State, modifier: Modifier = Modifier)`
- Screens with no runtime state: extend `StaticScreen`; annotate the composable directly with `@CircuitInject`, no presenter needed
- `explicitApi()` is active — all public declarations need explicit `public`; screen composables are `internal`

### Composable Structure
- Parameter order: data/config params → callback params → `modifier: Modifier = Modifier` → optional trailing content lambda
- Scrollable screens: `Box(modifier.fillMaxSize())` outer; `Column(Modifier.fillMaxSize().verticalScroll(scrollState))` inner; `VerticalScrollbar(scrollState, Modifier.align(Alignment.CenterEnd).fillMaxHeight())` overlaid
- Decompose each logical section into a `private` composable in the same file, named `<Concept>Section`, `<Concept>Item`, or `<Concept>Text`
- Every page-level content band: `Section(backgroundColor = …) { … }`; for the topmost section only, add `paddingValues = PaddingValues.Section.copy(top = LocalScaffoldPadding.current.calculateTopPadding())`
- Stateless by default — use `remember` / `rememberSaveable` only when the composable itself owns the state
- Conditional modifier chains: `.run { when (x) { … } }` not an if/else block

### Responsive Layout
- Read once per composable: `val windowSizeClass = LocalWindowSizeClass.current`
- Branch: `if (windowSizeClass == Compact) { /* mobile */ } else { /* desktop */ }`
- Never introduce a helper or enum for this — plain `if/else` is the pattern

### Naming
- Screen files and composables: `FooScreen`; route objects: bare noun (`Landing`, `About`)
- Private helpers: `<Concept>Section`, `<Concept>Item`, `<Concept>Text`
- Booleans: `isX`, `showX`, `hasX`; callbacks: `onX` or `openX`
- Extension files: `TypeName.ext.kt` (e.g., `Tag.ext.kt`)

### Functional Style
Prefer expressions and pipelines over statements and loops.

- `val` over `var` everywhere except Compose state (`rememberSaveable`) and platform bridge code
- `if` and `when` are expressions — assign the result: `val label = if (x) "a" else "b"`
- Collection pipelines over loops: `sortedByDescending { }`, `filter { }`, `map { }`, `fold`, `associateBy { }` — never a `for` loop when a pipeline fits
- `forEach` / `forEachIndexed` only inside composable rendering (where calling a composable is a side effect by nature); use `map` / `filter` / `fold` when computing a value
- `when` on sealed types / exhaustive enums: no `else` unless truly unreachable (`else -> error("…")`)
- `@JvmInline value class` for single-value wrappers
- Trailing lambdas always used when available

### Scope Functions
Scope functions eliminate intermediate variables and keep logic as a single expression chain. Reach for them whenever a temporary `val` would break the flow.

- `let` (`it`, returns lambda result): type transformation mid-chain or scoping a nullable — `.let { YearMonth(it.year, it.month) }`
- `run` (`this`, returns lambda result): conditional logic inside a fluent chain — `Modifier.run { when (lane) { 0 -> padding(start = x); 1 -> padding(end = x); else -> this } }`
- `with` (`this`, returns lambda result): grouping multiple calls on the same object into one expression — `with(canvas) { drawCircle(…); drawLine(…) }`
- `apply` (`this`, returns the object): inline configuration of a newly created object — `Path().apply { fillType = EvenOdd; addOval(…) }`
- `also` (`it`, returns the object): side effects that must not break the chain — `.also { log(it) }`

### Resources & Data
- All visible strings: `stringResource(Res.string.…)`; images: `painterResource(Res.drawable.…)` — never hardcode
- Static content: immutable `data class` + singleton `object` (e.g., `PortfolioData`)
- Sealed `interface` hierarchies for variant types

### Comments
- Write no comments by default
- Only comment when the WHY is non-obvious (hidden constraint, platform quirk, subtle invariant)
- Never comment what the code does — only why, if surprising
