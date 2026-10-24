package com.czwd.flow_wanandroid.module.user

import com.czwd.flow_wanandroid.network.flowOfApiSimple

class UserRepository(private val userApi: UserApi) {

    fun loginOut() =
        flowOfApiSimple {
            userApi.loginOut()
        }

}