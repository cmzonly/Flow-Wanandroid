package com.czwd.flow_wanandroid.module.home.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.czwd.flow_wanandroid.FlowApplication
import com.czwd.flow_wanandroid.R
import com.czwd.flow_wanandroid.databinding.ItemBannerBinding
import com.czwd.flow_wanandroid.module.home.HomeBanner
import com.youth.banner.adapter.BannerAdapter

class HomeBannerAdapter(
    bannerList : List<HomeBanner> = emptyList()
) : BannerAdapter<HomeBanner, HomeBannerAdapter.MyBannerHolder>(bannerList) {
    override fun onCreateHolder(
        parent: ViewGroup,
        viewType: Int
    ): MyBannerHolder {
        return MyBannerHolder( parent)
    }

    override fun onBindView(
        holder: MyBannerHolder?,
        p1: HomeBanner?,
        p2: Int,
        p3: Int
    ) {
        holder?.ivBanner?.let {
            Glide.with(FlowApplication.context)
                .load(p1?.imagePath)
                .into(it)
        }
    }

   class MyBannerHolder(
       viewGroup: ViewGroup,
       binding: ItemBannerBinding = ItemBannerBinding.inflate(LayoutInflater.from(viewGroup.context) , viewGroup , false)
   ) : RecyclerView.ViewHolder(binding.root){
           var ivBanner : ImageView=  binding.ivBanner

    }
}