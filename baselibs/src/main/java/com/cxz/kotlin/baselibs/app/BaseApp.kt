package com.cxz.kotlin.baselibs.app

import android.app.Activity
import android.app.Application
import android.content.Context
import android.os.Bundle
import android.support.multidex.MultiDex
import com.alibaba.android.arouter.launcher.ARouter
import com.cxz.kotlin.baselibs.BuildConfig
import com.cxz.kotlin.baselibs.utils.NLog
import com.squareup.leakcanary.LeakCanary
import com.squareup.leakcanary.RefWatcher
import kotlin.properties.Delegates

/**
 * @author chenxz
 * @date 2018/11/18
 * @desc BaseApp
 */
open class BaseApp : Application() {

    private var refWatcher: RefWatcher? = null

    companion object {

        @JvmField
        val TAG = "BaseApp"

        var instance: Context by Delegates.notNull()
            private set

        fun getRefWatcher(context: Context): RefWatcher? {
            val app = context.applicationContext as BaseApp
            return app.refWatcher
        }

    }

    override fun onCreate() {
        super.onCreate()
        instance = this
        refWatcher = setupLeakCanary()
        registerActivityLifecycleCallbacks(mActivityLifecycleCallbacks)
        initRouter()
    }

    private fun initRouter() {
        if (BuildConfig.DEBUG) {
            ARouter.openLog()
            ARouter.openDebug()
        }
        ARouter.init(this)
    }

    override fun attachBaseContext(base: Context?) {
        super.attachBaseContext(base)
        MultiDex.install(this)
    }

    private fun setupLeakCanary(): RefWatcher {
        return if (LeakCanary.isInAnalyzerProcess(this)) {
            RefWatcher.DISABLED
        } else LeakCanary.install(this)
    }

    private val mActivityLifecycleCallbacks = object : Application.ActivityLifecycleCallbacks {
        override fun onActivityCreated(activity: Activity, savedInstanceState: Bundle?) {
            NLog.d(TAG, "onCreated: " + activity.componentName.className)
        }

        override fun onActivityStarted(activity: Activity) {
            NLog.d(TAG, "onStart: " + activity.componentName.className)
        }

        override fun onActivityResumed(activity: Activity) {

        }

        override fun onActivityPaused(activity: Activity) {

        }

        override fun onActivityStopped(activity: Activity) {

        }

        override fun onActivitySaveInstanceState(activity: Activity, outState: Bundle) {

        }

        override fun onActivityDestroyed(activity: Activity) {
            NLog.d(TAG, "onDestroy: " + activity.componentName.className)
        }
    }
}