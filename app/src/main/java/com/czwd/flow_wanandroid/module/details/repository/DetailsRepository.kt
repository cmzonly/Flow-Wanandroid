package com.czwd.flow_wanandroid.module.details.repository

import com.czwd.flow_wanandroid.module.details.api.DetailsApi
import com.wanandroid.app.network.flowOfApiSimple

class DetailsRepository(private val detailsApi : DetailsApi) {

    /*==================获取体系下详情列表===================*/
    fun getDetailsList(page : Int, id: Int)
    = flowOfApiSimple {
        detailsApi.getDetailsList(page, id)
    }
}