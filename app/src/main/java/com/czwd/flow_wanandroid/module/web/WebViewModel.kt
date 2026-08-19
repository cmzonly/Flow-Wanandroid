package com.czwd.flow_wanandroid.module.web

import com.czwd.flow_wanandroid.base.BaseViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class WebViewModel : BaseViewModel() {
    private var _bundleFlow = MutableStateFlow<String?>(null)
    val bundleFlow get() = _bundleFlow.asStateFlow()

    fun setUrl(url: String?){
        _bundleFlow.value = url
    }
}