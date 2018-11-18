package com.cxz.kotlin.baselibs.mvp

import android.arch.lifecycle.LifecycleObserver

/**
 * @author chenxz
 * @date 2018/11/18
 * @desc
 */
abstract class BasePresenter<M : IModel, V : IView> : IPresenter<V>, LifecycleObserver {

    private var mModel: M? = null
    private var mView: V? = null

    override fun attachView(mView: V) {
        this.mView = mView
    }

}