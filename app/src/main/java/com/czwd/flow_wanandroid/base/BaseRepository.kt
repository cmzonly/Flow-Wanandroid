package com.czwd.flow_wanandroid.base

import com.czwd.flow_wanandroid.network.NetworkResult
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

open class BaseRepository {
    /**
     * safeApiCall参数必须添加关键字suspend变为挂起函数
     * 因为flow { } 块内部确实是协程作用域，但这个作用域不会自动传递给普通的 lambda 参数
     */
    fun <T> safeApiCall(
        apiCall:suspend () -> BaseResponse<T>
    ): Flow<NetworkResult<T>> = flow {
        emit(NetworkResult.Loading)
        try {
            val response = apiCall()
            when{
                response.isSuccess -> emit(NetworkResult.Success(response.data))
                else -> emit(NetworkResult.Error(response.errorCode, response.errorMsg))
            }
        } catch (e: Exception) {
            emit(NetworkResult.Error(message = e.message ?: "网络请求失败"))
        }
    }.flowOn(Dispatchers.IO)
}