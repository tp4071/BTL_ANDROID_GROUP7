plugins {
    alias(libs.plugins.android.application)
}

android {
    namespace = "com.example.libraryapplication"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.example.libraryapplication"
        minSdk = 25
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
}

dependencies {

    implementation(libs.appcompat)
    implementation(libs.material)
    implementation(libs.activity)
    implementation(libs.constraintlayout)
    testImplementation(libs.junit)
    androidTestImplementation(libs.ext.junit)
    androidTestImplementation(libs.espresso.core)
    // Retrofit
    implementation(libs.retrofit)
    implementation(libs.converter.gson)

    // OkHttp (logging)
    implementation(libs.logging.interceptor)

    // ViewModel + LiveData (Jetpack)
    implementation(libs.lifecycle.livedata)
    implementation(libs.lifecycle.viewmodel)

    //Chart
    implementation("com.github.PhilJay:MPAndroidChart:v3.1.0")
}