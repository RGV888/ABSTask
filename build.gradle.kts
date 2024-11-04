// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
    alias(libs.plugins.android.application) apply false
    alias(libs.plugins.kotlin.android) apply false
    alias(libs.plugins.hilt.android) apply false
    alias(libs.plugins.devtools.ksp) apply false
    alias(libs.plugins.kotlin.parcelize) apply false


    //id("org.jetbrains.kotlin.jvm") version "1.9.0"
    // STEP 2: Apply the KSP plugin
  //  id("com.google.devtools.ksp") version "1.9.0-1.0.12"
  //  id("com.google.dagger.hilt.android") version "2.51.1" apply false
}