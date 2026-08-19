package com.czwd.flow_wanandroid.module.user

import com.wanandroid.app.network.flowOfApiSimple

class UserRepository(private val userApi: UserApi) {

    fun loginOut() =
        flowOfApiSimple {
            userApi.loginOut()
        }

}