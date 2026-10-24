package com.czwd.flow_wanandroid.module.project

data class ProjectUiState(
    /**是否显示加载框*/
    val isShowLoading: Boolean = true,
    /**当前page*/
    val currentPage: Int = 1,
    /**当前cid*/
    val currentCid : Int = 0,
    /** 是否正在加载中 */
    val isLoading : Boolean = false,
    /** 项目分类列表 */
    val projectTypeList: List<ProjectTypeResponse> = emptyList(),
    /** 单个分类下的列表 */
    val projectList: List<ProjectListResponse.DataX> = emptyList(),
    /** 错误信息 */
    val errorMsg: String? = null,
    /**是否完结*/
    val isOver : Boolean = false,
    /**下拉刷新*/
    val isRefresh : Boolean = false,
    /** 是否还有更多数据 */
    val isArticleOver: Boolean = false,
)
