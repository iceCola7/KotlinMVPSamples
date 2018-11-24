package com.cxz.kotlin.baselibs.ext

import android.app.Activity
import android.content.Context
import android.support.design.widget.Snackbar
import android.support.v4.app.Fragment
import android.support.v4.content.ContextCompat
import android.util.Log
import android.widget.TextView
import com.cxz.kotlin.baselibs.R
import com.cxz.kotlin.baselibs.bean.BaseBean
import com.cxz.kotlin.baselibs.http.exception.ErrorStatus
import com.cxz.kotlin.baselibs.http.exception.ExceptionHandle
import com.cxz.kotlin.baselibs.http.function.RetryWithDelay
import com.cxz.kotlin.baselibs.mvp.IModel
import com.cxz.kotlin.baselibs.mvp.IView
import com.cxz.kotlin.baselibs.rx.SchedulerUtils
import com.cxz.kotlin.baselibs.utils.NetWorkUtil
import com.cxz.kotlin.baselibs.widget.CustomToast
import io.reactivex.Observable
import io.reactivex.Observer
import io.reactivex.disposables.Disposable

/**
 * @author chenxz
 * @date 2018/11/20
 * @desc
 */

fun Any.loge(content: String?) {
    Log.e(this.javaClass.simpleName, content ?: "")
}

fun Fragment.showToast(content: String) {
    CustomToast(this?.activity?.applicationContext, content).show()
}

fun Context.showToast(content: String) {
    CustomToast(this, content).show()
}

fun Activity.showSnackMsg(msg: String) {
    val snackbar = Snackbar.make(this.window.decorView, msg, Snackbar.LENGTH_SHORT)
    val view = snackbar.view
    view.findViewById<TextView>(R.id.snackbar_text).setTextColor(ContextCompat.getColor(this, R.color.white))
    snackbar.show()
}

fun Fragment.showSnackMsg(msg: String) {
    this.activity ?: return
    val snackbar = Snackbar.make(this.activity!!.window.decorView, msg, Snackbar.LENGTH_SHORT)
    val view = snackbar.view
    view.findViewById<TextView>(R.id.snackbar_text).setTextColor(ContextCompat.getColor(this.activity!!, R.color.white))
    snackbar.show()
}

fun <T : BaseBean> Observable<T>.ss(
    model: IModel?,
    view: IView?,
    isShowLoading: Boolean = true,
    onSuccess: (T) -> Unit
) {
    this.compose(SchedulerUtils.ioToMain())
        .retryWhen(RetryWithDelay())
        .subscribe(object : Observer<T> {
            override fun onComplete() {
                view?.hideLoading()
            }

            override fun onSubscribe(d: Disposable) {
                if (isShowLoading) {
                    view?.showLoading()
                }
                model?.addDisposable(d)
                if (!NetWorkUtil.isConnected()) {
                    view?.showDefaultMsg("当前网络不可用，请检查网络设置")
                    d.dispose()
                    onComplete()
                }
            }

            override fun onNext(t: T) {
                when {
                    t.errorCode == ErrorStatus.SUCCESS -> onSuccess.invoke(t)
                    t.errorCode == ErrorStatus.TOKEN_INVAILD -> {
                        // Token 过期，重新登录
                    }
                    else -> view?.showDefaultMsg(t.errorMsg)
                }
            }

            override fun onError(t: Throwable) {
                view?.hideLoading()
                view?.showError(ExceptionHandle.handleException(t))
            }
        })
}

fun <T : BaseBean> Observable<T>.sss(
    view: IView?,
    isShowLoading: Boolean = true,
    onSuccess: (T) -> Unit
): Disposable {
    if (isShowLoading) {
        view?.showLoading()
    }
    return this.compose(SchedulerUtils.ioToMain())
        .retryWhen(RetryWithDelay())
        .subscribe({
            when {
                it.errorCode == ErrorStatus.SUCCESS -> onSuccess.invoke(it)
                it.errorCode == ErrorStatus.TOKEN_INVAILD -> {
                    // Token 过期，重新登录
                }
                else -> view?.showDefaultMsg(it.errorMsg)
            }
            view?.hideLoading()
        }, {
            view?.hideLoading()
            view?.showError(ExceptionHandle.handleException(it))
        })
}
