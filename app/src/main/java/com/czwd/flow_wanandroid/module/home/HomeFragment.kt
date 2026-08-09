package com.czwd.flow_wanandroid.module.home

import android.annotation.SuppressLint
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.chad.library.adapter4.QuickAdapterHelper
import com.czwd.flow_wanandroid.R
import com.czwd.flow_wanandroid.base.BaseFragment
import com.czwd.flow_wanandroid.databinding.FragmentHomeBinding
import com.czwd.flow_wanandroid.module.home.adapter.ArticleAdapter
import com.czwd.flow_wanandroid.module.home.adapter.HomeBannerAdapter
import com.czwd.flow_wanandroid.module.web.WebFragment
import com.czwd.flow_wanandroid.network.NetworkResult
import com.youth.banner.indicator.CircleIndicator
import com.youth.banner.transformer.DepthPageTransformer
import com.youth.banner.transformer.ZoomOutPageTransformer
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel
import androidx.recyclerview.widget.ConcatAdapter
import com.blankj.utilcode.util.ToastUtils
import com.czwd.flow_wanandroid.module.home.adapter.HomeBannerWrapper

class HomeFragment : BaseFragment<FragmentHomeBinding>() {
    lateinit var articleAdapter: ArticleAdapter

    lateinit var homeBannerWrapper: HomeBannerWrapper

    lateinit var homeBannerAdapter: HomeBannerAdapter

    lateinit var concatAdapter : ConcatAdapter

    companion object{
        private const val TAG = "HomeFragment"
    }

    private val homeViewModel: HomeViewModel by viewModel()

    override fun getViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ) = FragmentHomeBinding.inflate(inflater, container, false)

    override fun initData() {
        homeViewModel.getBannerData()
        homeViewModel.getArticleListData()
    }

    override fun initView() {
        initBanner()
        initAdapter()
    }

    private fun initAdapter() {
        binding.rv.apply {
            layoutManager = LinearLayoutManager(requireActivity() , LinearLayoutManager.VERTICAL , false)
            articleAdapter = ArticleAdapter()
            concatAdapter = ConcatAdapter(homeBannerWrapper , articleAdapter)
            adapter = concatAdapter
            articleAdapter.setOnItemClickListener { adapter, _, positon ->
                ToastUtils.showLong(positon)
                val articleInfo = articleAdapter.getItem(positon)
                WebFragment.startToWebFragment(this@HomeFragment,articleInfo.link)
            }
            
            articleAdapter.addOnItemChildClickListener(R.id.iv_collect){adapter, _, position ->
                homeViewModel.cmzCollect(adapter.getItem(position).id)
            }

        }
    }

    private fun initBanner() {
        homeBannerAdapter = HomeBannerAdapter()
        homeBannerWrapper = HomeBannerWrapper{banner ->
            banner.apply {
                homeBannerAdapter = HomeBannerAdapter()
                setAdapter(homeBannerAdapter)
                indicator = CircleIndicator(this@HomeFragment.requireContext()) // 设置圆形指示器
                isAutoLoop(true) // 开启自动轮播
                setLoopTime(3000)                      // 轮播间隔3秒
                addBannerLifecycleObserver(this@HomeFragment);      // 【推荐】让Banner自动管理生命周期（开始/停止轮
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

    @SuppressLint("NotifyDataSetChanged")
    override fun initObserver() {
        startObserverOnStarted {
            launch {
                        homeViewModel.bannerFlow.collect {
                            when(it){
                                NetworkResult.Idle -> {}

                                is NetworkResult.Loading -> {
                                }
                                is NetworkResult.Success -> {
                                    homeBannerAdapter.setDatas(it.data)
                                    homeBannerWrapper.item = it.data
                                }
                                is NetworkResult.Error -> {
                                    Log.d(TAG, "Error:${it.code}--${it.message} ")
                                }

                            }
                        }
            }

            launch {
                homeViewModel.articleFlow.collect {
                    when(it){
                        is NetworkResult.Loading -> {
                            Log.d(TAG, "Loading ")
                        }
                        is NetworkResult.Success -> {
                            Log.d(TAG, "Success:${it.data} ")
                            articleAdapter.submitList(it.data.datas)

                        }
                        is NetworkResult.Error -> {

                        }
                        NetworkResult.Idle -> {}
                    }
                }
            }

            launch {
                homeViewModel.collectFlow.collect {
                    when(it){
                        is NetworkResult.Loading ->{
                            Log.d(TAG, "collectFlow  Loading ")
                        }
                        is NetworkResult.Success<CollectResponse> -> {
                            Log.d(TAG, "collectFlow success: ")
                            homeViewModel.getArticleListData(0)
                        }
                        is NetworkResult.Error -> {
                            Log.d(TAG, "collectFlow  Error :${it.message}")
                        }

                        NetworkResult.Idle -> {}
                    }
                }
            }
        }
    }


    override fun onResume() {
        super.onResume()
        setLightStatusBar(true)
    }

}