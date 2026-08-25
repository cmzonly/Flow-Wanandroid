package com.czwd.flow_wanandroid.module.search.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.chad.library.adapter4.BaseQuickAdapter
import com.czwd.flow_wanandroid.R
import com.czwd.flow_wanandroid.databinding.ItemHotBinding
import com.czwd.flow_wanandroid.module.search.bean.HotResponse

class HotAdapter : BaseQuickAdapter<HotResponse , HotAdapter.VH>() {
    override fun onCreateViewHolder(
        context: Context,
        parent: ViewGroup,
        viewType: Int
    ): VH  = VH(parent)

    override fun onBindViewHolder(
        holder: VH,
        position: Int,
        item: HotResponse?
    ) {
        holder.tvHot.text = item?.name ?: context.resources.getString(R.string.unknow)
    }

    class VH(
        parent : ViewGroup,
        val binding: ItemHotBinding = ItemHotBinding.inflate(LayoutInflater.from(parent.context) , parent , false)
        ) : RecyclerView.ViewHolder(binding.root){
            val tvHot = binding.tvHot
        }
}