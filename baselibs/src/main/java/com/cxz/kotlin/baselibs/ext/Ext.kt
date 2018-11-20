package com.cxz.kotlin.baselibs.ext

import android.util.Log

/**
 * @author chenxz
 * @date 2018/11/20
 * @desc
 */

fun Any.loge(content: String?) {
    Log.e(this.javaClass.simpleName, content ?: "")
}