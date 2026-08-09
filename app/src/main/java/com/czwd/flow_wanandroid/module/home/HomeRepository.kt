package com.czwd.flow_wanandroid.module.home

import com.czwd.flow_wanandroid.base.BaseRepository

class HomeRepository(private val homeApi: HomeApi) : BaseRepository() {

    fun getBanner() =
        safeApiCall {
            homeApi.getBanner()
        }

    fun getArticleList(pageNum: Int) =
        safeApiCall {
            homeApi.getArticleList(pageNum)
        }


    /**
     * 收藏
     */
    fun cmzCollect(cmzId : Int) =
        safeApiCall {
            homeApi.cmzCollect(cmzId)
        }




}