plugins {
    id("com.android.application")
}

android {
    namespace = "com.example.ingredientsapp"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.example.ingredientsapp"
        minSdk = 29
        targetSdk = 34
        versionCode = 1
        versionName = "0.1"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    packaging {
        resources.pickFirsts.add("META-INF/DEPENDENCIES")
    }

    lint {
        abortOnError = false
    }
}

dependencies {
    implementation("com.itextpdf:itextg:5.5.10")
    implementation("androidx.appcompat:appcompat:1.6.1")
    implementation("com.google.android.material:material:1.11.0")
    implementation("androidx.constraintlayout:constraintlayout:2.1.4")
    testImplementation("junit:junit:4.13.2")
    implementation("com.itextpdf:itext7-core:7.2.0")

}