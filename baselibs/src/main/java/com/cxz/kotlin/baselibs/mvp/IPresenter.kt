package com.cxz.kotlin.baselibs.mvp

/**
 * @author chenxz
 * @date 2018/11/18
 * @desc IPresenter
 */
interface IPresenter<in V : IView> {

    fun attachView(mView: V)

    fun detachView()

}