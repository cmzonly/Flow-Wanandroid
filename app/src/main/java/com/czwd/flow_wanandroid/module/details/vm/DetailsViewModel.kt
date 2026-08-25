package com.czwd.flow_wanandroid.module.details.vm

import com.czwd.flow_wanandroid.base.BaseViewModel
import com.czwd.flow_wanandroid.module.details.repository.DetailsRepository
import com.czwd.flow_wanandroid.module.details.uistate.DetailsUiState
import com.czwd.flow_wanandroid.module.home.Article
import com.czwd.flow_wanandroid.network.NetworkResult
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collectLatest

class DetailsViewModel(private val repository: DetailsRepository) : BaseViewModel() {

    private val _detailsFlow = MutableStateFlow(DetailsUiState())
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
                        _detailsFlow.value = _detailsFlow.value.copy(
                            isLoading = false,
                            detailsList = it.data.datas
                        )
                    }
                    is NetworkResult.Error -> {
                        _detailsFlow.value = _detailsFlow.value.copy(
                            isLoading = false,
                            error = it.message
                        )
                    }
                    else -> {}
                }
            }
        }
    }
}