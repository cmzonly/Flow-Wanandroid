package com.czwd.flow_wanandroid.module.search.ui

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.doOnTextChanged
import androidx.recyclerview.widget.ConcatAdapter
import androidx.recyclerview.widget.LinearLayoutManager
import com.blankj.utilcode.util.StringUtils
import com.blankj.utilcode.util.ToastUtils
import com.chad.library.adapter4.QuickAdapterHelper
import com.chad.library.adapter4.loadState.trailing.TrailingLoadStateAdapter
import com.czwd.flow_wanandroid.R
import com.czwd.flow_wanandroid.base.BaseFragment
import com.czwd.flow_wanandroid.base.BaseViewModel
import com.czwd.flow_wanandroid.databinding.FragmentSearchBinding
import com.czwd.flow_wanandroid.db.room.entity.HistoryEntity
import com.czwd.flow_wanandroid.module.home.Article
import com.czwd.flow_wanandroid.module.search.adapter.SearchHeardHotAdapter
import com.czwd.flow_wanandroid.module.search.adapter.SearchHeardHistoryAdapter
import com.czwd.flow_wanandroid.module.search.adapter.SearchLikeAdapter
import com.czwd.flow_wanandroid.module.search.adapter.SearchListAdapter
import com.czwd.flow_wanandroid.module.search.vm.SearchViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

class SearchFragment : BaseFragment<FragmentSearchBinding>() {
    override fun initBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentSearchBinding = FragmentSearchBinding.inflate(inflater , container ,false)

    private lateinit var concatAdapter: ConcatAdapter
    private lateinit var searchListHelper: QuickAdapterHelper

    /**搜索历史头部适配器*/
    private lateinit var heardHistoryAdapter : SearchHeardHistoryAdapter

    /**搜索热词头部适配器*/
    private lateinit var heardHotAdapter: SearchHeardHotAdapter


    /**置顶文章适配器*/
    private lateinit var searchLikeAdapter: SearchLikeAdapter

    /**搜索列表适配器*/
    private lateinit var searchListAdapter: SearchListAdapter

    private lateinit var searchListLayoutManager : LinearLayoutManager

    private val viewmodel by viewModel<SearchViewModel>()

    override fun provideViewModel(): BaseViewModel = viewmodel

    override fun initObserver() {
        startObserverOnStarted {
            launch {
                viewmodel.searchFlow.collectLatest {uiState ->
                    //当前文本
                    if (StringUtils.isEmpty(uiState.query)){
                        binding.et.setText(uiState.query)
                    }

                    //搜索列表
                    if (uiState.searchList.isNotEmpty()){
                        searchListAdapter.submitList(uiState.searchList)
                    }

                    //loading显示
                    showLoading(uiState.isShowLoading)

                    //搜索历史
                    showHistory(uiState.historyList)

                    //=== 热词 ===
                    heardHotAdapter.item = uiState.hotList ?: emptyList()
                    //置顶列表
                    searchLikeAdapter.submitList(uiState.likeList ?: emptyList())

//                    uiState.error.let {
//                        ToastUtils.showShort(it)
//                    }
                }
            }
        }
    }

    private fun showLoading(showLoading: Boolean) {
        if (!showLoading){
            binding.spinkitview.visibility = View.GONE
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
            isShowView(text)
        }

        //关闭按钮点击
        binding.ivClose.setOnClickListener {
            binding.et.text?.clear()
        }

        //搜索按钮点击
        binding.btnSearch.setOnClickListener {
            if (StringUtils.isTrimEmpty(binding.et.text.toString())){
                binding.rvSearch.visibility = View.VISIBLE
                binding.spinkitview.visibility = View.VISIBLE
                viewmodel.getSearchArticles(keyWord = binding.et.text.toString().trim())
            }

        }

        //搜索历史删除点击
        heardHistoryAdapter.addOnItemChildClickListener(R.id.iv_delete){_, _, _ ->
            viewmodel.deleteHistory()
        }

    }

    private fun isShowView(text: CharSequence?) {
        if (StringUtils.isTrimEmpty(text.toString())){
            binding.ivClose.visibility = View.GONE
            binding.btnSearch.visibility = View.GONE
        }else{
            binding.btnSearch.visibility = View.VISIBLE
            binding.ivClose.visibility = View.VISIBLE
        }
    }

    private fun setUpRecyclerView() {
        heardHistoryAdapter = SearchHeardHistoryAdapter()
        heardHotAdapter = SearchHeardHotAdapter()
        searchLikeAdapter = SearchLikeAdapter()

        binding.rvTop.apply {
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
            concatAdapter = ConcatAdapter(heardHotAdapter , searchLikeAdapter)
            adapter = concatAdapter
        }

        binding.rvSearch.apply {
            searchListLayoutManager = LinearLayoutManager(context , LinearLayoutManager.VERTICAL , false)
            layoutManager = searchListLayoutManager
            searchListAdapter = SearchListAdapter()
            searchListHelper = QuickAdapterHelper.Builder(searchListAdapter)
                .setTrailingLoadStateAdapter(object : TrailingLoadStateAdapter.OnTrailingListener{
                    override fun onLoad() {
//                        viewmodel.loadMore()
                    }

                    override fun onFailRetry() {
//                        viewmodel.loadMore()
                    }
                })
                .build()
            adapter=searchListHelper.adapter
        }
    }

    override fun onResume() {
        super.onResume()
        setLightStatusBar(true)
    }
}