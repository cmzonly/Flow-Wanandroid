package com.czwd.flow_wanandroid.module.search.ui

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.doOnTextChanged
import androidx.recyclerview.widget.ConcatAdapter
import androidx.recyclerview.widget.LinearLayoutManager
import com.blankj.utilcode.util.StringUtils
import com.chad.library.adapter4.QuickAdapterHelper
import com.chad.library.adapter4.loadState.LoadState
import com.chad.library.adapter4.loadState.trailing.TrailingLoadStateAdapter
import com.czwd.flow_wanandroid.R
import com.czwd.flow_wanandroid.base.BaseFragment
import com.czwd.flow_wanandroid.base.BaseViewModel
import com.czwd.flow_wanandroid.databinding.FragmentSearchBinding
import com.czwd.flow_wanandroid.db.room.entity.HistoryEntity
import com.czwd.flow_wanandroid.module.search.adapter.SearchHeardHotAdapter
import com.czwd.flow_wanandroid.module.search.adapter.SearchHeardHistoryAdapter
import com.czwd.flow_wanandroid.module.search.adapter.SearchLikeAdapter
import com.czwd.flow_wanandroid.module.search.adapter.SearchListAdapter
import com.czwd.flow_wanandroid.module.search.bean.SearchUiState
import com.czwd.flow_wanandroid.module.search.vm.SearchViewModel
import com.czwd.flow_wanandroid.module.web.WebFragment
import com.czwd.flow_wanandroid.utils.PopupUtil
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

class SearchFragment : BaseFragment<FragmentSearchBinding>() {
    override fun initBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentSearchBinding = FragmentSearchBinding.inflate(inflater , container ,false)

    private  val TAG = "SearchFragment"

    private lateinit var concatAdapter: ConcatAdapter

    lateinit var searchHelp : QuickAdapterHelper

    /**搜索历史头部适配器*/
    private lateinit var heardHistoryAdapter : SearchHeardHistoryAdapter

    /**搜索热词头部适配器*/
    private lateinit var heardHotAdapter: SearchHeardHotAdapter


    /**置顶文章适配器*/
    private lateinit var likeAdapter: SearchLikeAdapter

    /**搜索列表适配器*/
    private lateinit var searchListAdapter: SearchListAdapter

    private lateinit var searchListLayoutManager : LinearLayoutManager

    private val viewmodel by viewModel<SearchViewModel>()

    override fun provideViewModel(): BaseViewModel = viewmodel

    override fun initObserver() {
        startObserverOnStarted {
            launch {
                viewmodel.searchFlow.collectLatest {uiState ->
                    //搜索历史
                    showHistory(uiState.historyList)

                    //=== 热词 ===
                    heardHotAdapter.item = uiState.hotList ?: emptyList()
                    //置顶列表
                    likeAdapter.submitList(uiState.likeList ?: emptyList())

                    //搜索列表
                    showSearchList(uiState)


//                    uiState.error.let {
//                        ToastUtils.showShort(it)
//                    }
                }
            }
        }
    }

    private fun showSearchList(uiState: SearchUiState) {
        if (StringUtils.isTrimEmpty(uiState.query)){
            binding.ivClose.visibility = View.GONE
            binding.rvSearch.visibility = View.GONE
            searchHelp.trailingLoadState = LoadState.None
        }else{
            binding.ivClose.visibility = View.VISIBLE
            binding.rvSearch.visibility = View.VISIBLE
            searchListAdapter.keyword = uiState.query
            searchListAdapter.submitList(uiState.searchList ?: emptyList())
            if (uiState.isLoading || uiState.searchList.isEmpty()){
                searchHelp.trailingLoadState = LoadState.None
            }else {
                searchHelp.trailingLoadState = LoadState.NotLoading(uiState.isOver)
            }

        }

    }


    private fun showHistory(historyList: List<HistoryEntity>?) {
        if (historyList.isNullOrEmpty()){
            concatAdapter.removeAdapter(heardHistoryAdapter)
        }else{
            concatAdapter.addAdapter(0 , heardHistoryAdapter)
            heardHistoryAdapter.item =historyList

        }
    }

    override fun onFirstLoad() {
        viewmodel.getSearchData()
    }

    override fun initView() {
        setUpRecyclerView()
    }

    override fun initListen() {
        //监听输入框文字变化
        binding.et.doOnTextChanged { text, _, _, _ ->
//            searchHelp.trailingLoadState = LoadState.None
            Log.d(TAG, "initListen: ")
            viewmodel.setKeyWord(text.toString())
        }

        //关闭按钮点击
        binding.ivClose.setOnClickListener {
            binding.et.text?.clear()
        }


        //搜索历史删除点击
        heardHistoryAdapter.addOnItemChildClickListener(R.id.iv_delete){_, _, _ ->
            viewmodel.deleteHistory()
        }

    }


    private fun setUpRecyclerView() {
        heardHistoryAdapter = SearchHeardHistoryAdapter()
        heardHotAdapter = SearchHeardHotAdapter()
        likeAdapter = SearchLikeAdapter()

        binding.rvTop.apply {
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
            concatAdapter = ConcatAdapter(heardHotAdapter , likeAdapter)
            adapter = concatAdapter
        }


        //搜索列表适配器

        binding.rvSearch.apply {
            searchListAdapter = SearchListAdapter()
            searchListLayoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
            layoutManager = searchListLayoutManager
            searchHelp = QuickAdapterHelper.Builder(searchListAdapter)
                .setTrailingLoadStateAdapter(object : TrailingLoadStateAdapter.OnTrailingListener{
                    override fun onLoad() {
                        viewmodel.loadMore()
                    }

                    override fun onFailRetry() {
                        viewmodel.loadMore()
                    }

                })
                .build()

            adapter = searchHelp.adapter

            searchListAdapter.setOnItemClickListener { adapter, view, positon ->
                WebFragment.startToWebFragment(this@SearchFragment,R.id.nav_search_to_web,searchListAdapter.getItem(positon).link)
            }
        }

    }

    override fun onResume() {
        super.onResume()
        setLightStatusBar(true)
    }
}