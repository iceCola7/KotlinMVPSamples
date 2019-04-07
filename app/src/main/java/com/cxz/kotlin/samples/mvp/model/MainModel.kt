package com.cxz.kotlin.samples.mvp.model

import com.cxz.kotlin.baselibs.mvp.BaseModel
import com.cxz.kotlin.samples.bean.Banner
import com.cxz.kotlin.samples.bean.CollectionArticle
import com.cxz.kotlin.samples.bean.CollectionResponseBody
import com.cxz.kotlin.samples.bean.HttpResult
import com.cxz.kotlin.samples.http.MainRetrofit
import com.cxz.kotlin.samples.mvp.contract.MainContract
import io.reactivex.Observable

/**
 * @author admin
 * @date 2018/11/20
 * @desc
 */
class MainModel : BaseModel(), MainContract.Model {

    override fun getBanners(): Observable<HttpResult<MutableList<Banner>>> {
        return MainRetrofit.service.getHomeBanner()
    }

    override fun login(username: String, password: String): Observable<HttpResult<Any>> {
        return MainRetrofit.service.login(username, password)
    }

    override fun getBannerList(): Observable<HttpResult<MutableList<Banner>>> {
        return MainRetrofit.service.getBannerList()
    }

    override fun getCollectList(page: Int): Observable<HttpResult<CollectionResponseBody<CollectionArticle>>> {
        return MainRetrofit.service.getCollectList(page)
    }

    override fun logout(): Observable<HttpResult<Any>> {
        return MainRetrofit.service.logout()
    }

}