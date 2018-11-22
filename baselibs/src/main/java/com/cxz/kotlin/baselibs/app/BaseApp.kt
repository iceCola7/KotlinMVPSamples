package com.cxz.kotlin.baselibs.app

import android.app.Application
import android.content.Context
import kotlin.properties.Delegates

/**
 * @author chenxz
 * @date 2018/11/18
 * @desc BaseApp
 */
abstract class BaseApp : Application() {

    companion object {
        var instance: Context by Delegates.notNull()
            private set
    }

    override fun onCreate() {
        super.onCreate()
        instance = this
    }

}