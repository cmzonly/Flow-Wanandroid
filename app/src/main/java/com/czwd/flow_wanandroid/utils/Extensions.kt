package com.czwd.flow_wanandroid.utils

// Extensions.kt

import android.widget.Toast
import androidx.fragment.app.Fragment
import com.czwd.flow_wanandroid.network.NetworkResult

fun Fragment.showToast(message: String) {
    Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
}

fun Fragment.showLongToast(message: String) {
    Toast.makeText(requireContext(), message, Toast.LENGTH_LONG).show()
}

/**
 * 处理网络请求结果的扩展函数
 */
suspend fun <T> NetworkResult<T>.onSuccess(action: suspend (T) -> Unit): NetworkResult<T> {
    if (this is NetworkResult.Success) {
        action(data)
    }
    return this
}

suspend fun <T> NetworkResult<T>.onError(action: suspend (String) -> Unit): NetworkResult<T> {
    if (this is NetworkResult.Error) {
        action(message ?: "未知错误")
    }
    return this
}