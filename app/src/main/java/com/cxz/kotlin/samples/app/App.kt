package com.cxz.kotlin.samples.app

import android.content.Context
import android.support.multidex.MultiDex
import com.cxz.kotlin.baselibs.app.BaseApp
import kotlin.properties.Delegates

/**
 * @author admin
 * @date 2018/11/21
 * @desc
 */
class App : BaseApp() {

    companion object {
        var instance: Context by Delegates.notNull()
            private set
    }

    override fun onCreate() {
        super.onCreate()
        instance = this
    }

    override fun attachBaseContext(base: Context?) {
        super.attachBaseContext(base)
        MultiDex.install(this)
    }

}