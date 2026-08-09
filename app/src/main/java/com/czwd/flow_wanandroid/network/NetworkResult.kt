package com.czwd.flow_wanandroid.network

sealed class NetworkResult<out T> {

    data object Idle : NetworkResult<Nothing>()
    data class Success<T>(var data : T) : NetworkResult<T>()
    data object Loading : NetworkResult<Nothing>()

    data class Error(val code: Int? = null, val message: String? = null) : NetworkResult<Nothing>()

    val isSuccess: Boolean
        get() = this is Success

    val isError: Boolean
        get() = this is Error

    val isLoading: Boolean
        get() = this is Loading

    fun getOrNull(): T? = when (this) {
        is Success -> data
        else -> null
    }

    fun errorMessage(): String? = when (this) {
        is Error -> message
        else -> null
    }
}