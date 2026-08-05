package com.czwd.flow_wanandroid.module.home

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import com.czwd.flow_wanandroid.base.BaseFragment
import com.czwd.flow_wanandroid.databinding.FragmentHomeBinding
import com.czwd.flow_wanandroid.module.home.adapter.HomeBannerAdapter
import com.czwd.flow_wanandroid.network.NetworkResult
import com.youth.banner.indicator.CircleIndicator
import com.youth.banner.transformer.DepthPageTransformer
import com.youth.banner.transformer.ZoomOutPageTransformer
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

class HomeFragment : BaseFragment<FragmentHomeBinding>() {
    lateinit var homeBannerAdapter: HomeBannerAdapter

    companion object{
        private const val TAG = "HomeFragment"
    }

    private val homeViewModel: HomeViewModel by viewModel()

    override fun getViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ) = FragmentHomeBinding.inflate(inflater, container, false)

    override fun initData() {
        homeViewModel.getHomeData()
    }

    override fun initView() {
        initBanner()
    }

    private fun initBanner() {
        binding.banner.apply {
            homeBannerAdapter = HomeBannerAdapter()
            adapter = homeBannerAdapter
            indicator = CircleIndicator(this@HomeFragment.requireContext()) // 设置圆形指示器
            isAutoLoop(true) // 开启自动轮播
            setLoopTime(3000)                      // 轮播间隔3秒
            addBannerLifecycleObserver(this@HomeFragment);      // 【推荐】让Banner自动管理生命周期（开始/停止轮
            // setIndicatorSelectedColor(@ColorInt)                       设置指示器选中颜色
            //setIndicatorNormalColor(@ColorInt)                          设置指示器默认颜色
            addPageTransformer(DepthPageTransformer())  //  ZoomOutPageTransformer
            addPageTransformer(ZoomOutPageTransformer())  //ScaleInTransformer
//            setBannerRound(120f)
            setBannerGalleryEffect(10,10,10)
            setOnBannerListener { banner, position ->

            }
        }
    }

    override fun initObserver() {
        userObserverOnStarted {
            launch {
                        homeViewModel.bannerFlow.collect {
                            when(it){
                                is NetworkResult.Loading -> {
                                    Log.d(TAG, "Loading ")
                                }
                                is NetworkResult.Success -> {
                                    Log.d(TAG, "Success:${it.data} ")
                                    homeBannerAdapter.setDatas(it.data)
                                }
                                is NetworkResult.Error -> {
                                    Log.d(TAG, "Error:${it.code}--${it.message} ")
                                }
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