package com.czwd.flow_wanandroid.module.details.koin

import com.czwd.flow_wanandroid.module.details.api.DetailsApi
import com.czwd.flow_wanandroid.module.details.repository.DetailsRepository
import com.czwd.flow_wanandroid.module.details.vm.DetailsViewModel
import com.czwd.flow_wanandroid.network.RetrofitClient
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val detailsKoin = module{
    single {
        RetrofitClient.createService<DetailsApi>()
    }

    single {
        DetailsRepository(get())
    }

    viewModel{
        DetailsViewModel(get())
    }
}