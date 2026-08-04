package com.czwd.flow_wanandroid.utils

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

object GlobalViewModel {
    private var _statusBarHeightFlow = MutableStateFlow(0)
    val statusBarHeightFlow get() = _statusBarHeightFlow.asStateFlow()

    fun saveStatusBarHeight(statusBarHeight : Int){
        _statusBarHeightFlow.value = statusBarHeight
    }
}