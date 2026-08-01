plugins {
    alias(libs.plugins.android.application)
}

android {
    namespace = "com.czwd.flow_wanandroid"
    compileSdk {
        version = release(36) {
            minorApiLevel = 1
        }
    }

    defaultConfig {
        applicationId = "com.czwd.flow_wanandroid"
        minSdk = 28
        targetSdk = 36
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

        debug {
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

    buildFeatures {
        buildConfig = true
        viewBinding = true
    }
}

dependencies {
    implementation(libs.androidx.activity.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.androidx.constraintlayout)
    implementation(libs.androidx.core.ktx)
    implementation(libs.material)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(libs.androidx.junit)
    //xpopup用来定义弹窗,shapeview用来定义控件圆角形状之类的,注意,使用background属性,弹窗为方形,使用shape_solide为自定义的圆角
    implementation(libs.xpopup)
    implementation(libs.shapeview)
    //今日头条屏幕适配
    implementation(libs.androidautosize)
    //recyclerview适配器
    implementation(libs.baserecyclerviewadapterhelper4)
    //常用工具类
    implementation(libs.utilcodex)
    //retrofit配套Gson
    implementation(libs.retrofit)
    implementation(libs.converter.gson)
    //拦截器
    implementation(libs.logging.interceptor)
    //依赖注入koin
    implementation(libs.koin.core)
    implementation(libs.koin.android)

}