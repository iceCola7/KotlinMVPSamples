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
