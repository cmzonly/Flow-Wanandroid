# Jetpack App start库使用
    1.作用:它让你在 Application.onCreate() 之前就完成初始化工作（底层通过 ContentProvider 实现）
    好处是:
        初始化更早、更可靠
        可以控制多个初始化器的执行顺序
        代码更模块化，不用全塞在 Application 里
    2.使用方法
        1>添加依赖: implementation("androidx.startup:startup-runtime:1.2.0")
        2>创建一个类实现Initializer<Unit>接口,重写create()和dependencies()
           oncreate()方法里写初始化代码,dependencies()方法里控制执行顺序
            例如:AutoSizeInitializer 需要在 Koin 之后执行
                // 声明依赖 KoinInitializer → App Startup 保证 KoinInitializer 先执行
                override fun dependencies() = listOf(KoinInitializer::class.java)
        3>在 AndroidManifest.xml 中注册
           <provider
            android:name="androidx.startup.InitializationProvider"
            android:authorities="${applicationId}.androidx-startup"
            android:exported="false"
            tools:node="merge">
            //以上为固定写法,以下为多个实现Initializer接口的自定义实现类
            <meta-data
                android:name="com.czwd.flow_wanandroid.KoinInitializer"
                android:value="androidx.startup" />
            ....
        </provider>