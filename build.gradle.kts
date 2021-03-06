// Top-level build file where you can add configuration options common to all sub-projects/modules.
buildscript {
    repositories {
        google()
        jcenter()

    }
    dependencies {
        classpath("com.android.tools.build:gradle:4.0.0-beta01")
        classpath("org.jetbrains.kotlin:kotlin-gradle-plugin:${embeddedKotlinVersion}")

        val nav_version = "2.3.0-alpha03";
        classpath("androidx.navigation:navigation-safe-args-gradle-plugin:$nav_version")

        // NOTE: Do not place your application dependencies here; they belong
        // in the individual module build.gradle files
    }
}

allprojects {
    repositories {
        google()
        jcenter()

    }
}

tasks {
    delete(rootProject.buildDir)
}
