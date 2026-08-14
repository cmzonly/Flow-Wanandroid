package com.czwd.flow_wanandroid.module.login

import com.czwd.flow_wanandroid.network.NetworkResult
import com.wanandroid.app.network.flowOfApiSimple
import kotlinx.coroutines.flow.MutableStateFlow

class LoginRepository(val loginApi : LoginApi) {


    /**
     * 注册
     */
    fun register(
        username : String,
        password : String,
        repassword : String,
    ) = flowOfApiSimple {
            loginApi.register(username , password , repassword)
        }

    /**
     * 登录
     */
    fun login(
        username : String,
        password : String
    ) = flowOfApiSimple {
        loginApi.login(username ,password )
    }

}