package com.cxz.kotlin.baselibs.mvp

/**
 * @author chenxz
 * @date 2018/11/18
 * @desc IPresenter
 */
interface IPresenter<in V : IView> {

    /**
     * 绑定 View
     */
    fun attachView(mView: V)

    /**
     * 解绑 View
     */
    fun detachView()

}