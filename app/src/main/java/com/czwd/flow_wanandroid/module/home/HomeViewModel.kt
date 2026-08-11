package com.czwd.flow_wanandroid.module.home

import com.czwd.flow_wanandroid.base.BaseViewModel
import com.czwd.flow_wanandroid.network.NetworkResult
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.zip

class HomeViewModel(private val homeRepository: HomeRepository) : BaseViewModel() {
     var mCurrentPager = 0
    companion object{
        private const val KEY_BANNER = "banner"
        private const val KEY_ARTICLE = "article"
        private const val KEY_COLLECT = "collect"
    }

    private var _homeFlow = MutableStateFlow<NetworkResult<HomeAllData>>(NetworkResult.Idle)
    val homeFlow = _homeFlow.asStateFlow()


    //banner
    private val _bannerFlow = MutableStateFlow<NetworkResult<List<HomeBanner>>>(NetworkResult.Idle)
    val bannerFlow = _bannerFlow.asStateFlow()

    //article
    private val _articleFlow = MutableStateFlow<NetworkResult<Article>>(NetworkResult.Idle)
    val articleFlow = _articleFlow.asStateFlow()

    //collect
    private val _collectFlow = MutableStateFlow<NetworkResult<CollectResponse>>(NetworkResult.Idle)
    val collectFlow get() = _collectFlow.asStateFlow()

    fun getHomeData(){
        requestOfManual {
            _homeFlow.value = NetworkResult.Loading
            homeRepository.getBanner().zip(homeRepository.getArticleList(0)){
                    bannerResult , articleResult ->
                Pair(bannerResult , articleResult)
            }.collectLatest { (bannerResult , articleResult) ->
                _homeFlow.value =
                    when{
                        bannerResult is NetworkResult.Success && articleResult is NetworkResult.Success ->
                            NetworkResult.Success(
                                HomeAllData(bannerResult.data, articleResult.data)
                            )
                        bannerResult is NetworkResult.Error -> bannerResult
                        articleResult is NetworkResult.Error -> articleResult
                        else -> NetworkResult.Idle
                    }
            }
        }
    }




    fun getArticleListData(currentPager: Int = 0){
        requestOfAuto(KEY_ARTICLE){
                homeRepository.getArticleList(currentPager).collectLatest {
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