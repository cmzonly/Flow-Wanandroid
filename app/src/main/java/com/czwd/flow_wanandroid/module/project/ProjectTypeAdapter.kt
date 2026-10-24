package com.czwd.flow_wanandroid.module.project

import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.chad.library.adapter4.BaseQuickAdapter
import com.czwd.flow_wanandroid.FlowApplication
import com.czwd.flow_wanandroid.R
import com.czwd.flow_wanandroid.databinding.ItemProjectTypeBinding
import com.hjq.shape.view.ShapeTextView

class ProjectTypeAdapter : BaseQuickAdapter<ProjectTypeResponse , ProjectTypeAdapter.VH>() {
    override fun onCreateViewHolder(
        context: Context,
        parent: ViewGroup,
        viewType: Int
    ): VH  = VH(parent)

    override fun onBindViewHolder(
        holder: VH,
        position: Int,
        item: ProjectTypeResponse?
    ) {
        holder.tvType.text = item?.name ?: FlowApplication.context.getString(R.string.unknow)
        item?.let {
            if (it.isChecked){
                holder.tvType.shapeDrawableBuilder.apply {
                    solidColor = context.resources.getColor(R.color.gray , null)
                }.intoBackground()

                holder.tvType.textColorBuilder.apply {
                    textColor = context.resources.getColor(R.color.theme , null)
                }.intoTextColor()
            }else{
                holder.tvType.shapeDrawableBuilder.apply {
                    solidColor = Color.WHITE
                }.intoBackground()
                holder.tvType.textColorBuilder.apply {
                    textColor = context.resources.getColor(R.color.black , null)
                }.intoTextColor()
            }
        }
    }

    class VH(
        parent : ViewGroup,
        val binding : ItemProjectTypeBinding = ItemProjectTypeBinding.inflate(LayoutInflater.from(parent.context) , parent , false)
    ) : RecyclerView.ViewHolder(binding.root){
        val tvType : ShapeTextView = binding.tvType
    }
}