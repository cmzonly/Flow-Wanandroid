package com.czwd.flow_wanandroid.module.details.vm

import android.util.Log
import com.czwd.flow_wanandroid.base.BaseViewModel
import com.czwd.flow_wanandroid.module.details.repository.DetailsRepository
import com.czwd.flow_wanandroid.module.details.uistate.DetailsUiState
import com.czwd.flow_wanandroid.module.home.Article
import com.czwd.flow_wanandroid.module.system.bean.SystemResponse
import com.czwd.flow_wanandroid.network.NetworkResult
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest

class DetailsViewModel(private val repository: DetailsRepository) : BaseViewModel() {

    private val _detailsFlow = MutableStateFlow(DetailsUiState())
    val detailsFlow get() = _detailsFlow.asStateFlow()
    /**
     * 获取体系下详情列表
     * @param page 页码
     * @param id 体系id
     */
    fun getDetailsList(page: Int = 0, id: Int){
        launchOnMain {
            repository.getDetailsList(page, id).collectLatest {
                when(it){
                    NetworkResult.Loading -> {
                        _detailsFlow.value = _detailsFlow.value.copy(
                            isLoading = true
                        )
                    }
                    is NetworkResult.Success<Article> -> {
                        val currentData = it.data
                        val allData = if (page == 0){
                            currentData.datas
                        }else{
                            _detailsFlow.value.detailsList + currentData.datas
                        }
                        _detailsFlow.value = _detailsFlow.value.copy(
                            isRefreshing = false,
                            isLoading = false,
                            isShowLoading = false,
                            detailsList = allData,
                            isOver = currentData.over,
                            currentPage = if (currentData.over) page else page + 1
                        )
                    }
                    is NetworkResult.Error -> {
                        _detailsFlow.value = _detailsFlow.value.copy(
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

    /**
     * 加载更多
     */
    fun loadMore(){
        Log.d("details", "loadMore:${_detailsFlow.value.isOver} ")
        if (_detailsFlow.value.isLoading || _detailsFlow.value.isOver) return
        getDetailsList(_detailsFlow.value.currentPage, _detailsFlow.value.currentBundleData?.id!!)
    }

    fun setCurrentData(it: SystemResponse.Children) {
        _detailsFlow.value = _detailsFlow.value.copy(
            currentBundleData = it
        )
        getDetailsList(0,it.id)
    }

    /**
     * 下拉刷新数据
     */
    fun refresh() {
        _detailsFlow.value = _detailsFlow.value.copy(isRefreshing = true)
        getDetailsList(0, _detailsFlow.value.currentBundleData?.id!!)
    }
}