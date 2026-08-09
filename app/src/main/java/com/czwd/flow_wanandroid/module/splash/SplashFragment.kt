package com.czwd.flow_wanandroid.module.splash

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.czwd.flow_wanandroid.R
import com.czwd.flow_wanandroid.base.BaseFragment
import com.czwd.flow_wanandroid.databinding.FragmentSplashBinding
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class SplashFragment: BaseFragment<FragmentSplashBinding>() {
    private val splashViewModel by viewModels<SplashViewModel>()

    override fun getViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentSplashBinding = FragmentSplashBinding.inflate(inflater,container,false)

    override fun initData() {

    }

    override fun initView() {

    }

    override fun initListen() {
        binding.skipLayout.setOnClickListener {
            splashViewModel.skipCountdown()
        }
    }

    override fun initObserver() {
        startObserverOnStarted {
            launch{
                splashViewModel.timeFlow.collectLatest {
                    binding.tvTime.text = "$it"
                }
            }

            launch {
                splashViewModel.navigateEvent.collectLatest {
                    findNavController().navigate(R.id.splash_to_nav)
                }
            }
        }
    }
}