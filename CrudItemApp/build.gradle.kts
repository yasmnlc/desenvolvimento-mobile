plugins {
    alias(libs.plugins.android.application) apply false
    alias(libs.plugins.kotlin.android) apply false
    id("com.google.gms.google-services") version "4.3.15" apply false
    id("org.jetbrains.compose") version "1.5.3" apply false
}
