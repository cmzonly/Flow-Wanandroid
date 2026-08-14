package com.czwd.flow_wanandroid.module.login

import com.czwd.flow_wanandroid.base.ApiResponse
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

interface LoginApi {

    /**
     * 注册
     */
    @POST("user/register")
    @FormUrlEncoded
    suspend fun register(
        @Field("username") username: String,
        @Field("password") password: String,
        @Field("repassword") repassword: String
    ): ApiResponse<RegisterResponse>

    /**
     * 登录
     */
    @POST("user/login")
    @FormUrlEncoded
    suspend fun login(
        @Field("username") username : String,
        @Field("password") password : String
    ) : ApiResponse<LoginResponse>
}