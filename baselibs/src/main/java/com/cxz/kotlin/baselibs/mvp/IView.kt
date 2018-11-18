package com.cxz.kotlin.baselibs.mvp

/**
 * @author chenxz
 * @date 2018/11/18
 * @desc
 */
interface IView {

    fun showLoading()

    fun hideLoading()

    fun showError(errorMsg: String)

}