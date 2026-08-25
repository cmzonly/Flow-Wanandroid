package com.czwd.flow_wanandroid.module.search.vm

import com.czwd.flow_wanandroid.FlowApplication
import com.czwd.flow_wanandroid.R
import com.czwd.flow_wanandroid.base.BaseViewModel
import com.czwd.flow_wanandroid.db.room.database.AppDataBase
import com.czwd.flow_wanandroid.module.search.bean.SearchUiState
import com.czwd.flow_wanandroid.module.search.repository.SearchRepository
import com.czwd.flow_wanandroid.network.NetworkResult
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest

class SearchViewModel(private val repository: SearchRepository) : BaseViewModel() {

    private val _searchFlow = MutableStateFlow(SearchUiState())
    val searchFlow = _searchFlow.asStateFlow()

    /**
     * 获取search页数据
     */
    fun getSearchData(){
        getRoomData()
        getHotWord()
        getTopArticles()
    }

    private fun getRoomData() {
        launchOnMain {
            AppDataBase.getDataBase(FlowApplication.context).historyDao().getAllHistory().apply {
                _searchFlow.value = _searchFlow.value.copy(
                    historyList = this
                )
            }
        }
    }

    //搜索热词
    fun getHotWord(){
        launchOnMain {
            repository.getHotWords().collectLatest {
                when(it){
                    is NetworkResult.Loading ->{}
                    is NetworkResult.Success ->{
                        _searchFlow.value = _searchFlow.value.copy(
                            hotList = it.data ?: emptyList()
                        )
                    }
                    is NetworkResult.Error ->{
                        _searchFlow.value = _searchFlow.value.copy(
                            error = it.message ?: FlowApplication.context.resources.getString(R.string.unknow_error)
                        )
                    }
                    else -> {}
                }
            }
        }
    }

    //置顶文章
    fun getTopArticles(){
        launchOnMain {
            repository.getTopArticles().collectLatest {
                when(it){
                    is NetworkResult.Loading ->{
                        _searchFlow.value = _searchFlow.value.copy(
                            isLoading = true
                        )
                    }
                    is NetworkResult.Success ->{
                        _searchFlow.value = _searchFlow.value.copy(
                            isLoading = false,
                             likeList = it.data ?: emptyList()
                        )
                    }
                    is NetworkResult.Error ->{
                        _searchFlow.value = _searchFlow.value.copy(
                            isLoading = false,
                            error = it.message ?: FlowApplication.context.resources.getString(R.string.unknow_error)
                        )
                    }
                    else -> {}
                }
            }
        }
    }
}