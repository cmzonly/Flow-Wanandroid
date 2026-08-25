package com.czwd.flow_wanandroid.module.search.repository

import com.czwd.flow_wanandroid.module.search.api.SearchApi
import com.wanandroid.app.network.flowOfApiSimple

class SearchRepository(private val searchApi: SearchApi)  {
    // 搜索热词
    fun getHotWords() =
        flowOfApiSimple{
            searchApi.getHotWords()
        }

    // 置顶文章列表
    fun getTopArticles() =
        flowOfApiSimple{
            searchApi.getTopArticles()
        }

}