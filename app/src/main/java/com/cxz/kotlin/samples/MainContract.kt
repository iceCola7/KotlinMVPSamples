package com.cxz.kotlin.samples

import com.cxz.kotlin.baselibs.mvp.IModel
import com.cxz.kotlin.baselibs.mvp.IPresenter
import com.cxz.kotlin.baselibs.mvp.IView

/**
 * @author admin
 * @date 2018/11/20
 * @desc
 */
interface MainContract {

    interface View : IView {
        fun showData(data: String)
    }

    interface Presenter : IPresenter<View> {
        fun getData()
    }

    interface Model : IModel {
        fun getData(): String
    }

}