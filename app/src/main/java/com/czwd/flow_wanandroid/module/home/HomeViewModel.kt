package com.czwd.flow_wanandroid.module.home

import com.czwd.flow_wanandroid.base.BaseViewModel
import com.czwd.flow_wanandroid.network.NetworkResult
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.zip

class HomeViewModel(private val homeRepository: HomeRepository) : BaseViewModel() {
    companion object{
        private const val KEY_HOME = "home"
    }

    private val _bannerFlow = MutableStateFlow<NetworkResult<List<Banner>>>(NetworkResult.Loading)
    val bannerFlow = _bannerFlow.asStateFlow()

    private val _articleFlow = MutableStateFlow<NetworkResult<Article>>(NetworkResult.Loading)
    val articleFlow = _articleFlow.asStateFlow()

    fun getHomeData(){
        loadOnce(KEY_HOME){
                homeRepository.apply {
                    getBanner().zip(getArticleList(0)){banner, article ->
                        Pair(banner, article)
                    }.collectLatest {
                        _bannerFlow.value = it.first
                        _articleFlow.value = it.second
                    }
                }
        }
    }

}