package com.czwd.flow_wanandroid.module.project

import com.czwd.flow_wanandroid.network.RetrofitClient
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val projectKoin = module {
    single {
        RetrofitClient.createService<ProjectApi>()
    }

    single {
        ProjectRepository(get())
    }

    viewModel {
        ProjectViewModel(get())
    }
}