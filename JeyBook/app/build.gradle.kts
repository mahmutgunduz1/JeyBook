plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.jetbrains.kotlin.android)
    id("com.google.gms.google-services")
}
android {
    namespace = "com.mahmutgunduz.jeybook"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.mahmutgunduz.jeybook.View"
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
    compileOptions {

        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }

    buildFeatures {
        viewBinding = true
        dataBinding = true
    }
}
val retrofitVersion = "2.9.0"
val rxJavaVersion = "2.2.6"
val nav_version = "2.7.7"
val lottieVersion = "3.4.0"
dependencies {

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation(libs.androidx.activity)
    implementation(libs.androidx.constraintlayout)
    implementation(libs.androidx.navigation.fragment.ktx)
    implementation(libs.androidx.navigation.ui.ktx)

    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)

    // Retrofit
    implementation("com.squareup.retrofit2:retrofit:$retrofitVersion")
    implementation("com.squareup.retrofit2:converter-gson:$retrofitVersion")
    implementation("com.squareup.retrofit2:adapter-rxjava2:$retrofitVersion")

    // RxJava
    implementation("io.reactivex.rxjava2:rxjava:$rxJavaVersion")
    implementation("io.reactivex.rxjava2:rxandroid:2.1.1")

    // RecyclerView
    implementation("androidx.recyclerview:recyclerview:1.1.0")


    //picasso
    implementation("com.squareup.picasso:picasso:2.71828")

    implementation("com.github.bumptech.glide:glide:4.12.0")

    val nav_version = "2.7.7"

        //viewpager slyat
    implementation("androidx.viewpager2:viewpager2:1.1.0")

    implementation("me.relex:circleindicator:2.1.6")

    implementation( "com.google.android.material:material:1.8.0")


    implementation("androidx.navigation:navigation-fragment:$nav_version")
    implementation("androidx.navigation:navigation-ui:$nav_version")

    //firebase
    implementation(platform("com.google.firebase:firebase-bom:33.1.2"))

    implementation("com.google.firebase:firebase-analytics")


    implementation("com.google.firebase:firebase-analytics")
    implementation("com.google.firebase:firebase-auth")
    implementation("com.google.firebase:firebase-firestore")
    implementation("com.google.firebase:firebase-storage")

    //lottiee kanksss

    implementation ("com.airbnb.android:lottie:$lottieVersion")
    implementation ("de.hdodenhof:circleimageview:3.1.0")
}