# CLAUDE.md

This file provides guidance to Claude Code (claude.ai/code) when working with code in this repository.

## Project Overview

Kotlin Multiplatform (KMP) portfolio website built with Compose Multiplatform. Targets Android, iOS, JavaScript (browser), and WebAssembly (browser).

## Build Commands

```bash
# Web development servers
./gradlew composeApp:jsBrowserDevelopmentRun          # JS dev server
./gradlew composeApp:wasmJsBrowserDevelopmentRun      # Wasm dev server

# Web production builds
./gradlew composeApp:jsBrowserProductionWebpack       # JS production bundle
./gradlew composeApp:wasmJsBrowserProductionWebpack   # Wasm production bundle
./gradlew composeApp:composeCompatibilityBrowserDistribution  # Combined JS+Wasm with fallback

# Tests
./gradlew composeApp:jsBrowserTest                    # JS browser tests
./gradlew composeApp:wasmJsBrowserTest                # Wasm browser tests
./gradlew composeApp:allTests                         # All platform tests

# Android
./gradlew androidApp:assembleDebug                    # Build Android debug APK
```

Web build artifacts go to `composeApp/build/dist/`. The combined distribution (`composeWebCompatibility/productionExecutable/`) serves Wasm with JS fallback.

## Architecture

**Two modules:**
- `composeApp` — Shared KMP library containing all Compose UI and platform abstractions
- `androidApp` — Thin Android wrapper that depends on `composeApp`
- `iosApp/` — iOS app (Swift/Xcode, not a Gradle module)

**Platform source sets in `composeApp/src/`:**
- `commonMain` — Shared Compose UI (`App.kt`), `Platform` interface, `Greeting` class
- `webMain` — Web entry point (`main.kt` using `ComposeViewport`), `index.html`, `styles.css`
- `jsMain` / `wasmJsMain` — Platform-specific `getPlatform()` implementations for each web target
- `androidMain` — Android `getPlatform()` implementation
- `iosMain` — iOS `getPlatform()` and `MainViewController` for SwiftUI integration

**Key pattern:** Uses Kotlin `expect`/`actual` for `Platform` interface and `getPlatform()` function across all targets.

## Key Versions

Kotlin 2.3.10, Compose Multiplatform 1.10.0, AGP 9.0.0, Gradle 9.3.1, Material 3. Android min SDK 24, target/compile SDK 36, JVM target 17. All versions managed in `gradle/libs.versions.toml`.
