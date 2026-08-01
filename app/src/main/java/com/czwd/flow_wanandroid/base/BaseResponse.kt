package com.czwd.flow_wanandroid.base

data class BaseResponse<T>(
    val data: T,
    val errorCode: Int,
    val errorMsg: String
) {
    val isSuccess get() = errorCode == 0
}