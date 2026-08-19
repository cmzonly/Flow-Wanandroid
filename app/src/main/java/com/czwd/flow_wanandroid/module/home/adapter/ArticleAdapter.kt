package com.czwd.flow_wanandroid.module.home.adapter


import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.chad.library.adapter4.BaseQuickAdapter
import com.czwd.flow_wanandroid.R
import com.czwd.flow_wanandroid.databinding.ItemArticleBinding
import com.czwd.flow_wanandroid.module.home.Article

class ArticleAdapter : BaseQuickAdapter<Article.DataX, ArticleAdapter.ArticleHolder >() {
    override fun onCreateViewHolder(
        context: Context,
        parent: ViewGroup,
        viewType: Int
    ): ArticleHolder {
        return ArticleHolder(parent)
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(
        holder: ArticleHolder,
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
        item?.collect?.let {
            if (it) {
                holder.ivCollete.setImageResource(R.drawable.collect2)
            } else {
                holder.ivCollete.setImageResource(R.drawable.collect1)
            }
        }

    }

    class ArticleHolder(
        parent : ViewGroup,
        binding: ItemArticleBinding = ItemArticleBinding.inflate(LayoutInflater.from(parent.context), parent, false)
    ): RecyclerView.ViewHolder(binding.root){

        val ivCollete : ImageView = binding.ivCollect
        val tvTitle : TextView =binding.tvTitle
        val tvAuthor : TextView =binding.tvAuthor
        val tvType : TextView =binding.tvType
        val tvTime : TextView =binding.tvTime

    }
}