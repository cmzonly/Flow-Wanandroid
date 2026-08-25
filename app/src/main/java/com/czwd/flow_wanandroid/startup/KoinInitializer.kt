package com.czwd.flow_wanandroid.startup

import android.content.Context
import androidx.startup.Initializer
import com.czwd.flow_wanandroid.module.home.homeKoin
import com.czwd.flow_wanandroid.module.login.loginKoin
import com.czwd.flow_wanandroid.module.project.projectKoin
import com.czwd.flow_wanandroid.module.search.koin.searchKoin
import com.czwd.flow_wanandroid.module.system.koin.systemKoin
import com.czwd.flow_wanandroid.module.user.userKoin
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class KoinInitializer : Initializer<Unit> {
    override fun create(context: Context) {
        startKoin {
            androidLogger()
            androidContext(context)
            modules(homeKoin , loginKoin , userKoin , projectKoin , searchKoin , systemKoin)
        }
    }

    override fun dependencies(): List<Class<out Initializer<*>?>?>  =
        emptyList<Class<out Initializer<*>?>>()
}