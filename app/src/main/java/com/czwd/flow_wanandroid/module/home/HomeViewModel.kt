package com.czwd.flow_wanandroid.module.home

import android.util.Log
import com.czwd.flow_wanandroid.base.AsyncState
import com.czwd.flow_wanandroid.base.BaseViewModel
import com.czwd.flow_wanandroid.network.NetworkResult
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest

/**
 * 首页 ViewModel
 *
 * 核心原则：
 * - 只暴露一个 uiState: StateFlow<HomeUiState>
 * - Fragment 只 collect 这一个 Flow
 * - 所有数据变更通过 copy() 产生新对象
 */
class HomeViewModel(private val repository: HomeRepository) : BaseViewModel() {

    // ==================== 是否是首次加载 ====================
    var isFirstLoad = true

    // ==================== 私有可变状态 ====================
    private val _uiState = MutableStateFlow(HomeUiState())
    val uiState: StateFlow<HomeUiState> = _uiState.asStateFlow()

    // ==================== 公开方法（UI 调用） ====================

    /** 首次加载：Banner + 首页文章 */
    fun loadHomeData() {
        loadBanner()
        loadArticleList(page = 0)
    }

//    /** 下拉刷新 */
//    fun refresh() {
//        _uiState.value = _uiState.value.copy(
//            isRefreshing = true,
//            errorMsg = null
//        )
//        loadBanner()
//        loadArticleList(page = 0)
//    }



    /** 收藏文章 */
    fun collect(id: Int) {
        launchOnMain {
            repository.collect(id).collectLatest { result ->
                when (result) {
                    is NetworkResult.Success -> {
                        updateCollectState(id, true)
                        _uiState.value = _uiState.value.copy(
                            collectState = AsyncState.Success(true)
                        )
                    }
                    is NetworkResult.Error -> {
                        _uiState.value = _uiState.value.copy(
                            collectState = AsyncState.Error(
                                code = result.code,
                                message = result.message
                            )
                        )
                    }
                    else -> {}
                }
            }
        }
    }

    /** 取消收藏 */
    fun unCollect(id: Int) {
        launchOnMain {
            repository.unCollect(id).collectLatest { result ->
                when (result) {
                    is NetworkResult.Success -> {
                        updateCollectState(id, false)
                        _uiState.value = _uiState.value.copy(
                            collectState = AsyncState.Success(false)
                        )
                    }
                    is NetworkResult.Error -> {
                        _uiState.value = _uiState.value.copy(
                            collectState = AsyncState.Error(
                                code = result.code,
                                message = result.message
                            )
                        )
                    }
                    else -> {}
                }
            }
        }
    }




    // ==================== 私有方法 ====================

    private fun loadBanner() {
        launchOnMain {
            repository.getBanner().collectLatest { result ->
                when (result) {
                    is NetworkResult.Success -> {
                        _uiState.value = _uiState.value.copy(
                            banner = result.data
                        )
                    }
                    is NetworkResult.Error -> {
                        _uiState.value = _uiState.value.copy(
                            errorMsg = result.message
                        )
                    }
                    else -> {}
                }
            }
        }
    }


    /** 加载更多 */
    fun loadMore() {
//        Log.d(TAG, "loadMore: ")
        if (_uiState.value.isLoading || _uiState.value.isArticleOver) return
        val nextPage = _uiState.value.currentPage
        loadArticleList(page = nextPage)
    }
    private fun loadArticleList(page: Int) {
        _uiState.value = _uiState.value.copy(
            isLoading = true,
            errorMsg = null
        )
        launchOnMain {
            repository.getArticleList(page).collectLatest { result ->
                when (result) {
                    is NetworkResult.Loading -> {

                    }

                    is NetworkResult.Success -> {
                        val paginationData = result.data

                        // 根据页码决定是替换还是追加
                        val newList = if (page == 0) {
                            // 刷新 / 首次加载：替换
                            paginationData.datas
                        } else {
                            // 加载更多：追加
                            _uiState.value.articleList + paginationData.datas
                        }

                        _uiState.value = _uiState.value.copy(
                            isLoading = false,
                            articleList = newList,
                            isArticleOver = paginationData.over,
                            currentPage = if (paginationData.over) page else page + 1
                        )
                    }

                    is NetworkResult.Error -> {
                        _uiState.value = _uiState.value.copy(
                            isLoading = false,
                            errorMsg = result.message
                        )
                    }

                    else -> {}
                }
            }
        }
    }

    /**
     * 更新列表中某篇文章的收藏状态
     * 使用 map 产生新 List，保证 diff 正确
     */
    private fun updateCollectState(id: Int, collected: Boolean) {
        val updatedList = _uiState.value.articleList.map { article ->
            if (article.id == id) {
                article.copy(collect = collected)
            } else {
                article
            }
        }
        _uiState.value = _uiState.value.copy(articleList = updatedList)
    }

}