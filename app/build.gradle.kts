plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
}

android {
    namespace = "com.example.lokaltask"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.example.lokaltask"
        minSdk = 24
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
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
}

dependencies {

//    implementation("androidx.core:core-ktx:1.10.1")
    implementation("androidx.compose.material3:material3:1.2.0-alpha04")

    implementation("androidx.appcompat:appcompat:1.6.1")
    implementation("com.google.android.material:material:1.9.0")
    implementation("androidx.constraintlayout:constraintlayout:2.1.4")
    implementation("androidx.swiperefreshlayout:swiperefreshlayout:1.1.0")
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")
    implementation("androidx.recyclerview:recyclerview:1.3.1")
    implementation("com.google.dagger:dagger:2.47")

    annotationProcessor("com.google.dagger:dagger-compiler:2.47")
    implementation("com.google.code.gson:gson:2.10.1")

    // Retrofit core library
    implementation("com.squareup.retrofit2:retrofit:2.9.0")
    implementation("com.squareup.okhttp3:logging-interceptor:4.2.1")// replace x.x with the latest version
    implementation("javax.annotation:javax.annotation-api:1.3.2")

//Glide to Render Image using URL
    implementation("com.github.bumptech.glide:glide:4.11.0")
    implementation("com.squareup.picasso:picasso:2.71828")



// Gson converter for Retrofit
    implementation("com.squareup.retrofit2:converter-gson:2.9.0")



}