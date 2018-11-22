package com.cxz.kotlin.samples

import com.cxz.kotlin.baselibs.base.BaseMvpTitleActivity
import com.cxz.kotlin.baselibs.ext.loge
import com.cxz.kotlin.samples.bean.Banner
import com.cxz.kotlin.samples.utils.DialogUtil
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : BaseMvpTitleActivity<MainContract.View, MainContract.Presenter>(), MainContract.View {

    private val mDialog by lazy {
        DialogUtil.getWaitDialog(this, "正在加载")
    }

    override fun attachChildLayoutRes(): Int = R.layout.activity_main

    override fun createPresenter(): MainContract.Presenter = MainPresenter()

    override fun hasBackIcon(): Boolean = true

    override fun showLoading() {
        mDialog.show()
    }

    override fun hideLoading() {
        mDialog.dismiss()
    }

    override fun initView() {
        setStatusBarColor(resources.getColor(R.color.colorPrimary))
        setStatusBarIcon(false)
        super.initView()
        setBaseTitleColor(android.R.color.white)
        setBaseTitle("Main")

        button.setOnClickListener {
            tv_result.text = ""
            mPresenter?.getBanner()
        }

        button2.setOnClickListener {
            tv_result.text = ""
            mPresenter?.getBanner2()
        }

        button3.setOnClickListener {
            tv_result.text = ""
            mPresenter?.getBanner3()
        }

    }

    override fun initData() {
    }

    override fun start() {
    }

    override fun showBanners(banners: MutableList<Banner>) {
        loge("-------->>$banners")
        tv_result.text = banners.toString()
    }

}
