package com.czwd.flow_wanandroid.module.search.koin

import com.czwd.flow_wanandroid.module.search.api.SearchApi
import com.czwd.flow_wanandroid.module.search.repository.SearchRepository
import com.czwd.flow_wanandroid.module.search.vm.SearchViewModel
import com.czwd.flow_wanandroid.network.RetrofitClient
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val searchKoin = module{
    single {
        RetrofitClient.createService<SearchApi>()
    }

    single {
        SearchRepository(get())
    }

    viewModel {
        SearchViewModel(get())
    }
}