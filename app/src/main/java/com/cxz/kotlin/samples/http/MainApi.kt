package com.cxz.kotlin.samples.http

import com.cxz.kotlin.samples.bean.Banner
import com.cxz.kotlin.samples.bean.HttpResult
import io.reactivex.Observable
import retrofit2.http.GET


interface MainApi {

    @GET("/banner/json")
    fun getHomeBanner(): Observable<HttpResult<MutableList<Banner>>>

}