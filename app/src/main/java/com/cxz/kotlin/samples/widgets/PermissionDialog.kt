package com.cxz.kotlin.samples.widgets

import android.content.DialogInterface
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.support.v4.app.DialogFragment
import android.view.*
import com.cxz.kotlin.baselibs.widget.OnNoDoubleClickListener
import com.cxz.kotlin.samples.R
import kotlinx.android.synthetic.main.dialog_permission.view.*

/**
 * @author admin
 * @date 2019/10/23
 * @desc
 */
class PermissionDialog : DialogFragment() {

    companion object {
        val TITLE = "title"
        val CONTENT = "content"
        val RIGHT_TEXT = "right_text"
        val CANCEL_ABLE = "cancel_able"

        fun newInstance(builder: Builder): PermissionDialog {
            val bundle = Bundle()
            val fragment = PermissionDialog()
            bundle.putString(TITLE, builder.title)
            bundle.putString(CONTENT, builder.content)
            bundle.putString(RIGHT_TEXT, builder.rightText)
            fragment.arguments = bundle
            fragment.isCancelable = builder.cancelAble
            return fragment
        }

        fun newBuilder(): Builder {
            return Builder()
        }
    }

    private var onConfirmClickListener: (() -> Unit)? = null
    private var onCancelClickListener: (() -> Unit)? = null

    fun setOnConfirmClickListener(click: (() -> Unit)): PermissionDialog {
        this.onConfirmClickListener = click
        return this
    }

    fun setOnCancelClickListener(click: (() -> Unit)): PermissionDialog {
        this.onCancelClickListener = click
        return this
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        dialog?.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog?.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog?.setCanceledOnTouchOutside(false)
        dialog?.setOnKeyListener(DialogInterface.OnKeyListener { dialog, keyCode, event ->
            if (keyCode == KeyEvent.KEYCODE_BACK) {
                return@OnKeyListener true
            }
            false
        })

        val rootView = inflater.inflate(R.layout.dialog_permission, container, false)
        initView(rootView)
        return rootView
    }

    private fun initView(view: View) {

        val title = this.arguments?.getString(TITLE)
        if (title.isNullOrEmpty().not()) {
            view.tv_title.visibility = View.VISIBLE
            view.tv_title.text = title
        }
        val content = this.arguments?.getString(CONTENT)
        if (content.isNullOrEmpty().not()) {
            view.tv_content.visibility = View.VISIBLE
            view.tv_content.text = content
        }
        val rightText = this.arguments?.getString(RIGHT_TEXT)
        if (rightText.isNullOrEmpty().not()) {
            view.tv_confirm.text = rightText
        }

        view.tv_confirm.setOnClickListener(object : OnNoDoubleClickListener() {
            override fun onNoDoubleClick(v: View?) {
                onConfirmClickListener?.invoke()
                dismiss()
            }
        })
        view.tv_cancel.setOnClickListener(object : OnNoDoubleClickListener() {
            override fun onNoDoubleClick(v: View?) {
                onCancelClickListener?.invoke()
                dismiss()
            }
        })
    }

    class Builder {
        private var mConfirmDialog: PermissionDialog? = null
        var title: String = ""
        var content: String = ""
        var rightText: String = ""
        var cancelAble: Boolean = false

        fun setTitle(title: String): Builder {
            this.title = title
            return this
        }

        fun setContent(content: String): Builder {
            this.content = content
            return this
        }

        fun setRightText(rightText: String): Builder {
            this.rightText = rightText
            return this
        }

        fun setCancelAble(cancelAble: Boolean): Builder {
            this.cancelAble = cancelAble
            return this
        }

        fun build(): PermissionDialog? {
            if (this.mConfirmDialog == null) {
                this.mConfirmDialog = newInstance(this)
            }
            return this.mConfirmDialog
        }
    }


}
