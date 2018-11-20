package com.cxz.kotlin.baselibs.base

import android.support.annotation.ColorRes
import android.support.annotation.StringRes
import com.cxz.kotlin.baselibs.R
import com.cxz.kotlin.baselibs.mvp.IPresenter
import com.cxz.kotlin.baselibs.mvp.IView
import kotlinx.android.synthetic.main.activity_base_title.*
import kotlinx.android.synthetic.main.base_toolbar.*

/**
 * @author chenxz
 * @date 2018/11/20
 * @desc BaseMvpTitleActivity
 */
abstract class BaseMvpTitleActivity<in V : IView, P : IPresenter<V>> : BaseMvpActivity<V, P>() {

    /**
     * 子布局文件id
     */
    protected abstract fun attachChildLayoutRes(): Int

    /**
     * 是否启用返回键
     */
    open fun hasBackIcon(): Boolean = true

    override fun attachLayoutRes(): Int = R.layout.activity_base_title

    override fun initView() {
        super.initView()
        base_container.addView(layoutInflater.inflate(attachChildLayoutRes(), null))
        base_toolbar.title = ""
        setSupportActionBar(base_toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(hasBackIcon())
    }

    /**
     * 设置 Title
     */
    fun setBaseTitle(title: String) {
        base_title_tv.text = title
    }

    /**
     * 设置 Title
     */
    fun setBaseTitleText(@StringRes resId: Int) {
        base_title_tv.setText(resId)
    }

    /**
     * 设置 Title 颜色
     */
    fun setBaseTitleColor(@ColorRes color: Int) {
        base_title_tv.setTextColor(resources.getColor(color))
    }

}