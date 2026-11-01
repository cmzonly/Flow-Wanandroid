package com.czwd.flow_wanandroid.utils

// Extensions.kt

import android.graphics.Color
import android.text.Spannable
import android.text.SpannableString
import android.text.style.ForegroundColorSpan
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.czwd.flow_wanandroid.network.NetworkResult
import java.util.regex.Pattern

fun Fragment.showToast(message: String) {
    Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
}

fun Fragment.showLongToast(message: String) {
    Toast.makeText(requireContext(), message, Toast.LENGTH_LONG).show()
}

/**
 * 处理网络请求结果的扩展函数
 */
suspend fun <T> NetworkResult<T>.onSuccess(action: suspend (T) -> Unit): NetworkResult<T> {
    if (this is NetworkResult.Success) {
        action(data)
    }
    return this
}

suspend fun <T> NetworkResult<T>.onError(action: suspend (String) -> Unit): NetworkResult<T> {
    if (this is NetworkResult.Error) {
        action(message ?: "未知错误")
    }
    return this
}

/**
 * 文本高亮：将 [keyword] 在原文中匹配到的片段染成 [color]
 * - 忽略大小写
 * - 使用 Pattern.quote 避免正则元字符问题
 * - 关键词为空时直接返回原文，减少 Span 开销
 */
fun CharSequence?.highlight(keyword: String, color: Int = Color.parseColor("#FF0000")): CharSequence {
    val source = this ?: return ""
    if (keyword.isBlank() || source.isBlank()) return source
    val spannable = SpannableString(source)
    val matcher = Pattern.compile(Pattern.quote(keyword), Pattern.CASE_INSENSITIVE).matcher(source)
    while (matcher.find()) {
        spannable.setSpan(
            ForegroundColorSpan(color),
            matcher.start(),
            matcher.end(),
            Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
        )
    }
    return spannable
}