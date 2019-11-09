package com.cxz.kotlin.samples.app

import android.app.Activity
import android.content.Context
import android.os.Bundle
import android.support.multidex.MultiDex
import com.alibaba.android.arouter.launcher.ARouter
import com.cxz.kotlin.baselibs.BuildConfig
import com.cxz.kotlin.baselibs.app.BaseApp
import com.cxz.kotlin.baselibs.utils.NLog
import com.squareup.leakcanary.LeakCanary
import com.squareup.leakcanary.RefWatcher

/**
 * @author admin
 * @date 2018/11/21
 * @desc
 */
class App : BaseApp() {

    private val TAG = "App"

    private var refWatcher: RefWatcher? = null

    companion object {
        fun getRefWatcher(context: Context): RefWatcher? {
            val app = context.applicationContext as App
            return app.refWatcher
        }
    }

    override fun onCreate() {
        super.onCreate()
        initLeakCanary()
        initRouter()
    }

    private fun initLeakCanary() {
        refWatcher = if (LeakCanary.isInAnalyzerProcess(this))
            RefWatcher.DISABLED
        else LeakCanary.install(this)

        registerActivityLifecycleCallbacks(mActivityLifecycleCallbacks)
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

    private val mActivityLifecycleCallbacks = object : ActivityLifecycleCallbacks {
        override fun onActivityCreated(activity: Activity, savedInstanceState: Bundle?) {
            NLog.d(TAG, "onCreated: " + activity.componentName.className)
        }

        override fun onActivityStarted(activity: Activity) {
            NLog.d(TAG, "onStart: " + activity.componentName.className)
        }

        override fun onActivityResumed(activity: Activity) {
            NLog.d(TAG, "onResume: " + activity.componentName.className)
        }

        override fun onActivityPaused(activity: Activity) {
            NLog.d(TAG, "onPause: " + activity.componentName.className)
        }

        override fun onActivityStopped(activity: Activity) {
            NLog.d(TAG, "onStop: " + activity.componentName.className)
        }

        override fun onActivitySaveInstanceState(activity: Activity, outState: Bundle) {
            NLog.d(TAG, "onSaveInstanceState: " + activity.componentName.className)
        }

        override fun onActivityDestroyed(activity: Activity) {
            NLog.d(TAG, "onDestroy: " + activity.componentName.className)
        }
    }

}