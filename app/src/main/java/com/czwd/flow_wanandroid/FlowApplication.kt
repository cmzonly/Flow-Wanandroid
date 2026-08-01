package com.czwd.flow_wanandroid

import android.app.Application

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