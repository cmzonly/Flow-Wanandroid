package com.czwd.flow_wanandroid.module.search.api

import com.czwd.flow_wanandroid.base.ApiResponse
import com.czwd.flow_wanandroid.module.home.Article
import com.czwd.flow_wanandroid.module.search.bean.HotResponse
import retrofit2.http.GET

interface SearchApi {
    // 搜索热词
    @GET("hotkey/json")
    suspend fun getHotWords() : ApiResponse<List<HotResponse>>

    //置顶文章列表
    @GET("article/top/json")
    suspend fun getTopArticles() : ApiResponse<List<Article.DataX>>
}