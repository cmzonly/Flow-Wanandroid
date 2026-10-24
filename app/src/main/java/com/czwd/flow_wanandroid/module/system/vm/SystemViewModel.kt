package com.czwd.flow_wanandroid.module.system.vm

import com.czwd.flow_wanandroid.base.BaseViewModel
import com.czwd.flow_wanandroid.module.system.bean.SystemResponse
import com.czwd.flow_wanandroid.module.system.repository.SystemRepository
import com.czwd.flow_wanandroid.module.system.uistate.SystemUiState
import com.czwd.flow_wanandroid.network.NetworkResult
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
class SystemViewModel(private val systemRepository: SystemRepository) : BaseViewModel() {

    private val _systemFlow = mutableStateFlow(SystemUiState())
    val systemFlow = _systemFlow.asStateFlow()

    fun getSystemData(){
        launchOnMain {
            systemRepository.getSystemData().collectLatest {
                when(it){
                    NetworkResult.Loading -> {
                        _systemFlow.value = _systemFlow.value.copy(
                            isLoading = true
                        )
                    }
                    is NetworkResult.Success<List<SystemResponse>> -> {
                        _systemFlow.value = _systemFlow.value.copy(
                            isLoading = false,
                            isShowLoading = false,
                            systemList = it.data
                        )
                    }
                    is NetworkResult.Error -> {
                        _systemFlow.value = _systemFlow.value.copy(
                            isLoading = false,
                            isShowLoading = false,
                            error = it.message
                        )
                    }
                    else -> {}
                }
            }
        }
    }


}