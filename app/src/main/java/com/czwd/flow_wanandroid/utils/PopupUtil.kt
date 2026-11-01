package com.czwd.flow_wanandroid.utils

import android.content.Context
import android.view.View
import com.czwd.flow_wanandroid.view.SearchPopup
import com.lxj.xpopup.XPopup

object PopupUtil {

    /**
     * 显示搜索弹窗
     */
    fun showSearchPopup(
        context: Context,
        view : View
    ) {
        XPopup.Builder(context)
            .hasShadowBg(false)
            .atView(view)
            .asCustom(SearchPopup(context))
            .show()
    }
}