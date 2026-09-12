package com.czwd.flow_wanandroid.module.system.api

import com.czwd.flow_wanandroid.base.ApiResponse
import com.czwd.flow_wanandroid.module.home.Article
import com.czwd.flow_wanandroid.module.system.bean.SystemResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface SystemApi {
    /**体系数据*/
    @GET("tree/json")
   suspend fun getSystemData(): ApiResponse<List<SystemResponse>>
}