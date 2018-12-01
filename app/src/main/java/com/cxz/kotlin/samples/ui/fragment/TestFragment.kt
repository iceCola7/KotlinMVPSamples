package com.cxz.kotlin.samples.ui.fragment

import com.cxz.kotlin.baselibs.base.BaseMvpFragment
import com.cxz.kotlin.samples.R
import com.cxz.kotlin.samples.mvp.contract.TestContract
import com.cxz.kotlin.samples.mvp.presenter.TestPresenter

/**
 * @author chenxz
 * @date 2018/11/30
 * @desc
 */
class TestFragment : BaseMvpFragment<TestContract.View, TestContract.Presenter>(), TestContract.View {

    override fun createPresenter(): TestContract.Presenter = TestPresenter()

    override fun attachLayoutRes(): Int = R.layout.fragment_test

    override fun lazyLoad() {
    }
}