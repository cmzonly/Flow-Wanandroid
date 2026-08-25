package com.czwd.flow_wanandroid.module.search.bean

import com.czwd.flow_wanandroid.db.room.entity.HistoryEntity
import com.czwd.flow_wanandroid.module.home.Article

data class SearchUiState(
    val isLoading : Boolean = false,
    val isShowLoading : Boolean = true,
    val historyList : List<HistoryEntity>?=null,
    val hotList: List<HotResponse>? = null,
    val likeList: List<Article.DataX>? =null,
    val isOver : Boolean = false,
    val error : String = ""
) {
}