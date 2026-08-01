package com.czwd.flow_wanandroid.base

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.czwd.flow_wanandroid.network.NetworkResult
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch

open class BaseViewModel : ViewModel(){
    /**
     * 在viewModelScope中安全地收集Flow
     */
    protected fun <T> collectFlow(
        flow: Flow<T>,
        onSuccess: (T) -> Unit,
        onError: ((Throwable) -> Unit)? = null
    ) {
        viewModelScope.launch {
            flow.catch { e ->
                onError?.invoke(e)
            }.collect { data ->
                onSuccess(data)
            }
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

    protected fun <T> collectNetworkResult(
        flow: Flow<NetworkResult<T>>,
        onStateChange: (NetworkResult<T>) -> Unit
    ) {
        viewModelScope.launch {
            flow.collect { result ->
                if (result is NetworkResult.Error && result.code == -1001) {
                    return@collect
                }
                onStateChange(result)
            }
        }
    }
}