package com.wanandroid.app.network

import android.util.Log
import com.czwd.flow_wanandroid.FlowApplication
import com.czwd.flow_wanandroid.R
import com.czwd.flow_wanandroid.base.ApiResponse
import com.czwd.flow_wanandroid.network.NetworkResult
import com.czwd.flow_wanandroid.network.RetrofitClient
import com.czwd.flow_wanandroid.utils.GlobalViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.Response

/**
 * 将 Retrofit 的 suspend 调用包装为 NetworkResult Flow
 *
 * 使用方式：
 * ```kotlin
 * fun getBanner(): Flow<NetworkResult<List<Banner>>> = flowOfApi {
 *     apiService.getBanner()
 * }
 * ```
 */
inline fun <reified T> flowOfApi(
    crossinline apiCall: suspend () -> Response<ApiResponse<T>>
): Flow<NetworkResult<T>> = flow {
    emit(NetworkResult.Loading)
    runCatching {
        apiCall()
    }.onSuccess { response ->
        if (response.isSuccessful) {
            val body = response.body()
            if (body != null && body.errorCode == 0 && body.data != null) {
                emit(NetworkResult.Success(body.data))
            } else {
                emit(
                    NetworkResult.Error(
                        code = body?.errorCode ?: response.code(),
                        message = body?.errorMsg ?: "Server error: ${response.code()}"
                    )
                )
            }
        } else {
            emit(
                NetworkResult.Error(
                    code = response.code(),
                    message = response.message() ?: "HTTP ${response.code()}"
                )
            )
        }
    }.onFailure { throwable ->
        emit(
            NetworkResult.Error(
                message = throwable.message ?: "Network exception"
            )
        )
    }
}

/**
 * 不需要 Response 包装的简化版本（直接返回 ApiResponse）
 */
inline fun <reified T> flowOfApiSimple(
    crossinline apiCall: suspend () -> ApiResponse<T>
): Flow<NetworkResult<T>> = flow {
    emit(NetworkResult.Loading)

    runCatching {
        apiCall()
    }.onSuccess { apiResponse ->
        when(apiResponse.errorCode){
            RetrofitClient.CODE_SUCCESS ->{
                emit(NetworkResult.Success(apiResponse.data))
            }

            RetrofitClient.CODE_NOT_LOGIN ->{
                Log.d("gggg", "CODE_NOT_LOGIN: ")
                GlobalViewModel.notLogin()
            }

            else -> {
                emit(NetworkResult.Error(
                    code = apiResponse.errorCode,
                    message = apiResponse.errorMsg ?: FlowApplication.context.getString(R.string.unknow_error)
                ))
            }
        }
    }.onFailure { throwable ->
        emit(
            NetworkResult.Error(
                message = throwable.message ?: "Network exception"
            )
        )
    }
}
