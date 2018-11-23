package com.cxz.kotlin.baselibs.rx

import com.cxz.kotlin.baselibs.bean.BaseBean
import com.cxz.kotlin.baselibs.http.exception.ErrorStatus
import com.cxz.kotlin.baselibs.http.exception.ExceptionHandle
import com.cxz.kotlin.baselibs.mvp.IView
import com.cxz.kotlin.baselibs.utils.NetWorkUtil
import io.reactivex.observers.ResourceObserver

/**
 * Created by chenxz on 2018/6/11.
 */
abstract class BaseObserver<T : BaseBean>(view: IView? = null) : ResourceObserver<T>() {

    private var mView = view

    abstract fun onSuccess(t: T)

    override fun onComplete() {
        mView?.hideLoading()
    }

    override fun onStart() {
        super.onStart()
        mView?.showLoading()
        if (!NetWorkUtil.isConnected()) {
            mView?.showDefaultMsg("当前网络不可用，请检查网络设置")
            onComplete()
        }
    }

    override fun onNext(t: T) {
        when {
            t.errorCode == ErrorStatus.SUCCESS -> onSuccess(t)
            t.errorCode == ErrorStatus.TOKEN_INVAILD -> {
                // Token 过期，重新登录
            }
            else -> mView?.showDefaultMsg(t.errorMsg)
        }
    }

    override fun onError(t: Throwable) {
        mView?.hideLoading()
        mView?.showError(ExceptionHandle.handleException(t))
    }

}
