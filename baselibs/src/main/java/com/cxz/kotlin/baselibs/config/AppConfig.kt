package com.cxz.kotlin.baselibs.config

import android.app.Application

/**
 * @author chenxz
 * @date 2019/11/1
 * @desc 用来初始化项目所需要的配置
 */
object AppConfig {

    const val TAG = "KotlinMVP"

    var debug = false

    private var application: Application? = null

    /**
     * Init, it must be call before used .
     */
    fun init(application: Application) {
        this.application = application
    }

    fun getApplication(): Application {
        if (application == null) {
            throw RuntimeException("Please init in Application#onCreate first.")
        }
        return application!!
    }

    fun openDebug() {
        debug = true
    }

}