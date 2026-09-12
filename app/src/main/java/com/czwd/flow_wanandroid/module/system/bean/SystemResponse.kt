package com.czwd.flow_wanandroid.module.system.bean

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class SystemResponse(
    val author: String,
    val children: List<Children>,
    val courseId: Int,
    val cover: String,
    val desc: String,
    val id: Int,
    val lisense: String,
    val lisenseLink: String,
    val name: String,
    val order: Int,
    val parentChapterId: Int,
    val type: Int,
    val userControlSetTop: Boolean,
    val visible: Int
) : Parcelable {
    @Parcelize
    data class Children(
        val articleList: List<Article>,
        val author: String,
        val courseId: Int,
        val cover: String,
        val desc: String,
        val id: Int,
        val lisense: String,
        val lisenseLink: String,
        val name: String,
        val order: Int,
        val parentChapterId: Int,
        val type: Int,
        val userControlSetTop: Boolean,
        val visible: Int
    ) : Parcelable {
        @Parcelize
        data class Article(
            val adminAdd: Boolean,
            val apkLink: String,
            val audit: Int,
            val author: String,
            val canEdit: Boolean,
            val chapterId: Int,
            val chapterName: String,
            val collect: Boolean,
            val courseId: Int,
            val desc: String,
            val descMd: String,
            val envelopePic: String,
            val fresh: Boolean,
            val host: String,
            val id: Int,
            val isAdminAdd: Boolean,
            val link: String,
            val niceDate: String,
            val niceShareDate: String,
            val origin: String,
            val prefix: String,
            val projectLink: String,
            val publishTime: Long,
            val realSuperChapterId: Int,
            val selfVisible: Int,
            val shareDate: Long,
            val shareUser: String,
            val superChapterId: Int,
            val superChapterName: String,
            val title: String,
            val type: Int,
            val userId: Int,
            val visible: Int,
            val zan: Int
        ) : Parcelable

    }

}


