import org.jetbrains.kotlin.config.KotlinCompilerVersion

plugins {
    id("com.android.application")
    kotlin("android")
    kotlin("android.extensions")
    id("com.google.gms.google-services")
}


android {
    compileSdkVersion(28)
    defaultConfig {
        applicationId = "com.te0tl.themoviesdb"
        minSdkVersion(21)
        targetSdkVersion(28)
        versionCode = 1
        versionName = "1.0"

        multiDexEnabled = true

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"

        testInstrumentationRunnerArguments = mapOf(
            "clearPackageData" to "true"
        )
    }

    buildTypes {
        getByName("release") {
            isMinifyEnabled = false
            proguardFiles(getDefaultProguardFile("proguard-android.txt"), "proguard-rules.pro")
        }
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }

    kotlinOptions {
        jvmTarget = "1.8"
    }

    packagingOptions {
        exclude("META-INF/*")
    }

//    tasks.withType(org.jetbrains.kotlin.gradle.tasks.KotlinCompile).all {
//        kotlinOptions {
//            jvmTarget = "1.8"
//        }
//    }

    tasks.withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile>().all {
        kotlinOptions.jvmTarget = "1.8"
    }

    buildFeatures {
        viewBinding = true
    }

    testOptions {
        animationsDisabled = true
        unitTests.apply {
            isReturnDefaultValues = true
            isIncludeAndroidResources = true
        }
        execution = "ANDROIDX_TEST_ORCHESTRATOR"
    }
}


dependencies {
    val kotlinVersion = KotlinCompilerVersion.VERSION
    val kotlinCoroutinesVersion = "1.3.8"
    val androidxVersion = "1.1.0"
    val androidxLifecycleVersion = "2.2.0"
    val androidxWorkVersion = "2.3.4"
    val androidxFragmentVersion = "1.3.0-alpha05"
    val ankoVersion = "0.10.8"
    val retrofitVersion = "2.6.0"
    val firebaseVersion = "21.2.1"
    val glideVersion = "4.9.0"

    val junitVersion = "4.12"
    val androidxTestVersion = "1.2.0"
    val androidxLifecycleTestVersion = "2.1.0"
    val mockkVersion = "1.10.0"
    val expressoVersion = "3.2.0"
    val androidxFragmentTestVersion = "1.2.0-alpha02"
    val androidxJunitVersion = "1.1.1"
    val androidxTruthVersion = "1.2.0"

    implementation(fileTree(mapOf("dir" to "libs", "include" to listOf("*.jar"))))

    //#####################################################################
    //          Kotlin
    //#####################################################################

    implementation(kotlin("stdlib-jdk8", kotlinVersion))
    implementation("org.jetbrains.kotlin:kotlin-reflect:$kotlinVersion")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:$kotlinCoroutinesVersion")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:$kotlinCoroutinesVersion")

    //#####################################################################
    //          Koin
    //#####################################################################
    val koinVersion = "2.2.0-beta-1"
    implementation("org.koin:koin-android:$koinVersion")
    implementation("org.koin:koin-androidx-scope:$koinVersion")
    implementation("org.koin:koin-androidx-viewmodel:$koinVersion")

    //#####################################################################
    //          Android appcompat and jetpack
    //#####################################################################
    implementation("androidx.multidex:multidex:2.0.1")
    implementation("androidx.appcompat:appcompat:$androidxVersion")
    implementation("androidx.core:core-ktx:$androidxVersion")

    implementation("androidx.lifecycle:lifecycle-extensions:$androidxLifecycleVersion")
    implementation("androidx.lifecycle:lifecycle-viewmodel:$androidxLifecycleVersion")
    implementation("androidx.lifecycle:lifecycle-livedata-ktx:$androidxLifecycleVersion")
    implementation("androidx.lifecycle:lifecycle-common-java8:$androidxLifecycleVersion")

//    implementation ("androidx.work:work-runtime-ktx:$androidxWorkVersion")
    implementation("androidx.fragment:fragment-ktx:$androidxFragmentVersion")

    implementation("androidx.constraintlayout:constraintlayout:1.1.3")
    implementation("androidx.swiperefreshlayout:swiperefreshlayout:1.0.0")
    implementation("androidx.viewpager:viewpager:1.0.0")


    //#####################################################################
    //          Anko
    //#####################################################################
    implementation("org.jetbrains.anko:anko-commons:$ankoVersion")
    implementation("org.jetbrains.anko:anko-design:$ankoVersion")


    //#####################################################################
    //          Firebase
    //#####################################################################
    implementation("com.google.firebase:firebase-firestore-ktx:$firebaseVersion")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-play-services:1.1.1")


    //#####################################################################
    //          Retrofit
    //#####################################################################
    implementation("com.squareup.retrofit2:retrofit:$retrofitVersion")
    implementation("com.squareup.retrofit2:converter-moshi:$retrofitVersion")
    implementation("com.squareup.retrofit2:adapter-rxjava2:$retrofitVersion")
    implementation("com.squareup.okhttp3:logging-interceptor:4.7.2")

    //#####################################################################
    //          Image processing
    //#####################################################################
    implementation("com.github.bumptech.glide:glide:$glideVersion")
    implementation("com.github.bumptech.glide:okhttp3-integration:$glideVersion")
    annotationProcessor("com.github.bumptech.glide:compiler:$glideVersion")


    //#####################################################################
    //          Logger
    //#####################################################################
    implementation("com.jakewharton.timber:timber:4.7.1")
    implementation("com.orhanobut:logger:2.2.0")


    //#####################################################################
    //          Others
    //#####################################################################
    implementation("com.google.android.material:material:1.1.0")
    implementation("com.pierfrancescosoffritti.androidyoutubeplayer:core:10.0.3")


    //#####################################################################
    //          Unit Testing
    //#####################################################################
    testImplementation("junit:junit:$junitVersion")
    testImplementation("androidx.arch.core:core-testing:$androidxLifecycleTestVersion")
    debugImplementation("androidx.fragment:fragment-testing:$androidxFragmentTestVersion") {
        exclude("androidx.test", "core")
    }
    testImplementation("androidx.fragment:fragment-testing:$androidxFragmentTestVersion") {
        exclude("androidx.test", "core")
    }
    testImplementation("androidx.test:runner:$androidxTestVersion")
    testImplementation("androidx.test.ext:junit:$androidxJunitVersion")
    testImplementation("androidx.test.ext:truth:$androidxTruthVersion")
    testImplementation("androidx.test.espresso:espresso-core:$expressoVersion")
    testImplementation("androidx.test.espresso:espresso-intents:$expressoVersion")
    testImplementation("androidx.test.espresso:espresso-contrib:$expressoVersion")
    testImplementation("org.robolectric:robolectric:4.3")

    testImplementation("io.mockk:mockk:$mockkVersion")
    testImplementation("org.jetbrains.kotlinx:kotlinx-coroutines-test:$kotlinCoroutinesVersion")
    testImplementation("org.koin:koin-test:$koinVersion")
    testImplementation("com.squareup.okhttp3:mockwebserver:4.1.1")
    testImplementation("com.agoda.kakao:kakao:2.1.0")



    //#####################################################################
    //          Instrumented Testing
    //#####################################################################
    androidTestImplementation ("androidx.test:runner:$androidxTestVersion")
    androidTestImplementation ("androidx.test.ext:junit:$androidxJunitVersion")
    androidTestImplementation ("androidx.test.ext:truth:$androidxTruthVersion")
    androidTestImplementation ("androidx.test:rules:$androidxTestVersion")
    androidTestImplementation ("androidx.arch.core:core-testing:$androidxLifecycleTestVersion")
    androidTestUtil ("androidx.test:orchestrator:$androidxTestVersion")

    androidTestImplementation ("androidx.test.espresso:espresso-core:$expressoVersion")
    androidTestImplementation ("androidx.test.espresso:espresso-intents:$expressoVersion")
    androidTestImplementation ("androidx.test.espresso:espresso-idling-resource:$expressoVersion")
    androidTestImplementation ("androidx.test.uiautomator:uiautomator:2.2.0")

    androidTestImplementation ("io.mockk:mockk-android:$mockkVersion")
    androidTestImplementation ("org.jetbrains.kotlinx:kotlinx-coroutines-test:$kotlinCoroutinesVersion")
    androidTestImplementation ("org.koin:koin-test:$koinVersion")
}