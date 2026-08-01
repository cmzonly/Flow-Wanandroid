package com.czwd.flow_wanandroid.module.home

import com.czwd.flow_wanandroid.base.BaseRepository
import com.czwd.flow_wanandroid.network.RetrofitClient

class HomeRepository(private val homeApi: HomeApi) : BaseRepository() {

    fun getBanner() =
        safeApiCall {
            homeApi.getBanner()
        }

}