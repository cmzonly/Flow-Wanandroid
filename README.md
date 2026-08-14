//以下仅供自己提供步骤
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

# viewmodel的生命周期,当activity中按返回键后对应的viewmodel是否还在
    情况一：按返回键，正常退出
    当用户按返回键，Activity 会正常走完 onDestroy() 流程并被系统回收。

    ViewModel 还“活着”吗？
    技术上，onDestroy() 执行时 ViewModel 还存在。但紧接着，系统会判断该 Activity 不是因配置更改（如旋转屏幕）而重建，于是会自动调用 ViewModelStore 的 clear() 方法。

    最终结果：被清除
    它会立即回调 ViewModel 的 onCleared() 方法。在这一刻之后，ViewModel 就不再存续了，持有的资源也会被释放。

    情况二：配置更改，系统重建
    为了对比，像旋转屏幕这种配置更改也走 onDestroy，但系统知道它马上要被重建。

    ViewModel 还“活着”吗？
    这时 ViewModel 实例会被保留下来，并传递给新的 Activity 实例，数据完好无损。你不需要在 onCleared() 里做清理。

    部分源码:
    if (event == Lifecycle.Event.ON_DESTROY) {
                // 1. 判断是不是配置更改
                if (!isChangingConfigurations()) {
                    // 2. 不是配置更改？清空 ViewModelStore
                    getViewModelStore().clear();
                }
    }

# Android 资源限定符的顺序规则
    drawable-{locale}-{night}-{density}-{touchscreen}...
    因此,暗色模式对应的drawable为drawable-night-xxhdpi

# Android沉浸式状态栏使用
    1.在Activity中调用setContentView(binding.root)之前调用enableEdgeToEdge(),该方法会填充全屏,但不包括 padding,在屏幕左上角
      写的view会在状态栏左上角,怎么处理?使用系统自带方法设置padding
            ViewCompat.setOnApplyWindowInsetsListener(binding.root) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            GlobalViewModel.saveStatusBarHeight(systemBars.top)    //获取系统状态栏高度
            v.setPadding(systemBars.left, 0, systemBars.right, 0)   //top设为0,保持沉浸式
            insets
        }
    2.在basefragment的onstart方法中获取globalViewmodel保存的值,设置给每个fragment的根view
        binding.root.apply { 
            GlobalViewModel.statusBarHeightFlow.value.let { 
                if (it > 0){
                    setPadding(paddingLeft, GlobalViewModel.statusBarHeightFlow.value, paddingRight, paddingBottom)
                }
            }
           //暂时有问题,没发现原因
        }

# basequickadapter v4.4.1使用
    1.使用多布局
    2.加载更多



# shapeview地址
[CLICK HERE] https://github.com/getActivity/ShapeView

# basequickadapter 地址
[CLICK HERE] https://github.com/CymChad/BaseRecyclerViewAdapterHelper/wiki

# xpopup
[click here] 

# 今日头条屏幕适配
[click here] https://github.com/JessYanCoding/AndroidAutoSize/issues/13
      