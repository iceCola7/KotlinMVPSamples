package com.cxz.kotlin.baselibs.http

import com.cxz.kotlin.baselibs.BuildConfig
import com.cxz.kotlin.baselibs.app.BaseApp
import com.cxz.kotlin.baselibs.http.constant.HttpConstant
import com.cxz.kotlin.baselibs.http.interceptor.CacheInterceptor
import com.cxz.kotlin.baselibs.http.interceptor.HeaderInterceptor
import com.cxz.kotlin.baselibs.http.interceptor.SaveCookieInterceptor
import okhttp3.Cache
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.moshi.MoshiConverterFactory
import java.io.File
import java.util.concurrent.TimeUnit

/**
 * @author admin
 * @date 2018/11/21
 * @desc RetrofitFactory
 */
abstract class RetrofitFactory<T> {

    private var mBaseUrl = ""
    private var retrofit: Retrofit? = null
    var service: T

    abstract fun baseUrl(): String
    abstract fun getService(): Class<T>

    init {
        mBaseUrl = this.baseUrl()
        if (mBaseUrl.isEmpty()) {
            throw RuntimeException("base url can not be empty!")
        }
        service = getRetrofit()!!.create(this.getService())
    }

    /**
     * 获取 Retrofit
     */
    private fun getRetrofit(): Retrofit? {
        if (retrofit == null) {
            synchronized(RetrofitFactory::class.java) {
                if (retrofit == null) {
                    retrofit = Retrofit.Builder()
                        .baseUrl(mBaseUrl)  // baseUrl
                        .client(getOkHttpClient())
                        //.addConverterFactory(GsonConverterFactory.create())
                        .addConverterFactory(MoshiConverterFactory.create())
                        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                        .build()
                }
            }
        }
        return retrofit
    }

    /**
     * 获取 OkHttpClient
     */
    private fun getOkHttpClient(): OkHttpClient {
        val builder = OkHttpClient().newBuilder()
        val httpLoggingInterceptor = HttpLoggingInterceptor()
        if (BuildConfig.DEBUG) {
            httpLoggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
        } else {
            httpLoggingInterceptor.level = HttpLoggingInterceptor.Level.NONE
        }

        //设置 请求的缓存的大小跟位置
        val cacheFile = File(BaseApp.instance.cacheDir, "cache")
        val cache = Cache(cacheFile, HttpConstant.MAX_CACHE_SIZE)

        builder.run {
            addInterceptor(httpLoggingInterceptor)
            addInterceptor(HeaderInterceptor())
            addInterceptor(SaveCookieInterceptor())
            addInterceptor(CacheInterceptor())
            cache(cache)  //添加缓存
            connectTimeout(HttpConstant.DEFAULT_TIMEOUT, TimeUnit.SECONDS)
            readTimeout(HttpConstant.DEFAULT_TIMEOUT, TimeUnit.SECONDS)
            writeTimeout(HttpConstant.DEFAULT_TIMEOUT, TimeUnit.SECONDS)
            retryOnConnectionFailure(true) // 错误重连
            // cookieJar(CookieManager())
        }
        return builder.build()
    }

}