package com.cxz.kotlin.baselibs.rx

import com.cxz.kotlin.baselibs.http.exception.ExceptionHandle
import com.cxz.kotlin.baselibs.mvp.IView
import io.reactivex.subscribers.ResourceSubscriber

/**
 * Created by chenxz on 2018/6/11.
 */
abstract class BaseSubscriber<T> : ResourceSubscriber<T> {

    private var mView: IView? = null
    private var mErrorMsg: String = ""

    constructor(view: IView) {
        this.mView = view
    }

    constructor(view: IView, errorMsg: String) {
        this.mView = view
        this.mErrorMsg = errorMsg
    }

    override fun onComplete() {
    }

    override fun onError(e: Throwable) {
        mView ?: return
        if (mErrorMsg.isNotEmpty()) {
            mView?.showError(mErrorMsg)
        } else {
            mErrorMsg = ExceptionHandle.handleException(e)
            mView?.showError(mErrorMsg)
        }
    }
}
