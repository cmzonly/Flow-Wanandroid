package com.czwd.flow_wanandroid.base

/**
 * 列表加载更多状态
 */
sealed class LoadState {

    /** 空闲（无加载操作） */
    data object Idle : LoadState()

    /** 正在加载 */
    data object Loading : LoadState()

    /** 加载完成 */
    data class NotLoading(val isEnd: Boolean = false) : LoadState()

    /** 加载失败 */
    data class Error(val message: String = "") : LoadState()

    val isLoading: Boolean get() = this is Loading
    val isEnded: Boolean get() = this is NotLoading && isEnd


}