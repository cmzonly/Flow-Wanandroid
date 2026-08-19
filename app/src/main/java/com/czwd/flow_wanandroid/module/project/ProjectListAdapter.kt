package com.czwd.flow_wanandroid.module.project

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.chad.library.adapter4.BaseQuickAdapter
import com.czwd.flow_wanandroid.databinding.ItemProjectListBinding

class ProjectListAdapter : BaseQuickAdapter<ProjectListResponse.DataX , ProjectListAdapter.VH>() {
    override fun onCreateViewHolder(
        context: Context,
        parent: ViewGroup,
        viewType: Int
    ): VH  = VH(parent)

    override fun onBindViewHolder(
        holder: VH,
        position: Int,
        item: ProjectListResponse.DataX?
    ) {
        holder.tvTitle.text = item?.title
        holder.tvContent.text = item?.desc
    }

    class VH(
        parent : ViewGroup,
        val binding: ItemProjectListBinding = ItemProjectListBinding.inflate(LayoutInflater.from(parent.context) , parent , false)
    ) : RecyclerView.ViewHolder(binding.root){
        val tvTitle : TextView = binding.tvTitle
        val tvContent : TextView = binding.tvContent
    }
}