package com.czwd.flow_wanandroid.module.details.ui

import android.os.Build
import android.os.Bundle
import android.os.Parcelable
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.core.os.BundleCompat
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.blankj.utilcode.util.ToastUtils
import com.chad.library.adapter4.QuickAdapterHelper
import com.chad.library.adapter4.loadState.LoadState
import com.chad.library.adapter4.loadState.trailing.TrailingLoadStateAdapter
import com.chad.library.adapter4.util.setOnDebouncedItemClick
import com.czwd.flow_wanandroid.R
import com.czwd.flow_wanandroid.base.BaseFragment
import com.czwd.flow_wanandroid.base.BaseViewModel
import com.czwd.flow_wanandroid.databinding.FragmentDetailsBinding
import com.czwd.flow_wanandroid.module.details.vm.DetailsViewModel
import com.czwd.flow_wanandroid.module.home.adapter.ArticleAdapter
import com.czwd.flow_wanandroid.module.system.bean.SystemResponse
import com.czwd.flow_wanandroid.module.web.WebFragment
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

class DetailsFragment : BaseFragment<FragmentDetailsBinding>() {

    lateinit var helper :QuickAdapterHelper
    lateinit var mLayoutManager: LinearLayoutManager

    companion object{
        const val CHILD = "child"

        fun toDetailFragment(fragment : Fragment , child : SystemResponse.Children){
            val bundle = Bundle().apply {
                putParcelable(CHILD , child)
            }
            fragment.findNavController().navigate(R.id.nav_to_details , bundle)
        }
    }
    lateinit var articleAdapter : ArticleAdapter

    private val viewmodel: DetailsViewModel by viewModel()

    override fun provideViewModel(): BaseViewModel  = viewmodel

    override fun onFirstLoad() {
        BundleCompat.getParcelable(arguments ?: Bundle.EMPTY , CHILD , SystemResponse.Children::class.java)
            .let { child ->
                child?.let {
                    viewmodel.setCurrentData(it)
                }
            }
    }


    override fun initView() {

        setUpRecyclerView()
        setupSwipeRefresh()
    }

    private fun setupSwipeRefresh() {
        binding.swipeRefreshLayout.setOnRefreshListener {
            viewmodel.refresh()
        }
    }

    override fun initObserver() {
        startObserverOnStarted {
            launch {
                viewmodel.detailsFlow.collectLatest {state ->
                    Log.d("details", "initObserver:${state} ")
                    //标题
                    state.currentBundleData?.let {
                        binding.tvTitle.text = it.name
                    }

                    //文章列表
                    articleAdapter.submitList(state.detailsList)

                    //loading显示 or 隐藏
                    binding.spinkitview.visibility = if (state.isShowLoading) View.VISIBLE else View.GONE

                    //加载更多状态
                    if (!state.isLoading && state.detailsList.isNotEmpty()){
                        helper.trailingLoadState = LoadState.NotLoading(state.isOver)
                    }

                    //下拉刷新
                    binding.swipeRefreshLayout.isRefreshing = state.isRefreshing

                    //error
                    state.error?.let {
                        ToastUtils.showShort(it)
                    }
                }
            }
        }
    }

    private fun setUpRecyclerView() {
        articleAdapter = ArticleAdapter()

        helper = QuickAdapterHelper.Builder(articleAdapter)
            .setTrailingLoadStateAdapter(object : TrailingLoadStateAdapter.OnTrailingListener{
                override fun onLoad() {
                    Log.d("details", "onLoad执行: ")
                    viewmodel.loadMore()
                }

                override fun onFailRetry() {
                    viewmodel.loadMore()
                }

            })
            .build()
        binding.rv.apply {
            mLayoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
            layoutManager = mLayoutManager
            adapter = helper.adapter
        }

        articleAdapter.setOnDebouncedItemClick { adapter, view, i ->
            WebFragment.startToWebFragment(this@DetailsFragment , R.id.nav_details_to_web,adapter.getItem(i).link)
        }
    }

    override fun initBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ) = FragmentDetailsBinding.inflate(inflater, container, false)


    override fun onSaveInstanceState(outState: Bundle) {
        holdRecyclerViewState(mLayoutManager.onSaveInstanceState())
    }

    override fun restoreRecyclerViewState(state: Parcelable) {
        mLayoutManager.onRestoreInstanceState(state)
    }

}