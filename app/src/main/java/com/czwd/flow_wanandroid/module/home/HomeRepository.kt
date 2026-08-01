package com.czwd.flow_wanandroid.module.home

import com.czwd.flow_wanandroid.base.BaseRepository
import com.czwd.flow_wanandroid.network.RetrofitClient

class HomeRepository() : BaseRepository() {

    fun getBanner() =
        safeApiCall {
            RetrofitClient.createService<HomeApi>().getBanner()
        }

}