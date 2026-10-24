package com.czwd.flow_wanandroid.base

/**
 * 通用异步状态密封类
 *
 * 用于描述一次异步操作的完整生命周期：
 * 未初始化 → 加载中 → 成功 / 失败
 */
sealed class AsyncState<out T> {

    /** 尚未开始 */
    data object Uninitialized : AsyncState<Nothing>()

    /** 加载中 */
    data object Loading : AsyncState<Nothing>()

    /** 操作成功（包含数据） */
    data class Success<T>(val data: T) : AsyncState<T>()

    /** 操作失败 */
    data class Error(
        val code: Int = -1,
        val message: String = "Unknown error"
    ) : AsyncState<Nothing>()

    // ===== 便捷属性 =====

    val isLoading: Boolean get() = this is Loading
    val isSuccess: Boolean get() = this is Success
    val isError: Boolean get() = this is Error

    /** 安全获取数据 */
    fun getOrNull(): T? = when (this) {
        is Success -> data
        else -> null
    }
}