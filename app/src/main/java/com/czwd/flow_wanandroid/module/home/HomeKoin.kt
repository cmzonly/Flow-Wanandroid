package com.czwd.flow_wanandroid.module.home

import com.czwd.flow_wanandroid.network.RetrofitClient
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val homeKoin = module{
    //提供HomeApi :通过RetrofitClient创建
    single { RetrofitClient.createService<HomeApi>()}

    //提供HomeRepository:Koin会自动把上面的HomeApi注入到函数构造
    single { HomeRepository(get()) }

    //提供HomeViewModel:Koin会自动把上面的HomeRepository注入到函数构造
    viewModel{ HomeViewModel(get()) }
}