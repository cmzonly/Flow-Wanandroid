package com.czwd.flow_wanandroid.view

import android.content.Context
import com.czwd.flow_wanandroid.R
import com.czwd.flow_wanandroid.databinding.PopupSearchBinding
import com.lxj.xpopup.core.AttachPopupView

class SearchPopup(context: Context) : AttachPopupView(context) {

    lateinit var binding : PopupSearchBinding

    override fun getImplLayoutId(): Int {
        return R.layout.popup_search
    }

    override fun onCreate() {
        super.onCreate()
        binding = PopupSearchBinding.bind(popupImplView)

    }
}