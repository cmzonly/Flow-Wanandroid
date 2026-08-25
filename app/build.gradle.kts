import java.util.Properties

plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.ksp)
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

        ndk {
            abiFilters.addAll(listOf("armeabi","armeabi-v7a"))
        }


        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    signingConfigs {
        create("release") {
            val properties = rootProject.file("local.properties").let { file ->
                Properties().apply {
                    load(file.inputStream())
                }
            }
            storeFile = file(properties["KEY_PATH"] as String)
            storePassword = properties["KEY_PASSWORD"] as String
            keyAlias = properties["KEY_ALIAS"] as String
            keyPassword = properties["ALIAS_PASSWORD"] as String
        }
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
            signingConfig = signingConfigs.getByName("release")
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
    /**
     * App Startup :它让你在 Application.onCreate() 之前就完成初始化工作
     * 初始化更早、更可靠
     * 可以控制多个初始化器的执行顺序
     * 代码更模块化，不用全塞在 Application 里
     */
    implementation(libs.androidx.app.startup)
    //Navigation导航
    implementation(libs.androidx.navigation.fragment)
    implementation(libs.androidx.navigation.ui)
    //banner轮播库
    implementation(libs.banner)
    //图片加载库
    implementation(libs.glide)
    //DataStore持久化
    implementation(libs.androidx.datastore.preferences)
    //room
    implementation(libs.androidx.room.runtime)
    implementation(libs.androidx.room.ktx)
    ksp(libs.androidx.room.compiler)
    /**带进度条webview*/
    implementation(libs.agentweb.core)
    /**最新版recyclerview,可设置ConcatAdapter*/
    implementation(libs.androidx.recyclerview)
    /**下拉刷新库*/
    implementation(libs.androidx.swiperefreshlayout)
    /**Android-SpinKit*/
    implementation(libs.android.spinkit)
    /**FlexBox*/
    implementation(libs.flexbox)


}