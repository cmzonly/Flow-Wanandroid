package com.czwd.flow_wanandroid.module.home

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import com.czwd.flow_wanandroid.base.BaseFragment
import com.czwd.flow_wanandroid.databinding.FragmentHomeBinding
import com.czwd.flow_wanandroid.network.NetworkResult
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

class HomeFragment : BaseFragment<FragmentHomeBinding>() {

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
                                    Log.d(TAG, "data = ${it.data} ")
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