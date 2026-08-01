package com.czwd.flow_wanandroid

import android.app.Application
import com.czwd.flow_wanandroid.module.home.homeKoin
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class FlowApplication : Application() {

    companion object {
        lateinit var context: FlowApplication
            private set
    }

    override fun onCreate() {
        super.onCreate()
        context = this
    }
}