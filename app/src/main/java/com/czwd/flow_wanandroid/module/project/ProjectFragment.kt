package com.czwd.flow_wanandroid.module.project

import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import com.czwd.flow_wanandroid.base.BaseActivity
import com.czwd.flow_wanandroid.base.BaseFragment
import com.czwd.flow_wanandroid.databinding.FragmentProjectBinding

class ProjectFragment : BaseFragment<FragmentProjectBinding>() {
    override fun getViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentProjectBinding = FragmentProjectBinding.inflate(inflater,container , false)

    override fun initData() {
    }

    override fun initView() {
    }

    override fun onResume() {
        super.onResume()
        setLightStatusBar(false)

//        (requireActivity() as BaseActivity<*>).setRootBackground(Color.BLACK)
    }
}