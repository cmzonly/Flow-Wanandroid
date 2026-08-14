package com.czwd.flow_wanandroid.module.project

import android.view.LayoutInflater
import android.view.ViewGroup
import com.czwd.flow_wanandroid.base.BaseFragment
import com.czwd.flow_wanandroid.databinding.FragmentProjectBinding

class ProjectFragment : BaseFragment<FragmentProjectBinding>() {
    override fun initBinding(
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
    }
}