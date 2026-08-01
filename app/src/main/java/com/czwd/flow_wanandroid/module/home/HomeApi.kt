package com.czwd.flow_wanandroid.module.home

import com.czwd.flow_wanandroid.base.BaseResponse
import retrofit2.http.GET

interface HomeApi {

    @GET("banner/json")
   suspend fun getBanner() : BaseResponse<List<Banner>>
}