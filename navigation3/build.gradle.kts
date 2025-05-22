plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.serialization)
    alias(libs.plugins.kotlin.compose)
}

android {
    namespace = "com.kotlearn.jetpack_navigation_3"
    compileSdk = 36

    defaultConfig {
        applicationId = "com.kotlearn.jetpack_navigation_3"
        minSdk = 26
        targetSdk = 36
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    kotlinOptions {
        jvmTarget = "11"
    }
    buildFeatures {
        compose = true
    }
}

dependencies {
    implementation(project(":notes"))

    // Core runtime for Jetpack Navigation 3 library — provides navigation components and APIs
    implementation("androidx.navigation3:navigation3-runtime:1.0.0-alpha01")

    // UI components for Navigation 3 — includes NavDisplay etc.
    implementation("androidx.navigation3:navigation3-ui:1.0.0-alpha01")

    // ViewModel integration with Navigation 3 — provides lifecycle-aware ViewModels scoped to navigation destinations
    implementation("androidx.lifecycle:lifecycle-viewmodel-navigation3:1.0.0-SNAPSHOT")

    implementation("androidx.activity:activity-compose:1.12.0-alpha01")
    implementation("androidx.compose.material3:material3:1.4.0-alpha13")

    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.material.icons.extended)
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.kotlinx.serialization.json)
    implementation(libs.kotlinx.serialization.core)
    debugImplementation(libs.androidx.ui.tooling)
}