plugins {
    alias(libs.plugins.android.application)
    id("org.jetbrains.kotlin.android")
    id("com.google.gms.google-services")
    id("org.jetbrains.compose")
}

android {
    namespace = "com.example.authapp2"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.example.authapp2"
        minSdk = 24
        targetSdk = 35
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
    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.3"
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}

dependencies {

    // Firebase
    implementation(platform("com.google.firebase:firebase-bom:33.7.0"))
    implementation("com.google.firebase:firebase-analytics")
    implementation("com.google.firebase:firebase-auth-ktx")
    implementation("com.google.firebase:firebase-messaging-ktx:24.1.0")

    implementation("com.google.firebase:firebase-firestore-ktx")

    // Google Sign-In (Para Social Login com Google)
    implementation("com.google.android.gms:play-services-auth:20.7.0")

    // Jetpack Compose
    implementation(platform("androidx.compose:compose-bom:2023.09.01"))

    constraints {
        implementation("androidx.compose:compose-bom:2023.09.01") {
            because("forçando a versão 2023.09.01 para consistência em todo o projeto")
        }
    }
    implementation("androidx.compose.ui:ui")
    implementation("androidx.compose.material3:material3")
    implementation("androidx.compose.ui:ui-tooling-preview")
    implementation("com.google.firebase:firebase-database-ktx")

    debugImplementation("androidx.compose.ui:ui-tooling")

    // Navigation Compose
    implementation("androidx.navigation:navigation-compose:2.7.2")

    // Coroutines
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:1.7.3")

    // Lifecycle
    implementation("androidx.lifecycle:lifecycle-runtime-compose:2.6.2")
    implementation("androidx.lifecycle:lifecycle-viewmodel-compose:2.6.2")

    // AndroidX Core
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)

    // Testes
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)

}