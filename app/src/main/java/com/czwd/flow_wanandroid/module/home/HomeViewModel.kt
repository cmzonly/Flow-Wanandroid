package com.czwd.flow_wanandroid.module.home

import com.czwd.flow_wanandroid.base.BaseViewModel
import com.czwd.flow_wanandroid.network.NetworkResult
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class HomeViewModel : BaseViewModel() {
    private val homeRepository = HomeRepository()

    private val _bannerFlow = MutableStateFlow<NetworkResult<List<Banner>>>(NetworkResult.Loading)
    val bannerFlow = _bannerFlow.asStateFlow()

    fun getBanner(){
        launchOnViewModelScope {
            homeRepository.getBanner().collect {
                _bannerFlow.value = it
            }
        }
    }

    fun getBanner1(){
        collectNetworkResult(homeRepository.getBanner()){
            _bannerFlow.value = it
        }
    }
}