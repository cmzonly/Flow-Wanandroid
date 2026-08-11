package com.czwd.flow_wanandroid.utils

import android.util.Log
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

object GlobalViewModel {
    private var _statusBarHeightFlow = MutableStateFlow(0)
    val statusBarHeightFlow get() = _statusBarHeightFlow.asStateFlow()

    private var _loginFlow = MutableSharedFlow<Unit>()
    val loginFlow get() = _loginFlow.asSharedFlow()

    fun saveStatusBarHeight(statusBarHeight : Int){
        Log.d("ttt", "statusBarHeight:${statusBarHeight} ")
        _statusBarHeightFlow.value = statusBarHeight
    }

    fun notLogin(){
        MainScope().launch {
            _loginFlow.emit(Unit)
        }
    }
}