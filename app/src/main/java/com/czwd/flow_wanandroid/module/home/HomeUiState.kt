package com.czwd.flow_wanandroid.module.home

import com.czwd.flow_wanandroid.base.AsyncState

/**
 * 首页 UI 状态（单一数据源）
 *
 * Fragment 只观察这一个 StateFlow，
 * 根据里面的字段决定如何渲染界面。
 */
data class HomeUiState(
    /**是否显示加载框*/
    val isShowLoading: Boolean = true,
    /** 是否正在加载 */
    val isLoading: Boolean = false,

    /**是否下拉刷新*/
    val isRefreshing: Boolean = false,


    /** Banner 数据 */
    val banner: List<HomeBanner> = emptyList(),

    /** 文章列表 */
    val articleList: List<Article.DataX> = emptyList(),

    /** 是否还有更多数据 */
    val isArticleOver: Boolean = false,

    /** 当前页码 */
    val currentPage: Int = 0,

    /** 收藏操作状态 */
    val collectState: AsyncState<Boolean> = AsyncState.Uninitialized,

    /** 错误信息 */
    val errorMsg: String? = null
)
