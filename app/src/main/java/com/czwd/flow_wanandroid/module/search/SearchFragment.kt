package com.czwd.flow_wanandroid.module.search

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import com.czwd.flow_wanandroid.R
import com.czwd.flow_wanandroid.base.BaseActivity
import com.czwd.flow_wanandroid.base.BaseFragment
import com.czwd.flow_wanandroid.databinding.FragmentSearchBinding

class SearchFragment : BaseFragment<FragmentSearchBinding>() {
    override fun initBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentSearchBinding = FragmentSearchBinding.inflate(inflater , container ,false)

    override fun initData() {
    }

    override fun initView() {
    }

    override fun onResume() {
        super.onResume()
        setLightStatusBar(true)
//        (requireActivity() as BaseActivity<*>).setRootBackground(ContextCompat.getColor(requireActivity() , R.color.theme))
    }
}