package com.czwd.flow_wanandroid.module.project

import com.czwd.flow_wanandroid.network.flowOfApiSimple

class ProjectRepository(val projectApi: ProjectApi) {
    //=====   项目分类列表   =====
    fun getProjectTypeList() =
        flowOfApiSimple {
            projectApi.getProjectTypeList()
        }

    //=====   项目列表数据   =====
    fun getProjectList(page: Int, cid: Int) =
        flowOfApiSimple {
            projectApi.getProjectList(page, cid)
        }
}