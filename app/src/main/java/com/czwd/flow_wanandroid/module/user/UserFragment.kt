package com.czwd.flow_wanandroid.module.user

import android.view.LayoutInflater
import android.view.ViewGroup
import com.czwd.flow_wanandroid.base.BaseFragment
import com.czwd.flow_wanandroid.databinding.FragmentUserBinding

class UserFragment : BaseFragment<FragmentUserBinding>() {
    override fun getViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentUserBinding  = FragmentUserBinding.inflate(inflater,container,false)

    override fun initData() {
    }

    override fun initView() {
    }

    override fun onResume() {
        super.onResume()
        setLightStatusBar(true)
    }
}