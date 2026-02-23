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
- `navigation/Routes.kt` — Type-safe navigation routes using `@Serializable` data objects (Home, About, Projects, Contact)
- `ui/screens/` — LandingScreen, AboutScreen, ProjectsScreen, ContactScreen
- `ui/components/` — TopNavBar, Footer, NavigationDrawerContent, ProjectCard, RepoCard, ContactCard, SkillBar, TimelineItem, Section
- `ui/theme/` — PortfolioTheme (dark color scheme, monospace typography), WindowSizeClass (Compact/Medium/Expanded via CompositionLocal)

**Key patterns:**
- Navigation: Jetpack Navigation Compose with type-safe `@Serializable` routes and `kotlinx.serialization`
- Responsive layout: `WindowSizeClass` enum (Compact < 600dp, Medium 600–1200dp, Expanded > 1200dp) provided via `LocalWindowSizeClass` CompositionLocal
- Web routing: URL hash fragments (`#about`, `#projects`, `#contact`) synced with navigation state in `wasmJsMain/main.kt`

**Design references** are in `doc/designs/`.

## Key Versions

Kotlin 2.3.10, Compose Multiplatform 1.10.0, AGP 9.0.0, Gradle 9.3.1, Material 3 1.11.0-alpha02, Navigation Compose 2.9.2, kotlinx-serialization 1.10.0. Android min SDK 24, target/compile SDK 36, JVM target 17. All versions managed in `gradle/libs.versions.toml`.
