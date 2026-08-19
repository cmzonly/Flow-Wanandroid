package com.czwd.flow_wanandroid.base

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

open class BaseViewModel : ViewModel(){

    // ==================== 是否是首次加载 ====================
    var isFirstLoad = true

    // ==================== 协程工具 ====================

    /** 统一异常处理器 */
    protected open val exceptionHandler = CoroutineExceptionHandler { _, throwable ->
        throwable.printStackTrace()
    }

    /** 在主线程启动协程 */
    protected fun launchOnMain(block: suspend CoroutineScope.() -> Unit) {
        viewModelScope.launch(Dispatchers.Main + exceptionHandler, block = block)
    }

    /** 在 IO 线程启动协程 */
    protected fun launchOnIO(block: suspend CoroutineScope.() -> Unit) {
        viewModelScope.launch(Dispatchers.IO + exceptionHandler, block = block)
    }

    /** 自定义调度器启动协程 */
    protected fun launchWith(
        dispatcher: CoroutineDispatcher = Dispatchers.Main,
        block: suspend CoroutineScope.() -> Unit
    ) {
        viewModelScope.launch(dispatcher + exceptionHandler, block = block)
    }

    // ==================== StateFlow 工具 ====================

    /**
     * 创建一个初始化的 MutableStateFlow
     */
    protected fun <T> mutableStateFlow(initial: T): MutableStateFlow<T> =
        MutableStateFlow(initial)

    /**
     * 将 MutableStateFlow 转为对外暴露的 StateFlow
     */
    protected fun <T> MutableStateFlow<T>.asExposedFlow(): StateFlow<T> =
        this.asStateFlow()

    // ==================== 异常处理 ====================

    /**
     * 子类可重写，统一处理业务异常
     */
    protected open fun handleError(code: Int, message: String) {
        // 默认实现：打印日志
        // 子类可重写后跳转到登录页、弹 Toast 等
    }

}