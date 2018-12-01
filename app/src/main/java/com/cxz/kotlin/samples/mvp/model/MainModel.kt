package com.cxz.kotlin.samples.mvp.model

import com.cxz.kotlin.baselibs.mvp.BaseModel
import com.cxz.kotlin.samples.bean.Banner
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

}