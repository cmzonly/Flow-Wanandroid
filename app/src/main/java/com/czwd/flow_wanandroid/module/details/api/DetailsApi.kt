package com.czwd.flow_wanandroid.module.details.api

import com.czwd.flow_wanandroid.base.ApiResponse
import com.czwd.flow_wanandroid.module.home.Article
import okhttp3.ResponseBody
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface DetailsApi {
    /*==================获取体系下详情列表===================*/
    @GET("article/list/{page}/json")
    suspend fun getDetailsList(
        @Path("page") page : Int ,
        @Query("cid") id: Int,
        @Query("page_size") pageSize: Int = 20
    ): ApiResponse<Article>
}