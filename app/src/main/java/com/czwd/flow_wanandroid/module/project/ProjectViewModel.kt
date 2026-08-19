package com.czwd.flow_wanandroid.module.project

import android.util.Log
import com.czwd.flow_wanandroid.base.BaseViewModel
import com.czwd.flow_wanandroid.network.NetworkResult
import com.czwd.flow_wanandroid.utils.flatMapSuccess
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest

class ProjectViewModel(val projectRepository: ProjectRepository) : BaseViewModel() {
    private val _uiState = MutableStateFlow(ProjectUiState())
    val uiState = _uiState.asStateFlow()



    /**
     * 加载项目页面数据
     */
    fun loadProjectData() {
        launchOnMain {
            projectRepository.getProjectTypeList().flatMapSuccess { result ->
                    for (i in 0 until result.size){
                        result[i].isChecked = i == 0
                    }
                    _uiState.value = _uiState.value.copy(projectTypeList = result)
                    projectRepository.getProjectList(1, result[0].id)
                }.collectLatest {
                    when(it){
                        NetworkResult.Loading -> {
                            _uiState.value = _uiState.value.copy(
                                isLoading = true
                            )
                        }
                        is NetworkResult.Success<ProjectListResponse> -> {
                            _uiState.value = _uiState.value.copy(
                                isShowLoading = false,
                                isLoading = false,
                                isRefresh = false,
                                projectList = it.data.datas
                            )
                        }
                        is NetworkResult.Error -> {
                            _uiState.value = _uiState.value.copy(
                                isShowLoading = false,
                                isLoading = false,
                                isRefresh = false,
                                errorMsg = it.message
                            )
                        }
                        else -> {}
                    }
            }
            }
        }

    /**
     * 下拉刷新
     */
    fun fresh(){
        _uiState.value = _uiState.value.copy(
            isShowLoading = false,
            isRefresh = true
        )
        loadProjectData()
    }

    /**
     * 加载某个cid数据列表
     */
    fun cidOfList(cid: Int){
        val newTypeList = _uiState.value.projectTypeList
        for (i in 0 until newTypeList.size){
            newTypeList[i].isChecked = newTypeList[i].id == cid
        }
        _uiState.value = _uiState.value.copy(
            isShowLoading = true,
            currentPage = 1,
            currentCid = cid,
            projectTypeList = newTypeList,
            projectList = emptyList()
        )
        val currentPage = _uiState.value.currentPage
        val currentCid = _uiState.value.currentCid
        getProjectList(currentPage , currentCid)
    }


    /**
     * 下拉加载更多
     */
    fun loadMore(){
        if (_uiState.value.isLoading || _uiState.value.isOver) return
        Log.d("ProjectFragment", "loadMore: ")
        val nextPage = _uiState.value.currentPage + 1
        val nextCid = _uiState.value.currentCid
        getProjectList(nextPage , nextCid)
    }


    //=======  获取项目列表数据 ======
    fun getProjectList(page: Int, cid: Int) {
        launchOnMain {
            projectRepository.getProjectList(page, cid).collectLatest {
                when(it){
                    is NetworkResult.Loading -> {
                        _uiState.value = _uiState.value.copy(
                            isLoading = true
                        )
                    }

                    is NetworkResult.Success ->{
                        val resultData = it.data
                        // 根据页码决定是替换还是追加
                        val newList = if (page == 1) {
                            // 刷新 / 首次加载：替换
                            resultData.datas
                        } else {
                            // 加载更多：追加
                            _uiState.value.projectList + resultData.datas
                        }
                        _uiState.value = _uiState.value.copy(
                            isLoading = false,
                            projectList = newList,
                            isOver = resultData.over,
                            currentPage = if (resultData.over) page else page + 1
                        )

                    }
                    is NetworkResult.Error ->{}
                    else -> {}
                }
            }
        }
    }
}