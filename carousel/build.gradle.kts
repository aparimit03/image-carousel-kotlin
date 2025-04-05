plugins {
    id("com.android.library")
    alias(libs.plugins.kotlin.android)
    `maven-publish`
}

group = "com.github.aparimit03"
version = "1.2.0"

afterEvaluate {
    val sourcesJar = tasks.register("androidSourcesJar", Jar::class) {
        archiveClassifier.set("sources")
        from(android.sourceSets["main"].java.srcDirs)
    }

    publishing {
        publications {
            create<MavenPublication>("release") {
                from(components["release"])
                groupId = "com.github.aparimit03"
                artifactId = "carousel"
                version = "1.2.0"

                artifact(sourcesJar.get())
            }
        }
    }

    tasks.named("generateMetadataFileForReleasePublication") {
        dependsOn(sourcesJar)
    }
}

android {
    namespace = "com.example.carousel"
    compileSdk = 35

    defaultConfig {
        minSdk = 22
        targetSdk = 34
        multiDexEnabled = true
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
    kotlinOptions {
        jvmTarget = "11"
    }
}

dependencies {

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
//    Dependencies for the library
    api(libs.picasso)
    implementation(libs.glide)
    implementation(libs.androidx.multidex)
}