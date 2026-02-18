import org.jetbrains.kotlin.gradle.ExperimentalWasmDsl
import org.jetbrains.kotlin.gradle.dsl.JvmTarget
import org.jetbrains.kotlin.gradle.tasks.KotlinCompilationTask

plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.androidLibrary)
    alias(libs.plugins.composeMultiplatform)
    alias(libs.plugins.composeCompiler)
    alias(libs.plugins.kotlinSerialization)
    alias(libs.plugins.metro)
    alias(libs.plugins.ksp)
    id("org.jetbrains.kotlin.plugin.parcelize")
}

kotlin {
    explicitApi()
    androidLibrary {
        namespace = "com.masselis.portfolio"
        minSdk = libs.versions.android.minSdk.get().toInt()
        compileSdk = libs.versions.android.compileSdk.get().toInt()
        compilerOptions {
            jvmTarget.set(libs.versions.android.jvmTarget.map(JvmTarget::fromTarget))
        }
        androidResources {
            enable = true
        }
    }

    listOf(
        iosArm64(),
        iosSimulatorArm64()
    ).forEach { iosTarget ->
        iosTarget.binaries.framework {
            baseName = "ComposeApp"
            isStatic = true
        }
    }

    js {
        browser()
        binaries.executable()
    }

    @OptIn(ExperimentalWasmDsl::class)
    wasmJs {
        browser()
        binaries.executable()
    }

    compilerOptions {
        freeCompilerArgs.addAll(
            "-P=plugin:org.jetbrains.kotlin.parcelize:additionalAnnotation=com.masselis.portfolio.utils.CommonParcelize",
            "-Xexpect-actual-classes"
        )
    }

    sourceSets {
        commonMain {
            kotlin {
                // KSP's metadata output directory isn't automatically added to the commonMain
                // source set. This line tells the Kotlin compiler to also compile the generated
                // factory files alongside your hand-written code.
                srcDir("build/generated/ksp/metadata/commonMain/kotlin")
            }
        }
        androidMain.dependencies {
            implementation(libs.compose.uiToolingPreview)
            implementation(libs.androidx.activity.compose)
            implementation(libs.ktor.client.okhttp)
        }
        commonMain.dependencies {
            implementation(libs.compose.runtime)
            implementation(libs.compose.foundation)
            implementation(libs.compose.material3)
            @Suppress("DEPRECATION")
            implementation(compose.materialIconsExtended)
            implementation(libs.compose.ui)
            implementation(libs.compose.components.resources)
            implementation(libs.compose.uiToolingPreview)
            implementation(libs.androidx.lifecycle.viewmodelCompose)
            implementation(libs.androidx.lifecycle.runtimeCompose)
            implementation(libs.kotlinx.serialization.json)
            implementation(libs.ktor.client.core)
            implementation(libs.ktor.client.content.negotiation)
            implementation(libs.ktor.serialization.kotlinx.json)
            implementation(libs.circuit.foundation)
            implementation(libs.circuit.codegen.annotations)
        }
        iosMain.dependencies {
            implementation(libs.ktor.client.darwin)
        }
        jsMain.dependencies {
            implementation(libs.ktor.client.js)
        }
        wasmJsMain.dependencies {
            implementation(libs.ktor.client.cio)
        }
    }
}

dependencies {
    androidRuntimeClasspath(libs.compose.uiTooling)
    // The special kspCommonMainMetadata configuration (not ksp or kspCommonMain) because in KMP,
    // KSP must run on the metadata compilation to produce code that works across all platforms.
    add("kspCommonMainMetadata", libs.circuit.codegen)
}

ksp { arg("circuit.codegen.mode", "metro") }

// Ensure KSP generates the Circuit factories before any Kotlin compilation runs
tasks.withType<KotlinCompilationTask<*>>().configureEach {
    dependsOn("kspCommonMainKotlinMetadata")
}
