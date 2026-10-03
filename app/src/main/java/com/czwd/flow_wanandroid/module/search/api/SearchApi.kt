package com.czwd.flow_wanandroid.module.search.api

import com.czwd.flow_wanandroid.base.ApiResponse
import com.czwd.flow_wanandroid.module.home.Article
import com.czwd.flow_wanandroid.module.search.bean.HotResponse
import okhttp3.ResponseBody
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

interface SearchApi {
    // 搜索热词
    @GET("hotkey/json")
    suspend fun getHotWords() : ApiResponse<List<HotResponse>>

    //置顶文章列表
    @GET("article/top/json")
    suspend fun getTopArticles() : ApiResponse<List<Article.DataX>>

    //搜索文章
    @POST("article/query/{page}/json")
    suspend fun getSearchArticles(
        @Path("page") page: Int,
        @Query("k") k: String,
        @Query("page_size") pageSize: Int = 20
    ) : ApiResponse<Article>
}