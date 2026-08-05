package com.czwd.flow_wanandroid.module.home.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.core.widget.ImageViewCompat
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.czwd.flow_wanandroid.FlowApplication
import com.czwd.flow_wanandroid.R
import com.czwd.flow_wanandroid.module.home.Banner
import com.youth.banner.adapter.BannerAdapter

class HomeBannerAdapter(
    bannerList : List<Banner> = emptyList()
) : BannerAdapter<Banner, HomeBannerAdapter.MyBannerHolder>(bannerList) {
    override fun onCreateHolder(
        parent: ViewGroup?,
        viewType: Int
    ): MyBannerHolder {
        val view = LayoutInflater.from(parent?.context).inflate(R.layout.item_banner, parent, false)
        return MyBannerHolder( view)
    }

    override fun onBindView(
        holder: MyBannerHolder?,
        p1: Banner?,
        p2: Int,
        p3: Int
    ) {
        holder?.ivBanner?.let {
            Glide.with(FlowApplication.context)
                .load(p1?.imagePath)
                .into(it)
        }
    }

   class MyBannerHolder(itemView : View) : RecyclerView.ViewHolder(itemView){
           var ivBanner : ImageView=  itemView.findViewById(R.id.iv_banner)

    }
}