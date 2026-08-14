package com.czwd.flow_wanandroid.module.home

import android.annotation.SuppressLint
import android.os.Bundle
import android.os.Parcelable
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.blankj.utilcode.util.ToastUtils
import com.chad.library.adapter4.BaseQuickAdapter
import com.chad.library.adapter4.QuickAdapterHelper
import com.chad.library.adapter4.loadState.LoadState
import com.chad.library.adapter4.loadState.trailing.TrailingLoadStateAdapter
import com.chad.library.adapter4.util.addOnDebouncedChildClick
import com.czwd.flow_wanandroid.R
import com.czwd.flow_wanandroid.base.BaseFragment
import com.czwd.flow_wanandroid.databinding.FragmentHomeBinding
import com.czwd.flow_wanandroid.module.home.adapter.ArticleAdapter
import com.czwd.flow_wanandroid.module.home.adapter.HomeBannerAdapter
import com.czwd.flow_wanandroid.module.home.adapter.HomeBannerWrapper
import com.czwd.flow_wanandroid.module.web.WebFragment
import com.youth.banner.indicator.CircleIndicator
import com.youth.banner.transformer.DepthPageTransformer
import com.youth.banner.transformer.ZoomOutPageTransformer
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.distinctUntilChangedBy
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

/**
 * 首页 Fragment
 *
 * 架构职责：
 * - 只负责 UI 渲染
 * - 只 collect 一个 uiState Flow
 * - 通过 ViewModel 发起所有数据请求
 *
 * 解决的核心问题：
 * - Navigation 返回后 RecyclerView 不回顶（保存/恢复 LayoutManager 状态）
 * - 数据不重复加载（StateFlow + page 控制）
 * - 列表不闪烁（ListAdapter + DiffUtil）
 */
class HomeFragment : BaseFragment<FragmentHomeBinding>() {
    lateinit var articleAdapter: ArticleAdapter

    lateinit var homeBannerWrapper: HomeBannerWrapper

    lateinit var homeBannerAdapter: HomeBannerAdapter

    lateinit var helper :QuickAdapterHelper

    private lateinit var linearLayoutManager: LinearLayoutManager


    companion object{
        private const val TAG = "HomeFragment"
    }

    private val viewmodel: HomeViewModel by viewModel()

    override fun initBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ) = FragmentHomeBinding.inflate(inflater, container, false)

    override fun initView() {
        setUpRecyclerView()
        setupSwipeRefresh()
    }

    override fun initObserver() {
        startObserverOnStarted {
            launch {
                viewmodel.uiState.distinctUntilChangedBy {
                    it.articleList.size
                }.collectLatest {
                    renderState(it)
                }
            }

//            launch {
//                viewmodel.uiState
//                    .distinctUntilChangedBy { it.articleList.size }
//                    .collectLatest {
//                        helper.trailingLoadState = LoadState.NotLoading(it.isArticleOver)
//                        Log.d(TAG, "size:${it.articleList.size} ")
//                    }
//            }
        }
    }

  private  fun renderState(state: HomeUiState) {

      // === Banner ===
      homeBannerAdapter.setDatas(state.banner)
      homeBannerWrapper.item  = state.banner

      // === 下拉刷新 ===
//      binding.swipeRefreshLayout.isRefreshing = state.isRefreshing

      // === 文章列表 ===
      articleAdapter.submitList(state.articleList)

      helper.trailingLoadState = LoadState.NotLoading(state.isArticleOver)

      // === 错误处理 ===
      state.errorMsg?.let { msg ->
          // 显示错误提示（Toast / Snackbar）
          ToastUtils.showLong(msg)
      }
    }

    override fun initData() {
        if (viewmodel.isFirstLoad){
            viewmodel.loadHomeData()
            viewmodel.isFirstLoad = false
        }
    }


    // ==================== UI 初始化 ====================

    private fun setupSwipeRefresh() {
//        binding.swipeRefreshLayout.setOnRefreshListener {
//            viewmodel.refresh()
//        }
    }
    private fun setUpRecyclerView() {
        setUpBanner()
        articleAdapter = ArticleAdapter()
        helper = QuickAdapterHelper.Builder(articleAdapter)
            .setTrailingLoadStateAdapter(object : TrailingLoadStateAdapter.OnTrailingListener{
                override fun onLoad() {
                    Log.d(TAG, "onLoad: ")
                    helper.trailingLoadState = LoadState.Loading
                    viewmodel.loadMore()
                }

                override fun onFailRetry() {
                    helper.trailingLoadState = LoadState.Loading
                    viewmodel.loadMore()
                }

            })
            .setTrailPreloadSize(2)
            .build().addBeforeAdapter(homeBannerWrapper)

        binding.rv.apply {
            linearLayoutManager = LinearLayoutManager(requireActivity() , LinearLayoutManager.VERTICAL , false)
            layoutManager = linearLayoutManager
            adapter = helper.adapter
            articleAdapter.setOnItemClickListener { adapter, _, positon ->
                    ToastUtils.showLong(positon)
                    val articleInfo = articleAdapter.getItem(positon)
                    WebFragment.startToWebFragment(this@HomeFragment,articleInfo.link)
            }

            articleAdapter.addOnDebouncedChildClick(R.id.iv_collect){adapter, _, position ->
                when(adapter.getItem(position).collect){
                    true ->{
                        viewmodel.unCollect(adapter.getItem(position).id)
                    }
                    false ->{
                        viewmodel.collect(adapter.getItem(position).id)
                    }
                }
            }

        }
    }

    /**配置banner*/
    private fun setUpBanner() {
        homeBannerAdapter = HomeBannerAdapter()
        homeBannerWrapper = HomeBannerWrapper{banner ->
            banner.apply {
                setAdapter(homeBannerAdapter)
                indicator = CircleIndicator(this@HomeFragment.requireContext()) // 设置圆形指示器
                isAutoLoop(true)                                               // 开启自动轮播
                setLoopTime(3000)                                              // 轮播间隔3秒
                addBannerLifecycleObserver(this@HomeFragment);         // 【推荐】让Banner自动管理生命周期（开始/停止轮播)
                //setIndicatorSelectedColor(@ColorInt)                       设置指示器选中颜色
                //setIndicatorNormalColor(@ColorInt)                          设置指示器默认颜色
                addPageTransformer(DepthPageTransformer())  //  ZoomOutPageTransformer
                addPageTransformer(ZoomOutPageTransformer())  //ScaleInTransformer
                //setBannerRound(120f)
                setBannerGalleryEffect(10,10,10)
                setOnBannerListener{banner , _ ->
                    WebFragment.startToWebFragment(this@HomeFragment, banner?.url ?: "")
                }
            }
        }


    }

    override fun onResume() {
        super.onResume()
        setLightStatusBar(true)
    }

    // ==================== 滚动状态保存/恢复 ====================

    override fun saveRecyclerViewState() {
        // 在 onPause 中调用，保存当前滚动位置
        holdRecyclerViewState(linearLayoutManager.onSaveInstanceState())
    }

    override fun restoreRecyclerViewState(state: Parcelable) {
        // 在 onViewCreated 末尾调用，恢复滚动位置
        linearLayoutManager.onRestoreInstanceState(state)
    }

}