plugins {
    alias(libs.plugins.androidApplication)
    alias(libs.plugins.jetbrainsKotlinAndroid)
}

android {
    namespace = "com.stlvrtx.mawatchface"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.stlvrtx.mawatchface"
        minSdk = 30
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"
        vectorDrawables {
            useSupportLibrary = true
        }
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
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.0"
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}

dependencies {
    implementation(libs.play.services.wearable)

    implementation(libs.core.ktx)
    implementation(libs.play.services.wearable.v1800)
    implementation(libs.percentlayout)
    implementation(libs.legacy.support.v4)
    implementation(libs.recyclerview)
    implementation(libs.lifecycle.runtime.ktx)
    implementation(libs.activity.compose)
    implementation(libs.watchface.complications.rendering)

    // Jetpack Compose
    implementation(platform(libs.compose.bom))
    androidTestImplementation(platform(libs.compose.bom.v20230300))

    // Design libraries
    implementation("androidx.compose.ui:ui")
    implementation(libs.compose.material)
    implementation(libs.compose.foundation)

    // Android Studio Preview support
    implementation("androidx.compose.ui:ui-tooling-preview")
    debugImplementation("androidx.compose.ui:ui-tooling")

    // UI Tests
    androidTestImplementation("androidx.compose.ui:ui-test-junit4")
    debugImplementation("androidx.compose.ui:ui-test-manifest")

    // Use to implement wear watch faces
    implementation(libs.watchface)
    // Use to implement wear watchface complications
    implementation (libs.watchface.complications.data.source)
    // (Kotlin-specific extensions)
    implementation (libs.watchface.complications.data.source.ktx)
    // Use to implement a watchface style and complication editor
    implementation(libs.watchface.editor)
}