package com.czwd.flow_wanandroid.module.user

import com.czwd.flow_wanandroid.base.ApiResponse
import com.czwd.flow_wanandroid.module.home.CollectResponse
import retrofit2.http.GET

interface UserApi {

    //退出登录
    @GET("user/logout/json")
    suspend fun loginOut(): ApiResponse<CollectResponse>
}