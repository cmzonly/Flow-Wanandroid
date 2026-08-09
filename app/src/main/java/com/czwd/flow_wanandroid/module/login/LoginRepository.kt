package com.czwd.flow_wanandroid.module.login

import com.czwd.flow_wanandroid.base.BaseRepository
import com.czwd.flow_wanandroid.network.NetworkResult
import kotlinx.coroutines.flow.MutableStateFlow

class LoginRepository(val loginApi : LoginApi) : BaseRepository() {


    /**
     * 注册
     */
    fun register(
        username : String,
        password : String,
        repassword : String,
    ) = safeApiCall {
            loginApi.register(username , password , repassword)
        }

    /**
     * 登录
     */
    fun login(
        username : String,
        password : String
    ) = safeApiCall {
        loginApi.login(username ,password )
    }

}