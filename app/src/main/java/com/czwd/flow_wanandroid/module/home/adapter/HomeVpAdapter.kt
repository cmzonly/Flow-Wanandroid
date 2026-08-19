package com.czwd.flow_wanandroid.module.home.adapter

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.czwd.flow_wanandroid.base.BaseFragment

class HomeVpAdapter(fragment : Fragment) : FragmentStateAdapter(fragment) {
    private val fragments = listOf<BaseFragment<*>>(

    )

    override fun createFragment(p0: Int): Fragment {
        TODO("Not yet implemented")
    }

    override fun getItemCount(): Int {
        TODO("Not yet implemented")
    }
}