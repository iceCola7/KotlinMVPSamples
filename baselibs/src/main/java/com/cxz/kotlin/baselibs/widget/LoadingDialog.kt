package com.cxz.kotlin.baselibs.widget

import android.app.Dialog
import android.content.Context
import android.view.Gravity
import android.view.View
import com.cxz.kotlin.baselibs.R
import com.github.ybq.android.spinkit.style.Wave
import kotlinx.android.synthetic.main.layout_loading_dialog.*

/**
 * @author chenxz
 * @date 2018/11/27
 * @desc
 */

class LoadingDialog : Dialog {

    constructor(context: Context) : super(context)

    constructor(context: Context, theme: Int) : super(context, theme)

    override fun onWindowFocusChanged(hasFocus: Boolean) {
        loadingBar.setIndeterminateDrawable(Wave())
        super.onWindowFocusChanged(hasFocus)
    }

    companion object {

        fun showDialog(context: Context, cancelable: Boolean): LoadingDialog? {
            return showDialog(context, null, cancelable)
        }

        fun showDialog(context: Context, message: CharSequence?, cancelable: Boolean): LoadingDialog? {
            val dialog = LoadingDialog(context, R.style.LoadingDialog)
            dialog.setContentView(R.layout.layout_loading_dialog)
            if (message.isNullOrBlank()) {
                dialog.tv_load_msg?.visibility = View.GONE
            } else {
                dialog.tv_load_msg?.visibility = View.VISIBLE
                dialog.tv_load_msg?.text = message
            }
            dialog.setCanceledOnTouchOutside(false)
            dialog.setCancelable(cancelable)
            dialog.window?.attributes?.gravity = Gravity.CENTER
            val lp = dialog.window?.attributes
            lp?.dimAmount = 0.2f
            dialog.window?.attributes = lp
            dialog.show()
            return dialog
        }
    }

}