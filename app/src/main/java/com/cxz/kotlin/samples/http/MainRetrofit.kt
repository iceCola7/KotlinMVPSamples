package com.cxz.kotlin.samples.http

import com.cxz.kotlin.baselibs.http.RetrofitFactory
import com.cxz.kotlin.samples.constant.Constant

/**
 * @author chenxz
 * @date 2018/11/21
 * @desc
 */
object MainRetrofit : RetrofitFactory<MainApi>() {

    override fun baseUrl(): String = Constant.BASE_URL

    override fun getService(): Class<MainApi> = MainApi::class.java

}