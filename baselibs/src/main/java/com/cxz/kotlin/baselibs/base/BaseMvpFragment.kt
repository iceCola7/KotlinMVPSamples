package com.cxz.kotlin.baselibs.base

import com.cxz.kotlin.baselibs.mvp.IPresenter
import com.cxz.kotlin.baselibs.mvp.IView

/**
 * @author chenxz
 * @date 2018/11/19
 * @desc BaseMvpFragment
 */
abstract class BaseMvpFragment<in V : IView, P : IPresenter<V>> : BaseFragment(), IView {

    /**
     * Presenter
     */
    protected var mPresenter: P? = null

    protected abstract fun createPresenter(): P

    override fun initView() {
        mPresenter = createPresenter()
        if (mPresenter != null) {
            mPresenter?.attachView(this as V)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        if (mPresenter != null) {
            mPresenter?.detachView()
        }
        this.mPresenter = null
    }

}