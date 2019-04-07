package com.cxz.kotlin.samples.http

import com.cxz.kotlin.samples.bean.Banner
import com.cxz.kotlin.samples.bean.CollectionArticle
import com.cxz.kotlin.samples.bean.CollectionResponseBody
import com.cxz.kotlin.samples.bean.HttpResult
import io.reactivex.Observable
import retrofit2.http.*


interface MainApi {

    @GET("banner/json")
    fun getHomeBanner(): Observable<HttpResult<MutableList<Banner>>>

    /**
     * 登录
     */
    @POST("user/login")
    @FormUrlEncoded
    fun login(@Field("username") username: String, @Field("password") password: String)
            : Observable<HttpResult<Any>>

    /**
     * 退出登录
     */
    @GET("user/logout/json")
    fun logout(): Observable<HttpResult<Any>>

    /**
     * 轮播列表数据
     */
    @GET("banner/json")
    fun getBannerList(): Observable<HttpResult<MutableList<Banner>>>

    /**
     * 收藏列表数据
     */
    @GET("lg/collect/list/{page}/json")
    fun getCollectList(@Path("page") page: Int)
            : Observable<HttpResult<CollectionResponseBody<CollectionArticle>>>

}