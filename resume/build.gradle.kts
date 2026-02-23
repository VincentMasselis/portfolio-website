plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.composeMultiplatform)
    alias(libs.plugins.composeCompiler)
}

kotlin {
    js(IR) {
        browser()
        binaries.executable()
    }
    sourceSets {
        jsMain.dependencies {
            implementation(libs.compose.html.core)
            implementation(libs.compose.runtime)
            implementation(projects.core)
        }
    }
}
