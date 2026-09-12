package com.czwd.flow_wanandroid.module.system.repository

import com.czwd.flow_wanandroid.module.system.api.SystemApi
import com.wanandroid.app.network.flowOfApiSimple

class SystemRepository(private val systemApi: SystemApi) {
    /**
     * 获取体系数据
     */
     fun getSystemData() = flowOfApiSimple {
         systemApi.getSystemData()
     }

}