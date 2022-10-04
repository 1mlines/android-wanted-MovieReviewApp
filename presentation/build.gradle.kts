plugins {
    id("com.android.library")
    id("org.jetbrains.kotlin.android")
    id("kotlin-kapt")
    id("dagger.hilt.android.plugin")
    id("androidx.navigation.safeargs")
}

android {
    compileSdk = Versions.COMPILE_SDK

    defaultConfig {
        minSdk = Versions.MIN_SDK
        targetSdk = Versions.TARGET_SDK

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
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
        dataBinding = true
    }
}

dependencies {
    implementation(project(":domain"))

    implementation(Ktx.CORE)
    implementation(AndroidX.APP_COMPAT)
    implementation(Google.MATERIAL)
    testImplementation(Test.JUNIT)
    androidTestImplementation(AndroidTest.EXT_JUNIT)
    androidTestImplementation(AndroidTest.ESPRESSO_CORE)

    //ktx
    implementation(Ktx.ACTIVITY)
    implementation(Ktx.FRAGMENT)

    //navigation
    implementation(Navigation.NAVIGATION_UI)
    implementation(Navigation.NAVIGATION_FRAGMENT)
    implementation(Navigation.NAVIGATION_DYNAMIC_FEATURES_FRAGMENT)

    //paging
    implementation(Paging3.PAGING_NO_ANDROID)

    //lifecycle aware component
    implementation(Lifecycle.LIFECYCLE)
    implementation(Lifecycle.LIFECYCLE_LIVEDATA_KTX)
    implementation(Lifecycle.LIFECYCLE_VIEWMODEL_KTX)
    //timber
    implementation(Timber.TIMBER)

    //hilt
    implementation(Hilt.HILT_ANDROID)
    kapt(Hilt.HILT_ANDROID_COMPILER)

    //Firebase
    implementation(platform(Firebase.FIREBASE_BOM))
    implementation(Firebase.FIREBASE_DATABASE_KTX)

    //Glide
    implementation(Glide.GLIDE)
    annotationProcessor(Glide.GLIDE_COMPILER)
}