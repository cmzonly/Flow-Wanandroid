package com.czwd.flow_wanandroid.module.home

import com.czwd.flow_wanandroid.base.BaseResponse
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface HomeApi {

    @GET("banner/json")
   suspend fun getBanner() : BaseResponse<List<HomeBanner>>


   //首页文章列表
   @GET("article/list/{page}/json")
   suspend fun getArticleList(@Path("page") page: Int) : BaseResponse<Article>

   //收藏
   @POST("lg/collect/{id}/json")
   suspend fun cmzCollect(@Path("id") id: Int): BaseResponse<CollectResponse>
}