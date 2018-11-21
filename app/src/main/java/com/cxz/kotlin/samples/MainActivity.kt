package com.cxz.kotlin.samples

import com.cxz.kotlin.baselibs.base.BaseMvpTitleActivity
import com.cxz.kotlin.baselibs.ext.loge
import com.cxz.kotlin.samples.bean.Banner
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : BaseMvpTitleActivity<MainContract.View, MainContract.Presenter>(), MainContract.View {

    override fun attachChildLayoutRes(): Int = R.layout.activity_main

    override fun createPresenter(): MainContract.Presenter = MainPresenter()

    override fun hasBackIcon(): Boolean = true

    override fun initView() {
        setStatusBarColor(resources.getColor(R.color.colorPrimary))
        setStatusBarIcon(false)
        super.initView()
        setBaseTitleColor(android.R.color.white)
        setBaseTitle("Main")

        button.setOnClickListener {
            mPresenter?.getData()
            mPresenter?.getBanner()
        }

    }

    override fun initData() {
    }

    override fun start() {
    }

    override fun showData(data: String) {
        loge("--------------showData------>>$data")
        tv_result.text = data
    }

    override fun showBanners(banners: MutableList<Banner>) {
        loge("-------------------->>$banners")
    }

}
