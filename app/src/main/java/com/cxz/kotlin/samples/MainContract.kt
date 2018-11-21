package com.cxz.kotlin.samples

import com.cxz.kotlin.baselibs.mvp.IModel
import com.cxz.kotlin.baselibs.mvp.IPresenter
import com.cxz.kotlin.baselibs.mvp.IView
import com.cxz.kotlin.samples.bean.Banner
import com.cxz.kotlin.samples.bean.HttpResult
import io.reactivex.Observable

/**
 * @author admin
 * @date 2018/11/20
 * @desc
 */
interface MainContract {

    interface View : IView {
        fun showData(data: String)
        fun showBanners(banners: MutableList<Banner>)
    }

    interface Presenter : IPresenter<View> {
        fun getData()
        fun getBanner()
    }

    interface Model : IModel {
        fun getData(): String
        fun getBanners(): Observable<HttpResult<MutableList<Banner>>>
    }

}