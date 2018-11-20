package com.cxz.kotlin.samples

import com.cxz.kotlin.baselibs.base.BaseMvpTitleActivity

class MainActivity : BaseMvpTitleActivity<MainContract.View, MainContract.Presenter>(), MainContract.View {

    override fun attachChildLayoutRes(): Int = R.layout.activity_main

    override fun createPresenter(): MainContract.Presenter = MainPresenter()

    override fun hasBackIcon(): Boolean = true

    override fun initView() {
        super.initView()
        setBaseTitleColor(android.R.color.white)
        setBaseTitle("Main")
    }

    override fun initData() {
    }

    override fun start() {
    }


}
