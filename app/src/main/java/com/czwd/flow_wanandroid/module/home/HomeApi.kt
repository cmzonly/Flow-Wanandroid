package com.czwd.flow_wanandroid.module.home

import com.czwd.flow_wanandroid.base.ApiResponse
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

interface HomeApi {

    @GET("banner/json")
   suspend fun getBanner() : ApiResponse<List<HomeBanner>>


   //首页文章列表
   @GET("article/list/{page}/json")
   suspend fun getArticleList(
      @Path("page") page: Int,
      @Query("page_size") pageSize: Int = 20
   ) : ApiResponse<Article>

   //收藏
   @POST("lg/collect/{id}/json")
   suspend fun collect(@Path("id") id: Int): ApiResponse<CollectResponse>

   //取消收藏
   @POST("lg/uncollect_originId/{id}/json")
   suspend fun unCollect(@Path("id") id: Int): ApiResponse<CollectResponse>
}