package com.czwd.flow_wanandroid

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.czwd.flow_wanandroid.base.BaseActivity
import com.czwd.flow_wanandroid.databinding.ActivityMainBinding
import com.czwd.flow_wanandroid.module.home.HomeViewModel
import com.czwd.flow_wanandroid.network.NetworkResult
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : BaseActivity<ActivityMainBinding>() {
    companion object{
        private const val TAG = "MainActivity"
    }
    private val homeViewModel: HomeViewModel by viewModel()
    override fun initObserver() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED){
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

    override fun initData() {
        homeViewModel.getBanner()
    }

    override fun initView() {

    }

    override fun getViewBinding(): ActivityMainBinding = ActivityMainBinding.inflate(layoutInflater)
}