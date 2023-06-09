@file:Suppress("DSL_SCOPE_VIOLATION")

buildscript {
    extra.set("compose_version", "1.4.2")

    dependencies {
        classpath("com.google.gms:google-services:4.3.15")
    }
} // Top-level build file where you can add configuration options common to all sub-projects/modules.

plugins {
    alias(libs.plugins.android.application) apply false
    alias(libs.plugins.android.library) apply false
    alias(libs.plugins.kotlin) apply false
    alias(libs.plugins.kotlin.jvm) apply false
    alias(libs.plugins.hilt) apply false
    alias(libs.plugins.androidx.navigation.safe.args) apply false
    alias(libs.plugins.ktlint) apply true
}

subprojects {
    apply(plugin = "org.jlleitschuh.gradle.ktlint")
}