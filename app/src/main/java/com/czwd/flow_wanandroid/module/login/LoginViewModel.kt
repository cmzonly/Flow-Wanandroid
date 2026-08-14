package com.czwd.flow_wanandroid.module.login

import com.czwd.flow_wanandroid.base.BaseViewModel
import com.czwd.flow_wanandroid.network.NetworkResult
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest

class LoginViewModel(val loginRepository: LoginRepository): BaseViewModel() {

    private var _registerFlow = MutableStateFlow<NetworkResult<RegisterResponse>>(NetworkResult.Idle)
    val registerFlow get() = _registerFlow.asStateFlow()

    private var _loginFlow = MutableStateFlow<NetworkResult<LoginResponse>>(NetworkResult.Idle)
    val loginFlow get() = _loginFlow.asStateFlow()

    companion object{
        private const val KEY_REGISTER = "register"
        private const val KEY_LOGIN = "login"
    }


    /**
     * 注册
     */
    fun register(
        userName : String = "" ,
        passWord : String = "",
        repeatPassWord : String = ""
    ){
        launchOnMain {
            loginRepository.register(userName ,passWord , repeatPassWord).collectLatest {
                _registerFlow.value = it
            }
        }


    }

    /**
     * 登录
     */
    fun login(
        userName : String = "" ,
        passWord : String = ""
    ){
        launchOnMain {
            loginRepository.login(userName , passWord).collectLatest {
                _loginFlow.value = it
            }
        }
    }
}