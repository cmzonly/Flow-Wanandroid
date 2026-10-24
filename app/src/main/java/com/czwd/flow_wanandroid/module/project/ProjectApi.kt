package com.czwd.flow_wanandroid.module.project

import com.czwd.flow_wanandroid.base.ApiResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ProjectApi {
    /**
     * 项目分类列表
     */
    @GET("project/tree/json")
    suspend fun getProjectTypeList(): ApiResponse<List<ProjectTypeResponse>>

    /**
     * 项目列表数据
     */
    @GET("project/list/{page}/json")
    suspend fun getProjectList(
        @Path("page") page: Int,
        @Query("cid") cid: Int
    ) : ApiResponse<ProjectListResponse>
}