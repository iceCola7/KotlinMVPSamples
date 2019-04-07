package com.cxz.kotlin.samples.mvp.contract

import com.cxz.kotlin.baselibs.mvp.IModel
import com.cxz.kotlin.baselibs.mvp.IPresenter
import com.cxz.kotlin.baselibs.mvp.IView
import com.cxz.kotlin.samples.bean.Banner
import com.cxz.kotlin.samples.bean.CollectionArticle
import com.cxz.kotlin.samples.bean.CollectionResponseBody
import com.cxz.kotlin.samples.bean.HttpResult
import io.reactivex.Observable

/**
 * @author admin
 * @date 2018/11/20
 * @desc
 */
interface MainContract {

    interface View : IView {
        fun showBanners(banners: MutableList<Banner>)

        fun loginSuccess()

        fun showBannerList(bannerList: MutableList<Banner>)

        fun showCollectList(collectionResponseBody: CollectionResponseBody<CollectionArticle>)

        fun logoutSuccess()
    }

    interface Presenter : IPresenter<View> {
        fun getBanner()
        fun getBanner2()
        fun getBanner3()

        fun login(username: String, password: String)
        fun getBannerList()
        fun getCollectList(page: Int)
        fun logout()
    }

    interface Model : IModel {
        fun getBanners(): Observable<HttpResult<MutableList<Banner>>>

        fun login(username: String, password: String): Observable<HttpResult<Any>>
        fun getBannerList(): Observable<HttpResult<MutableList<Banner>>>
        fun getCollectList(page: Int): Observable<HttpResult<CollectionResponseBody<CollectionArticle>>>
        fun logout(): Observable<HttpResult<Any>>

    }

}