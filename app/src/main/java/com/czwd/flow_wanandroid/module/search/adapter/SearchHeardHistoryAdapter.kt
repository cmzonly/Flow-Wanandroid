package com.czwd.flow_wanandroid.module.search.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.chad.library.adapter4.BaseSingleItemAdapter
import com.czwd.flow_wanandroid.databinding.ItemSearchHistoryHeardBinding
import com.czwd.flow_wanandroid.db.room.entity.HistoryEntity
import com.google.android.flexbox.AlignItems
import com.google.android.flexbox.FlexDirection
import com.google.android.flexbox.FlexWrap
import com.google.android.flexbox.FlexboxLayoutManager
import com.google.android.flexbox.JustifyContent

class SearchHeardHistoryAdapter : BaseSingleItemAdapter<List<HistoryEntity> , SearchHeardHistoryAdapter.VH>() {
    override fun onBindViewHolder(
        holder: VH,
        item: List<HistoryEntity>?
    )  {
        holder.rvHistory.apply {
            layoutManager = FlexboxLayoutManager(context).apply {
                flexDirection = FlexDirection.ROW                    //方向 + 排列的起点
                flexWrap = FlexWrap.WRAP                             //换行方式
                justifyContent = JustifyContent.FLEX_START           //对齐方式
                alignItems =  AlignItems.STRETCH
            }
            val historyAdapter = HistoryAdapter()
            adapter = historyAdapter
            historyAdapter.submitList(item)
        }

    }

    override fun onCreateViewHolder(
        context: Context,
        parent: ViewGroup,
        viewType: Int
    ) = VH(parent)

    class VH(
        parent : ViewGroup ,
        val binding : ItemSearchHistoryHeardBinding = ItemSearchHistoryHeardBinding.inflate(LayoutInflater.from(parent.context) , parent , false)
    ) : RecyclerView.ViewHolder(binding.root){
        val rvHistory = binding.rvHistory

    }
}