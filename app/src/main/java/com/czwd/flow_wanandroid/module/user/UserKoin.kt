package com.czwd.flow_wanandroid.module.user

import com.czwd.flow_wanandroid.network.RetrofitClient
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val userKoin = module {
    single { RetrofitClient.createService<UserApi>() }

    single { UserRepository(get()) }

    viewModel { UserViewModel(get()) }
}