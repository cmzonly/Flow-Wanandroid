package com.czwd.flow_wanandroid.module.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import com.czwd.flow_wanandroid.R
import com.czwd.flow_wanandroid.base.BaseActivity
import com.czwd.flow_wanandroid.base.BaseFragment
import com.czwd.flow_wanandroid.databinding.FragmentHomeBinding

class HomeFragment : BaseFragment<FragmentHomeBinding>() {
    override fun getViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ) = FragmentHomeBinding.inflate(inflater, container, false)

    override fun initData() {
    }

    override fun initView() {
    }

    override fun onResume() {
        super.onResume()
        setLightStatusBar(true)
//        (requireActivity() as BaseActivity<*>).setRootBackground(ContextCompat.getColor(requireActivity() , R.color.home))
    }

}