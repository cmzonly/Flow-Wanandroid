package com.czwd.flow_wanandroid.module.home.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.chad.library.adapter4.BaseSingleItemAdapter
import com.czwd.flow_wanandroid.R
import com.czwd.flow_wanandroid.databinding.BannerWrapperBinding
import com.czwd.flow_wanandroid.module.home.HomeBanner
import com.youth.banner.Banner

class HomeBannerWrapper(
   private var block :(Banner<HomeBanner , HomeBannerAdapter>) -> Unit
) : BaseSingleItemAdapter<List<HomeBanner>, HomeBannerWrapper.VH>() {



    override fun onCreateViewHolder(
        context: Context,
        parent: ViewGroup,
        viewType: Int
    ): VH  = VH(parent , block)

    override fun onBindViewHolder(
        holder: VH,
        item: List<HomeBanner>?
    ) {
       item?.let {
           holder.banner.adapter.setDatas(it)
       }
    }

    class VH(
        parent: ViewGroup,
        block: (Banner<HomeBanner, HomeBannerAdapter>) -> Unit

        ) : RecyclerView.ViewHolder(
        BannerWrapperBinding.inflate(LayoutInflater.from(parent.context) ,parent ,false).root
    ){
        val banner: Banner<HomeBanner, HomeBannerAdapter> = itemView.findViewById(R.id.banner)

        init {
            block.invoke(banner)
        }

    }
}