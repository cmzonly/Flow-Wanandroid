package com.czwd.flow_wanandroid.network

sealed class NetworkResult<out T> {

    /** 初始空闲状态 */
    data object Idle : NetworkResult<Nothing>()

    /** 加载中 */
    data object Loading : NetworkResult<Nothing>()

    /** 成功并返回数据 */
    data class Success<T>(val data: T) : NetworkResult<T>()

    /** 失败并携带错误信息 */
    data class Error(
        val code: Int = -1,
        val message: String = "Unknown error"
    ) : NetworkResult<Nothing>()

    // ===== 便捷属性 =====

    val isSuccess: Boolean get() = this is Success
    val isError: Boolean get() = this is Error
    val isLoading: Boolean get() = this is Loading

    /** 安全获取数据，失败返回 null */
    fun getOrNull(): T? = when (this) {
        is Success -> data
        else -> null
    }

    /** 获取数据或抛出异常 */
    fun getOrThrow(): T = when (this) {
        is Success -> data
        is Error -> throw IllegalStateException("NetworkResult.Error: code=$code, msg=$message")
        is Loading -> throw IllegalStateException("NetworkResult is still Loading")
        is Idle -> throw IllegalStateException("NetworkResult is Idle")
    }
}