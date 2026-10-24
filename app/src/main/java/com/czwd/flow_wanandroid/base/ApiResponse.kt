package com.czwd.flow_wanandroid.base

data class ApiResponse<T>(
    val data: T,
    val errorCode: Int,
    val errorMsg: String
)