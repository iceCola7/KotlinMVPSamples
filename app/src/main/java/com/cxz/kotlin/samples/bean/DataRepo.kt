package com.cxz.kotlin.samples.bean

import com.cxz.kotlin.baselibs.bean.BaseBean
import com.squareup.moshi.Json

/**
 * @author admin
 * @date 2018/11/21
 * @desc
 */

//data class HttpResult<T>(
//    @Json(name = "data") val data: T,
//    @Json(name = "errorCode") val errorCode: Int,
//    @Json(name = "errorMsg") val errorMsg: String
//)

data class HttpResult<T>(
        @Json(name = "data") val data: T
) : BaseBean()

//轮播图
data class Banner(
        @Json(name = "desc") val desc: String,
        @Json(name = "id") val id: Int,
        @Json(name = "imagePath") val imagePath: String,
        @Json(name = "isVisible") val isVisible: Int,
        @Json(name = "order") val order: Int,
        @Json(name = "title") val title: String,
        @Json(name = "type") val type: Int,
        @Json(name = "url") val url: String
)

// 登录数据
data class LoginData(
        @Json(name = "chapterTops") val chapterTops: MutableList<String>,
        @Json(name = "collectIds") val collectIds: MutableList<String>,
        @Json(name = "email") val email: String,
        @Json(name = "icon") val icon: String,
        @Json(name = "id") val id: Int,
        @Json(name = "password") val password: String,
        @Json(name = "token") val token: String,
        @Json(name = "type") val type: Int,
        @Json(name = "username") val username: String
)

data class CollectionResponseBody<T>(
        @Json(name = "curPage") val curPage: Int,
        @Json(name = "datas") val datas: List<T>,
        @Json(name = "offset") val offset: Int,
        @Json(name = "over") val over: Boolean,
        @Json(name = "pageCount") val pageCount: Int,
        @Json(name = "size") val size: Int,
        @Json(name = "total") val total: Int
)

data class CollectionArticle(
        @Json(name = "author") val author: String,
        @Json(name = "chapterId") val chapterId: Int,
        @Json(name = "chapterName") val chapterName: String,
        @Json(name = "courseId") val courseId: Int,
        @Json(name = "desc") val desc: String,
        @Json(name = "envelopePic") val envelopePic: String,
        @Json(name = "id") val id: Int,
        @Json(name = "link") val link: String,
        @Json(name = "niceDate") val niceDate: String,
        @Json(name = "origin") val origin: String,
        @Json(name = "originId") val originId: Int,
        @Json(name = "publishTime") val publishTime: Long,
        @Json(name = "title") val title: String,
        @Json(name = "userId") val userId: Int,
        @Json(name = "visible") val visible: Int,
        @Json(name = "zan") val zan: Int
)
