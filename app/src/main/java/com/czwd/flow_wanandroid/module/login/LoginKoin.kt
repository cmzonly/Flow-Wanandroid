package com.czwd.flow_wanandroid.module.login

import com.czwd.flow_wanandroid.module.home.HomeApi
import com.czwd.flow_wanandroid.network.RetrofitClient
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val loginKoin = module {
    single { RetrofitClient.createService<LoginApi>() }

    single { LoginRepository(get()) }

    viewModel { LoginViewModel(get()) }
}