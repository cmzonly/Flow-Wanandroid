package com.czwd.flow_wanandroid.module.project

import android.os.Parcelable
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.blankj.utilcode.util.ToastUtils
import com.chad.library.adapter4.QuickAdapterHelper
import com.chad.library.adapter4.loadState.LoadState
import com.chad.library.adapter4.loadState.trailing.TrailingLoadStateAdapter
import com.chad.library.adapter4.util.setOnDebouncedItemClick
import com.czwd.flow_wanandroid.R
import com.czwd.flow_wanandroid.base.BaseFragment
import com.czwd.flow_wanandroid.base.BaseViewModel
import com.czwd.flow_wanandroid.databinding.FragmentProjectBinding
import com.czwd.flow_wanandroid.module.web.WebFragment
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

class ProjectFragment : BaseFragment<FragmentProjectBinding>() {
    override fun initBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentProjectBinding = FragmentProjectBinding.inflate(inflater,container , false)

    companion object{
        private const val TAG = "ProjectFragment"
    }

    lateinit var projectTypeAdapter: ProjectTypeAdapter
    lateinit var projectListAdapter: ProjectListAdapter
    private lateinit var projectListLayoutManager: LinearLayoutManager

    private lateinit var helper: QuickAdapterHelper

    private val viewmodel by viewModel<ProjectViewModel>()

    override fun provideViewModel(): BaseViewModel = viewmodel

    override fun initView() {
        setupSwipeRefreshLayout()
        setUpRecyclerView()
    }

    private fun setupSwipeRefreshLayout() {
        binding.swiperefreshlayout.setOnRefreshListener {
            viewmodel.fresh()
        }
    }

    override fun initObserver() {
        startObserverOnStarted {
            launch {
                viewmodel.uiState.collectLatest { state ->
                    //=== loading显示  >>>
                    binding.spinkitview.visibility = if (state.isShowLoading) View.VISIBLE else View.GONE
                    //=== 下拉刷新状态 ===
                    binding.swiperefreshlayout.isRefreshing = state.isRefresh
                    //=== 左侧列表 ===
                    projectTypeAdapter.submitList(state.projectTypeList)
                    //=== 右侧列表 ===
                    projectListAdapter.submitList(state.projectList)
                    //上拉加载状态
                    if (!state.isLoading && state.projectList.isNotEmpty()){
                        helper.trailingLoadState = LoadState.NotLoading(state.isOver)
                    }
                    state.errorMsg?.let {
                        ToastUtils.showLong(it)
                    }
                }
            }
        }
    }

    private fun setUpRecyclerView() {
        binding.rv1.apply {
            layoutManager = LinearLayoutManager(context)
            projectTypeAdapter = ProjectTypeAdapter()
            adapter = projectTypeAdapter

            projectTypeAdapter.setOnItemClickListener { adapter, view, i ->
                if (adapter.getItem(i).id != viewmodel.uiState.value.currentCid){
                    helper.trailingLoadState = LoadState.None
                    viewmodel.cidOfList(adapter.getItem(i).id)
                }

            }
        }

        binding.rv2.apply {
            projectListLayoutManager = LinearLayoutManager(context)
            layoutManager = projectListLayoutManager
            projectListAdapter = ProjectListAdapter()
            helper= QuickAdapterHelper.Builder(projectListAdapter)
                .setTrailingLoadStateAdapter(object : TrailingLoadStateAdapter.OnTrailingListener{
                    override fun onLoad() {
                        Log.d(TAG, "onLoad: ")
                        viewmodel.loadMore()
                    }

                    override fun onFailRetry() {
                        viewmodel.loadMore()
                    }

                })
                .build()
            adapter = helper.adapter

            projectListAdapter.setOnDebouncedItemClick { adapter, view, i ->
                WebFragment.startToWebFragment(this@ProjectFragment , R.id.nav_home_to_web,adapter.getItem(i).link)
            }

        }
    }

    override fun onFirstLoad() {
        viewmodel.loadProjectData()
    }


    // === 保存与恢复recyclerview状态
    override fun saveRecyclerViewState() {
        holdRecyclerViewState(projectListLayoutManager.onSaveInstanceState())
    }

    override fun restoreRecyclerViewState(state: Parcelable) {
        projectListLayoutManager.onRestoreInstanceState(state)
    }


    // === 状态栏设置 ===
    override fun onResume() {
        super.onResume()
        setLightStatusBar(false)
    }
}