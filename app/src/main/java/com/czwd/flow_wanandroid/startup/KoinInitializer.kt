package com.czwd.flow_wanandroid.startup

import android.content.Context
import androidx.startup.Initializer
import com.czwd.flow_wanandroid.module.home.homeKoin
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class KoinInitializer : Initializer<Unit> {
    override fun create(context: Context) {
        startKoin {
            androidLogger()
            androidContext(context)
            modules(homeKoin)
        }
    }

    override fun dependencies(): List<Class<out Initializer<*>?>?>  =
        emptyList<Class<out Initializer<*>?>>()
}