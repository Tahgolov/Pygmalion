plugins {
    alias(libs.plugins.androidLibrary)
}

android {
    namespace = "com.vologhat.pygmalion"
    compileSdk = 34

    buildFeatures {
        androidResources = false
        buildConfig = true
    }
    defaultConfig {
        minSdk = 14
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }
    testOptions {
        targetSdk = 34
    }
    buildTypes {
        release {
            consumerProguardFiles("consumer-rules.pro")
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    packaging {
        resources {
            excludes += "/META-INF/**"
            excludes += "/*.properties"
        }
    }
}

dependencies {
    compileOnly(libs.androidx.annotation)
    implementation(libs.hiddenapibypass)

    androidTestImplementation(libs.ext.junit)
    androidTestImplementation(libs.espresso.core)
}