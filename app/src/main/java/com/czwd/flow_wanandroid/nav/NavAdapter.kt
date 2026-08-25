package com.czwd.flow_wanandroid.nav

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.czwd.flow_wanandroid.base.BaseFragment
import com.czwd.flow_wanandroid.module.home.HomeFragment
import com.czwd.flow_wanandroid.module.project.ProjectFragment
import com.czwd.flow_wanandroid.module.search.ui.SearchFragment
import com.czwd.flow_wanandroid.module.system.ui.SystemFragment
import com.czwd.flow_wanandroid.module.user.UserFragment

class NavAdapter(
    val fragmentList : List<BaseFragment<*>>
    = listOf(
        HomeFragment(),
        ProjectFragment(),
        SystemFragment(),
        UserFragment()
    ),
    fragment : Fragment
) : FragmentStateAdapter(fragment) {
    override fun createFragment(position: Int) = fragmentList[position]

    override fun getItemCount() = fragmentList.size
}