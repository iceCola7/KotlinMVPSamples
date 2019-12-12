package com.cxz.kotlin.samples.ui.activity

import android.text.TextUtils
import android.view.View
import com.bumptech.glide.Glide
import com.cxz.kotlin.baselibs.base.BaseMvpTitleActivity
import com.cxz.kotlin.baselibs.ext.setSingleClickListener
import com.cxz.kotlin.samples.R
import com.cxz.kotlin.samples.bean.Banner
import com.cxz.kotlin.samples.bean.CollectionArticle
import com.cxz.kotlin.samples.bean.CollectionResponseBody
import com.cxz.kotlin.samples.mvp.contract.MainContract
import com.cxz.kotlin.samples.mvp.presenter.MainPresenter
import com.cxz.kotlin.samples.utils.DialogUtil
import com.cxz.kotlin.samples.utils.PermissionHelper
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : BaseMvpTitleActivity<MainContract.View, MainContract.Presenter>(),
    MainContract.View {

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
    }

    override fun initData() {
        btn_login.setSingleClickListener {
            val username = et_username.text.toString()
            val password = et_password.text.toString()
            if (TextUtils.isEmpty(username)) {
                showDefaultMsg("账号不能为空")
                return@setSingleClickListener
            }
            if (TextUtils.isEmpty(password)) {
                showDefaultMsg("密码不能为空")
                return@setSingleClickListener
            }
            mPresenter?.login(username, password)
        }
        btn_logout.setSingleClickListener {
            mPresenter?.logout()
        }
        btn_get_banner.setSingleClickListener {
            tv_result.text = ""
            imageView.visibility = View.VISIBLE
            mPresenter?.getBannerList()
        }
        btn_collect.setSingleClickListener {
            tv_result.text = ""
            imageView.visibility = View.GONE
            mPresenter?.getCollectList(0)
        }
        btn_permission.setSingleClickListener {
            PermissionHelper.requestCameraPermission(this) {
                showDefaultMsg("相机权限申请成功")
            }
//            rxPermissions.request(Manifest.permission.READ_EXTERNAL_STORAGE)
//                    .subscribe {
//                        if (it) {
//                            showDefaultMsg("已允许")
//                        } else {
//                            showDefaultMsg("未允许")
//                        }
//                    }
        }
    }

    override fun start() {
    }

    override fun showBanners(banners: MutableList<Banner>) {
        tv_result.text = banners.toString()
    }

    override fun loginSuccess() {
        showDefaultMsg("登录成功")
    }

    override fun showBannerList(bannerList: MutableList<Banner>) {
        if (bannerList.size > 0) {
            tv_result.text = bannerList[0].title
            Glide.with(this).load(bannerList[0].imagePath).into(imageView)
        }
    }

    override fun showCollectList(collectionResponseBody: CollectionResponseBody<CollectionArticle>) {
        if (collectionResponseBody.datas.isNotEmpty()) {
            tv_result.text = collectionResponseBody.datas[0].title
        }
    }

    override fun logoutSuccess() {
        showDefaultMsg("已退出登录")
    }

}
