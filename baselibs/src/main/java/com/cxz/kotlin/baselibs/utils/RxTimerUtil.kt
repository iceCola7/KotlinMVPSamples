package com.cxz.kotlin.baselibs.utils

import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import java.util.concurrent.TimeUnit

/**
 * @author chenxz
 * @date 2019/1/10
 * @desc
 */
object RxTimerUtil {

    private var mDisposable: Disposable? = null

    /**
     * 延迟 milliseconds 毫秒后执行 task
     */
    fun postDelay(milliseconds: Long, task: ((Long) -> Unit)? = null) {
        mDisposable = Observable.timer(milliseconds, TimeUnit.MILLISECONDS)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe { number ->
                task?.invoke(number)
                cancel()
            }
    }

    /**
     * 每隔 milliseconds 毫秒循环执行 task
     */
    fun interval(milliseconds: Long, task: ((Long) -> Unit)? = null) {
        mDisposable = Observable.interval(milliseconds, TimeUnit.MILLISECONDS)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe { number ->
                task?.invoke(number)
            }
    }

    /**
     * 取消任务
     */
    fun cancel() {
        mDisposable?.let {
            if (!it.isDisposed) {
                it.dispose()
            }
        }
    }


}