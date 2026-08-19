package com.czwd.flow_wanandroid.nav

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.czwd.flow_wanandroid.R
import com.czwd.flow_wanandroid.base.BaseFragment
import com.czwd.flow_wanandroid.databinding.FragmentNavBinding


class NavFragment : BaseFragment<FragmentNavBinding>() {
    override fun initBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentNavBinding = FragmentNavBinding.inflate(inflater,container,false)

    override fun initData() {
    }

    override fun initView() {

        binding.vp.adapter = NavAdapter(fragment = this)
        //不需要设置,viewpager2有缓存策略,查看生命周期并不会多次执行fragment的onCreateView方法
        //binding.vp.offscreenPageLimit = 3
        //在底部BottomNavigationView的menu中设置icon为选择器无法正常显示选中与未选中图标,设置此属性即可
        binding.bnv.itemIconTintList = null
        // 去除 ViewPager2 内部 RecyclerView 的边缘阴影
        (binding.vp.getChildAt(0) as? RecyclerView)?.overScrollMode = View.OVER_SCROLL_NEVER
    }

    override fun initListen() {
        binding.bnv.setOnItemSelectedListener{
            when(it.itemId){
                R.id.item_home ->{
                    if (binding.bnv.selectedItemId != R.id.item_home){
                        binding.vp.setCurrentItem(0 , false)
                    }
                }
                R.id.item_project ->{
                    if (binding.bnv.selectedItemId != R.id.item_project){
                        binding.vp.setCurrentItem(1 , false)
                    }
                }
                R.id.item_search ->{
                    if (binding.bnv.selectedItemId != R.id.item_search){
                        binding.vp.setCurrentItem(2 , false)
                    }
                }
                R.id.item_user ->{
                    if (binding.bnv.selectedItemId != R.id.item_user){
                        binding.vp.setCurrentItem(3 , false)
                    }
                }
            }
            true
        }

        binding.vp.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback(){
            override fun onPageSelected(position: Int) {
               when(position){
                   0 -> binding.bnv.selectedItemId = R.id.item_home
                   1 -> binding.bnv.selectedItemId = R.id.item_project
                   2 -> binding.bnv.selectedItemId = R.id.item_search
                   3 -> binding.bnv.selectedItemId = R.id.item_user
               }
            }
        })
    }
}