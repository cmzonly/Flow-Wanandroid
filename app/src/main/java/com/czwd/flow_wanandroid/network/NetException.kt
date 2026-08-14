package com.czwd.flow_wanandroid.network

/**
 * 网络异常封装
 */
sealed class NetException(message: String) : Exception(message) {

    /** HTTP 错误（状态码非 2xx） */
    data class HttpError(val code: Int, val msg: String) :
        NetException("HTTP $code: $msg")

    /** 业务错误（errorCode != 0） */
    data class BusinessError(val code: Int, val msg: String) :
        NetException("Business error $code: $msg")

    /** 网络不可用 */
    data object NetworkUnavailable :
        NetException("Network unavailable")

    /** 连接超时 */
    data object Timeout :
        NetException("Connection timeout")

    /** 未知错误 */
    data class Unknown(val detail: String) :
        NetException("Unknown error: $detail")
}