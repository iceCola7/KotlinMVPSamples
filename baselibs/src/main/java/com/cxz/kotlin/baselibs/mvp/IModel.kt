package com.cxz.kotlin.baselibs.mvp

import io.reactivex.disposables.Disposable

/**
 * @author chenxz
 * @date 2018/11/18
 * @desc IModel
 */
interface IModel {

    fun addDisposable(disposable: Disposable?)

    fun onDetach()

}