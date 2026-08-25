package com.czwd.flow_wanandroid.module.system.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.chad.library.adapter4.BaseNodeAdapter
import com.czwd.flow_wanandroid.databinding.ItemSystemChildBinding
import com.czwd.flow_wanandroid.databinding.ItemSystemRootBinding
import com.czwd.flow_wanandroid.module.system.bean.SystemResponse

class SystemAdapter(val click :(SystemResponse.Children) -> Unit) : BaseNodeAdapter() {

    companion object{
        const val NODE_TYPE_ROOT = 0
        const val NODE_TYPE_CHILD = 1
    }


    /* ===================== 1. 区分 item 类型 ===================== */
    override fun getItemViewType(position: Int, list: List<Any>): Int {
      return when(list[position]){
           is SystemResponse -> NODE_TYPE_ROOT
           is SystemResponse.Children -> NODE_TYPE_CHILD
          else -> throw IllegalArgumentException("Unknown node type")
       }
    }

    /* ===================== 2. 返回子节点列表 ===================== */
    override fun getChildNodeList(
        position: Int,
        parent: Any
    ): List<Any>? {
        return when (parent) {
            is SystemResponse -> {
                // children 为空就返回 null，不要返回空 List
                parent.children.ifEmpty { null }
            }
            else -> null
        }
    }

    /* ===================== 3. 初始展开控制 ===================== */
    override fun isInitialOpen(position: Int, item: Any): Boolean {
        //默认全部展开
        return true
    }

    override fun onCreateViewHolder(
        context: Context,
        parent: ViewGroup,
        viewType: Int
    ): RecyclerView.ViewHolder {
       return  when (viewType) {
            NODE_TYPE_ROOT -> RootVH(parent)
            else -> ChildVH(parent)
        }

    }

    override fun onBindViewHolder(
        holder: RecyclerView.ViewHolder,
        position: Int,
        item: Any?
    ) {
        when (holder) {
            is RootVH -> {
                //一级
                val data = item as SystemResponse
                holder.tvRootTitle.text = data.name
            }
            is ChildVH -> {
                //二级
                val data = item as SystemResponse.Children
                holder.tvChildTitle.text = data.name
                holder.itemView.setOnClickListener {
                    click(data)
                }
            }
        }
    }

    class RootVH(
        parent  : ViewGroup,
        val binding : ItemSystemRootBinding = ItemSystemRootBinding.inflate(LayoutInflater.from(parent.context), parent, false)
    ) : RecyclerView.ViewHolder(binding.root){
        val tvRootTitle = binding.tvTitleRoot

    }

    class ChildVH(
        parent  : ViewGroup,
        val binding : ItemSystemChildBinding = ItemSystemChildBinding.inflate(LayoutInflater.from(parent.context), parent, false)
    ) : RecyclerView.ViewHolder(binding.root){
        val tvChildTitle = binding.tvTitleChild
    }
}