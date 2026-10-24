package com.czwd.flow_wanandroid.module.details.uistate

import com.czwd.flow_wanandroid.module.home.Article
import com.czwd.flow_wanandroid.module.system.bean.SystemResponse

data class DetailsUiState(
    val currentBundleData: SystemResponse.Children? = null,
    val isLoading : Boolean = false,
    val error : String? = null,
    val detailsList : List<Article.DataX> = emptyList(),
    val isShowLoading : Boolean = true,
    val currentPage : Int = 0,
    val isOver: Boolean = false,
    val isRefreshing: Boolean = false
)
