plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.compose)

}

android {
    namespace = "org.unizd.rma.brkic"
    compileSdk {
        version = release(37)
    }

    defaultConfig {
        applicationId = "org.unizd.rma.brkic"
        minSdk = 24
        targetSdk = 37
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            optimization {
                enable = false
            }
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    buildFeatures {
        compose = true

    }
}

dependencies {

    implementation(libs.androidx.room3.common)
    val room_version = "2.8.4"
    val nav_version = "2.7.0"
    val compose_ui_version = "1.5.3"
    val lifecycle_version = "2.10.0"




    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.activity.compose)
    implementation(libs.androidx.compose.material3)
    implementation(libs.androidx.compose.ui)
    implementation(libs.androidx.compose.ui.graphics)
    implementation(libs.androidx.compose.ui.tooling.preview)
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    testImplementation(libs.junit)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.compose.ui.test.junit4)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(libs.androidx.junit)
    debugImplementation(libs.androidx.compose.ui.test.manifest)
    debugImplementation(libs.androidx.compose.ui.tooling)





    // Core
    implementation ("androidx.core:core-ktx:1.17.0")
    implementation ("androidx.appcompat:appcompat:1.6.1")
    implementation ("androidx.lifecycle:lifecycle-runtime-ktx:$lifecycle_version")
    implementation ("androidx.lifecycle:lifecycle-viewmodel-compose:$lifecycle_version")

    // Compose
    implementation ("androidx.compose.ui:ui:$compose_ui_version")
    implementation ("androidx.compose.ui:ui-graphics:$compose_ui_version")
    implementation ("androidx.compose.ui:ui-tooling-preview:$compose_ui_version")
    implementation ("androidx.compose.material3:material3:1.3.0")
    implementation ("androidx.compose.material:material-icons-extended:$compose_ui_version")
    implementation ("androidx.activity:activity-compose:1.8.0")
    implementation ("androidx.compose.foundation:foundation:1.6.0")
    implementation ("androidx.navigation:navigation-compose:$nav_version")

    // Room DB

    implementation ("androidx.room:room-runtime:$room_version")
    implementation ("androidx.room:room-ktx:$room_version")


    // Hilt/Dagger DI
    implementation ("com.google.dagger:hilt-android:2.57.2")

    implementation ("androidx.hilt:hilt-navigation-compose:1.3.0")

    // Camera
    implementation ("androidx.camera:camera-core:1.5.1")
    implementation ("androidx.camera:camera-camera2:1.5.1")
    implementation ("androidx.camera:camera-lifecycle:1.5.1")
    implementation ("androidx.camera:camera-view:1.5.1")
    implementation ("androidx.camera:camera-extensions:1.5.1")

    // Permissions
    implementation ("com.google.accompanist:accompanist-permissions:0.37.3")

    // Coil images
    implementation ("io.coil-kt:coil-compose:2.7.0")

    // Unit testing
    testImplementation ("junit:junit:4.13.2")
    androidTestImplementation ("androidx.test.ext:junit:1.3.0")
    androidTestImplementation ("androidx.test.espresso:espresso-core:3.7.0")
    androidTestImplementation ("androidx.compose.ui:ui-test-junit4:$compose_ui_version")
    debugImplementation ("androidx.compose.ui:ui-tooling:$compose_ui_version")
    debugImplementation ("androidx.compose.ui:ui-test-manifest:$compose_ui_version")

}