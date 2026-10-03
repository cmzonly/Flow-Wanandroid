package com.czwd.flow_wanandroid.module.search.vm

import android.util.Log
import com.czwd.flow_wanandroid.FlowApplication
import com.czwd.flow_wanandroid.R
import com.czwd.flow_wanandroid.base.BaseViewModel
import com.czwd.flow_wanandroid.db.room.entity.HistoryEntity
import com.czwd.flow_wanandroid.db.room.ext.roomDao
import com.czwd.flow_wanandroid.db.room.ext.roomDb
import com.czwd.flow_wanandroid.module.home.Article
import com.czwd.flow_wanandroid.module.search.bean.SearchUiState
import com.czwd.flow_wanandroid.module.search.repository.SearchRepository
import com.czwd.flow_wanandroid.network.NetworkResult
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.update


class SearchViewModel(private val repository: SearchRepository) : BaseViewModel() {
    private  val TAG = "SearchViewModel"

    private val _searchFlow = MutableStateFlow(SearchUiState())
    val searchFlow = _searchFlow.asStateFlow()

//    private val queryFlow = MutableStateFlow("")

    /**
     * 此方法用于非手动点击搜索
     */
//    init {
//        observeQuery()
//    }

//    private fun observeQuery() {
//        launchOnMain {
//            queryFlow
//                .debounce(500L)
//                .distinctUntilChanged()
//                .filter { it.isNotBlank() }
//                .collectLatest { keyWord ->
//                    getSearchArticles(_searchFlow.value.currentPage , keyWord)
//                }
//        }
//    }

    /**
     * 获取搜索结果
     */
    fun getSearchArticles(page : Int = 0 , keyWord: String){
        _searchFlow.update {
            it.copy(
                query = keyWord
            )
        }
       insertHistory(keyWord)
        launchOnMain {
            repository.getSearchArticles(page , keyWord).collectLatest {
                when(it){
                    NetworkResult.Loading -> {
                        Log.d(TAG, "Loading: ")
                    }
                    is NetworkResult.Success<Article> -> {
                        Log.d(TAG, "Success: ")
                        val currentData = it.data
                        val allData = if (page ==0){
                            currentData.datas
                        }else{
                            _searchFlow.value.searchList + currentData.datas
                        }
                        _searchFlow.update { state ->
                            state.copy(
                                isShowLoading = false,
                                isLoading = false,
                                searchList = allData,
                                isOver = currentData.over,
                                currentPage = if (currentData.over) page else page + 1
                            )
                        }

                    }
                    is NetworkResult.Error -> {
                        Log.d(TAG, "Error:${it.message} ")
                        _searchFlow.value = _searchFlow.value.copy(
                            isShowLoading = false,
                            isLoading = false,
                            error = it.message ?: FlowApplication.context.resources.getString(R.string.unknow_error)
                        )
                    }
                    else -> {
                        _searchFlow.update { state ->
                            state.copy(
                                isLoading = false,
                                isShowLoading = false
                            )
                        }
                        Log.d(TAG, "else:")
                    }
                }
            }
        }

    }

    private fun insertHistory(keyWord: String) {
        launchOnIO {
           roomDao.deleteByName(keyWord)
            roomDao.insert(HistoryEntity(keyWord))
            _searchFlow.update {
                it.copy(
                    historyList = roomDao.getAllHistory()
                )
            }
        }

    }

    /**
     * 获取search页数据
     */
    fun getSearchData(){
        getRoomData()
        getHotWord()
        getTopArticles()
    }

    /**
     * 获取搜索内容
     */

    /**
     * 删除历史记录
     */
    fun deleteHistory(){
        launchOnIO {
            roomDao.deleteAll()
            _searchFlow.update {
                it.copy(
                    historyList = emptyList()
                )
            }
        }
    }

    private fun getRoomData() {
        launchOnIO {
            roomDao.getAllHistory().apply {
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