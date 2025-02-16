plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.jetbrains.kotlin.android)
    id("com.google.gms.google-services")
    id("kotlin-kapt")
    id("kotlin-parcelize")
    id("com.google.dagger.hilt.android")
}

android {
    namespace = "com.example.loja"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.example.loja"
        minSdk = 34
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
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
    buildFeatures {
        viewBinding = true
    }
}

dependencies {

    //Dependencias Hilt
    kapt(libs.hilt.android.compiler)
    implementation("com.google.dagger:hilt-android:2.48")

    //Kotlin KTX
    implementation("androidx.fragment:fragment-ktx:1.8.5")



    //Arquitetura
    val lifecycle_version = "2.8.2"
    implementation("androidx.lifecycle:lifecycle-viewmodel-ktx:$lifecycle_version")
    implementation("androidx.lifecycle:lifecycle-livedata-ktx:$lifecycle_version")
    implementation("androidx.lifecycle:lifecycle-runtime-ktx:$lifecycle_version")

    //Dependencias do Firebase
    implementation(platform("com.google.firebase:firebase-bom:33.7.0"))
    implementation("com.google.firebase:firebase-analytics")
    implementation("com.google.firebase:firebase-auth")
    implementation(libs.firebase.firestore)
    implementation(libs.firebase.storage)

    //Dependencia mascara
    implementation("io.github.vicmikhailau:MaskedEditText:5.0.2")

    //dependencias de validacao
    val ev_version = "1.0.4"
    implementation("com.wajahatkarim:easyvalidation-core:$ev_version")
    implementation("com.wajahatkarim:easyvalidation-toast:$ev_version")

    //Picasso
    implementation("com.squareup.picasso:picasso:2.8")

    // lib permissoes
    implementation("com.guolindev.permissionx:permissionx:1.8.1")

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation(libs.androidx.activity)
    implementation(libs.androidx.constraintlayout)
    implementation(project(":core"))
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
}
kapt {
    correctErrorTypes = true
}