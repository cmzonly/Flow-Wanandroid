package com.czwd.flow_wanandroid.module.search.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.chad.library.adapter4.BaseQuickAdapter
import com.czwd.flow_wanandroid.databinding.ItemArticleBinding
import com.czwd.flow_wanandroid.module.home.Article

class SearchLikeAdapter : BaseQuickAdapter<Article.DataX , SearchLikeAdapter.VH>() {
    override fun onCreateViewHolder(
        context: Context,
        parent: ViewGroup,
        viewType: Int
    ): VH  = VH(parent)

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(
        holder: VH,
        position: Int,
        item: Article.DataX?
    ) {
        holder.tvTitle.text = item?.title
        item?.author?.let {
            holder.tvAuthor.text = "作者: ${it} "
        }.run {
            holder.tvAuthor.text = "作者: ${item?.shareUser}"
        }
        holder.tvType.text = "${item?.superChapterName}/${item?.chapterName}"
        holder.tvTime.text = "时间:${item?.niceShareDate}"
    }

    class VH(
        parent: ViewGroup,
        val binding: ItemArticleBinding = ItemArticleBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )

    ) : RecyclerView.ViewHolder(binding.root){
        val ivCollete : ImageView = binding.ivCollect
        val tvTitle : TextView =binding.tvTitle
        val tvAuthor : TextView =binding.tvAuthor
        val tvType : TextView =binding.tvType
        val tvTime : TextView =binding.tvTime
    }
}