package com.czwd.flow_wanandroid.module.search.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.chad.library.adapter4.QuickAdapterHelper
import com.chad.library.adapter4.loadState.trailing.TrailingLoadStateAdapter
import com.czwd.flow_wanandroid.base.BaseFragment
import com.czwd.flow_wanandroid.base.BaseViewModel
import com.czwd.flow_wanandroid.databinding.FragmentSearchBinding
import com.czwd.flow_wanandroid.module.search.adapter.SearchHeardHotAdapter
import com.czwd.flow_wanandroid.module.search.adapter.SearchHeardHistoryAdapter
import com.czwd.flow_wanandroid.module.search.adapter.SearchLikeAdapter
import com.czwd.flow_wanandroid.module.search.vm.SearchViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

class SearchFragment : BaseFragment<FragmentSearchBinding>() {
    override fun initBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentSearchBinding = FragmentSearchBinding.inflate(inflater , container ,false)


    private lateinit var helper: QuickAdapterHelper
    private lateinit var searchLikeAdapter: SearchLikeAdapter

    private lateinit var heardHotAdapter: SearchHeardHotAdapter
    private lateinit var heardHistoryAdapter : SearchHeardHistoryAdapter

    private val viewmodel by viewModel<SearchViewModel>()

    override fun provideViewModel(): BaseViewModel? = viewmodel

    override fun initObserver() {
        startObserverOnStarted {
            launch {
                viewmodel.searchFlow.collectLatest {uiState ->
                    //=== 热词 ===
                    heardHotAdapter.item = uiState.hotList ?: emptyList()
                    //列表
                    searchLikeAdapter.submitList(uiState.likeList ?: emptyList())
                }
            }
        }
    }

    override fun onFirstLoad() {
        viewmodel.getSearchData()
    }

    override fun initView() {
        setUpRecyclerView()
    }

    private fun setUpRecyclerView() {
        binding.rv.apply {
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
            searchLikeAdapter = SearchLikeAdapter()
            heardHotAdapter = SearchHeardHotAdapter()
            heardHistoryAdapter = SearchHeardHistoryAdapter()
            helper = QuickAdapterHelper.Builder(searchLikeAdapter)
                .setTrailingLoadStateAdapter(object : TrailingLoadStateAdapter.OnTrailingListener{
                    override fun onLoad() {

                    }

                    override fun onFailRetry() {

                    }

                })
                .build()
                .addBeforeAdapter(heardHotAdapter)
            adapter = helper.adapter
        }
    }

    override fun onResume() {
        super.onResume()
        setLightStatusBar(true)
    }
}