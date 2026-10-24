package com.czwd.flow_wanandroid.utils

import com.czwd.flow_wanandroid.network.NetworkResult
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flatMapConcat
import kotlinx.coroutines.flow.flowOf

inline fun <T, R> Flow<NetworkResult<T>>.flatMapSuccess(
    crossinline transform: suspend (T) -> Flow<NetworkResult<R>>
): Flow<NetworkResult<R>> =
    flatMapConcat { result ->
        when (result) {
            is NetworkResult.Success -> transform(result.data)
            is NetworkResult.Error -> flowOf(NetworkResult.Error(result.code, result.message))
            is NetworkResult.Loading -> flowOf(NetworkResult.Loading)
            else -> {flowOf(NetworkResult.Idle)}
        }
    }
