package com.czwd.flow_wanandroid.base

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

open class BaseViewModel : ViewModel(){

    private val loadedKeys = mutableSetOf<String>()

    protected fun loadOnce(key : String , block: suspend () -> Unit){
        if (loadedKeys.contains(key)) return
        loadedKeys.add(key)
        launchOnViewModelScope { block() }
    }

    fun deleteLoadKey(vararg keys : String){
        keys.forEach {
            loadedKeys.remove(it)
        }
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