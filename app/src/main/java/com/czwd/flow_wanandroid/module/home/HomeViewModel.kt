package com.czwd.flow_wanandroid.module.home

import com.czwd.flow_wanandroid.base.BaseViewModel
import com.czwd.flow_wanandroid.network.NetworkResult
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest

class HomeViewModel(private val homeRepository: HomeRepository) : BaseViewModel() {
    companion object{
        private const val KEY_BANNER = "banner"
        private const val KEY_ARTICLE = "article"
        private const val KEY_COLLECT = "collect"
    }


    //banner
    private val _bannerFlow = MutableStateFlow<NetworkResult<List<HomeBanner>>>(NetworkResult.Idle)
    val bannerFlow = _bannerFlow.asStateFlow()

    //article
    private val _articleFlow = MutableStateFlow<NetworkResult<Article>>(NetworkResult.Idle)
    val articleFlow = _articleFlow.asStateFlow()

    //collect
    private val _collectFlow = MutableStateFlow<NetworkResult<CollectResponse>>(NetworkResult.Idle)
    val collectFlow get() = _collectFlow.asStateFlow()

    fun getBannerData(){
        requestOfAuto(KEY_BANNER) {
            homeRepository.getBanner().collectLatest {
                _bannerFlow.value = it
            }
        }
    }

    fun getArticleListData(pageNum: Int = 0, isRefresh : Boolean = false){
        if (isRefresh) resetKey(KEY_ARTICLE)
        requestOfAuto(KEY_ARTICLE){
                homeRepository.getArticleList(pageNum).collectLatest {
                    _articleFlow.value = it
                }
        }
    }

    /**
     * 收藏
     */
    fun cmzCollect(cmzId : Int){
       requestOfManual{
           homeRepository.cmzCollect(cmzId).collectLatest {
               _collectFlow.value = it
           }
       }
    }

}