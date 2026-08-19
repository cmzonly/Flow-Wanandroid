package com.czwd.flow_wanandroid.module.home

import com.czwd.flow_wanandroid.network.NetworkResult
import com.wanandroid.app.network.flowOfApi
import com.wanandroid.app.network.flowOfApiSimple
import kotlinx.coroutines.flow.Flow

class HomeRepository(private val homeApi: HomeApi) {

    // ==================== 首页 ====================
    fun getBanner() = flowOfApiSimple { homeApi.getBanner() }

    fun getArticleList(page: Int) =flowOfApiSimple { homeApi.getArticleList(page) }



    // ==================== 收藏 ====================
    fun collect(id : Int) = flowOfApiSimple { homeApi.collect(id) }

    fun unCollect(id : Int) = flowOfApiSimple {homeApi.unCollect(id)}

}