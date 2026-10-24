package com.czwd.flow_wanandroid.module.user

import com.czwd.flow_wanandroid.base.BaseViewModel
import com.czwd.flow_wanandroid.module.home.CollectResponse
import com.czwd.flow_wanandroid.network.NetworkResult
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest

class UserViewModel(private val userRepository: UserRepository): BaseViewModel() {

    private val _loginOutFlow = MutableStateFlow<NetworkResult<CollectResponse>>(NetworkResult.Idle)
    val loginOutFlow  get() = _loginOutFlow.asStateFlow()

    companion object{
        const val KEY_LOGIN_OUT = "login_out"
    }

    fun loginOut(){
        launchOnMain {
            userRepository.loginOut().collectLatest {
                _loginOutFlow.value = it
            }
        }
    }

}