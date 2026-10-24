package com.czwd.flow_wanandroid.module.search.bean

import com.czwd.flow_wanandroid.db.room.entity.HistoryEntity
import com.czwd.flow_wanandroid.module.home.Article

data class SearchUiState(
    /**搜索内容*/
    val query : String = "",
    /**是否加载中*/
    val isLoading : Boolean = false,
    /**是否显示加载中*/
    val isShowLoading : Boolean = true,
    /**历史记录列表*/
    val historyList : List<HistoryEntity>?=null,
    /**热搜列表*/
    val hotList: List<HotResponse> = emptyList(),
    /**搜索结果列表*/
    val searchList: List<Article.DataX> = emptyList(),
    /**置顶列表*/
    val likeList: List<Article.DataX> =emptyList(),
    /**是否加载完成*/
    val isOver : Boolean = false,
    /**错误信息*/
    val error : String = "",
    /**当前页码*/
    val currentPage : Int = 0
) {
}