package com.czwd.flow_wanandroid.module.system.koin

import com.czwd.flow_wanandroid.module.system.api.SystemApi
import com.czwd.flow_wanandroid.module.system.repository.SystemRepository
import com.czwd.flow_wanandroid.module.system.vm.SystemViewModel
import com.czwd.flow_wanandroid.network.RetrofitClient
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val systemKoin = module{
    single { RetrofitClient.createService<SystemApi>() }
    single { SystemRepository(get()) }
    viewModel{ SystemViewModel(get()) }
}