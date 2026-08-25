package com.czwd.flow_wanandroid.module.details.uistate

import com.czwd.flow_wanandroid.module.home.Article

data class DetailsUiState(
    val isLoading : Boolean = false,
    val error : String = "",
    val detailsList : List<Article.DataX>? = null,
    val isShowLoading : Boolean = false,
    val currentPage : Int = 0,
    val isOver: Boolean = false
)
