package com.czwd.flow_wanandroid.base

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

open class BaseViewModel : ViewModel(){

    private val loadedKeys = mutableSetOf<String>()

    /**
     * 自动请求
     */
    protected fun requestOfAuto(key : String, block: suspend () -> Unit){
        if (loadedKeys.contains(key))return
        else loadedKeys.add(key)
        launchOnViewModelScope { block() }
    }

    protected fun requestOfManual(block: suspend () -> Unit){
        launchOnViewModelScope { block() }
    }

    /**
     * 重置指定 key，使下次 requestOfAuto 可以重新执行
     * 用于下拉刷新等场景：先 reset，再 requestOfAuto
     */
    protected fun resetKey(key: String) {
        loadedKeys.remove(key)
    }

    /**
     * 启动协程的扩展函数
     */
    protected fun launchOnViewModelScope(block: suspend () -> Unit) {
        viewModelScope.launch {
            try {
                block()
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

}