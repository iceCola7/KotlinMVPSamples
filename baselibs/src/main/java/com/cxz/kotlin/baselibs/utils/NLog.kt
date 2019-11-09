package com.cxz.kotlin.baselibs.utils

import android.util.Log

/**
 * @author chenxz
 * @date 2019/11/9
 * @desc 日志打印类
 */
object NLog {

    private const val TAG = "KotlinMVP"

    private var debug: Boolean = false

    fun openLog() {
        debug = true
    }

    fun i(tag: String, content: String) {
        if (debug) {
            Log.i(tag, content)
        }
    }

    fun i(content: String) {
        i(TAG, content)
    }

    fun v(tag: String, content: String) {
        if (debug) {
            Log.v(tag, content)
        }
    }

    fun v(content: String) {
        v(TAG, content)
    }

    fun d(tag: String, content: String) {
        if (debug) {
            Log.d(tag, content)
        }
    }

    fun d(content: String) {
        d(TAG, content)
    }

    fun w(tag: String, content: String) {
        if (debug) {
            Log.w(tag, content)
        }
    }

    fun w(content: String) {
        w(TAG, content)
    }

    fun e(tag: String, content: String) {
        if (debug) {
            Log.e(tag, content)
        }
    }

    fun e(content: String) {
        e(TAG, content)
    }

}