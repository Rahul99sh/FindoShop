plugins {
    id("com.android.application")
}

android {
    namespace = "com.pro.findoshop"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.pro.findoshop"
        minSdk = 24
        targetSdk = 34
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
    buildFeatures{
        dataBinding = true
        viewBinding = true
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
}

dependencies {

    implementation("androidx.appcompat:appcompat:1.6.1")
    implementation("com.google.android.material:material:1.10.0")
    implementation("androidx.constraintlayout:constraintlayout:2.1.4")
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")

    //splash anim
    implementation ("com.github.sam43:AnimateViewLibrary:1.0.1")
    //Circular Image View
    implementation ("de.hdodenhof:circleimageview:3.1.0")

    //google maps
//    implementation ("com.google.maps.android:android-maps-utils:2.3.0")
    // dependency for loading image from url
    implementation ("com.github.bumptech.glide:glide:4.15.1")

    //auto resizer
    implementation ("com.intuit.sdp:sdp-android:1.1.0")
}