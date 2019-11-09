package com.cxz.kotlin.baselibs.app

import android.app.Application
import kotlin.properties.Delegates

/**
 * @author chenxz
 * @date 2018/11/18
 * @desc BaseApp
 */
open class BaseApp : Application() {

    companion object {
        var instance: Application by Delegates.notNull()
            private set
    }

    override fun onCreate() {
        super.onCreate()
        instance = this
    }

}