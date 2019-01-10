package com.cxz.kotlin.baselibs.base

import com.cxz.kotlin.baselibs.ext.showToast
import com.cxz.kotlin.baselibs.mvp.IPresenter
import com.cxz.kotlin.baselibs.mvp.IView

/**
 * @author chenxz
 * @date 2018/11/19
 * @desc BaseMvpActivity
 */
abstract class BaseMvpActivity<in V : IView, P : IPresenter<V>> : BaseActivity(), IView {

    /**
     * Presenter
     */
    protected var mPresenter: P? = null

    protected abstract fun createPresenter(): P

    override fun initView() {
        mPresenter = createPresenter()
        mPresenter?.attachView(this as V)
    }

    override fun onDestroy() {
        super.onDestroy()
        mPresenter?.detachView()
        this.mPresenter = null
    }

    override fun showLoading() {
    }

    override fun hideLoading() {
    }

    override fun showError(errorMsg: String) {
        showToast(errorMsg)
    }

    override fun showDefaultMsg(msg: String) {
        showToast(msg)
    }

    override fun showMsg(msg: String) {
        showToast(msg)
    }

}