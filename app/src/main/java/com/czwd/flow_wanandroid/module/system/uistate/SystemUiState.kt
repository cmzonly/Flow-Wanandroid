package com.czwd.flow_wanandroid.module.system.uistate

import com.czwd.flow_wanandroid.module.system.bean.SystemResponse

data class SystemUiState(
    val isShowLoading : Boolean = false,
    val isLoading : Boolean = false,
    val error : String = "",
    val systemList: List<SystemResponse> = emptyList()
)
