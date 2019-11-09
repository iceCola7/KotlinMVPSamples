package com.cxz.kotlin.baselibs.http.interceptor

import com.cxz.kotlin.baselibs.http.constant.HttpConstant
import com.cxz.kotlin.baselibs.utils.Preference
import okhttp3.Interceptor
import okhttp3.Response

/**
 * @author chenxz
 * @date 2018/9/26
 * @desc CookieInterceptor: 保存 Cookie
 */
class CookieInterceptor : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
        val response = chain.proceed(request)
        val requestUrl = request.url().toString()
        val domain = request.url().host()

        // set-cookie maybe has multi, login to save cookie
        if (
        //(requestUrl.contains(HttpConstant.SAVE_USER_LOGIN_KEY) || requestUrl.contains(HttpConstant.SAVE_USER_REGISTER_KEY)) &&
            response.headers(HttpConstant.SET_COOKIE_KEY).isNotEmpty()
        ) {
            val cookies = response.headers(HttpConstant.SET_COOKIE_KEY)
            val cookie = encodeCookie(cookies)
            saveCookie(requestUrl, domain, cookie)
        }
        // else if (requestUrl.contains(HttpConstant.REMOVE_USER_LOGOUT_KEY) && domain.isNotEmpty()) {
        // remove cookie
        // Preference.clearPreference(domain)
        // }
        return response
    }

    private fun encodeCookie(cookies: List<String>): String {
        val sb = StringBuilder()
        val set = HashSet<String>()
        cookies.map { cookie ->
            cookie.split(";".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()
        }.forEach {
            it.filterNot { set.contains(it) }.forEach { set.add(it) }
        }
        val ite = set.iterator()
        while (ite.hasNext()) {
            val cookie = ite.next()
            sb.append(cookie).append(";")
        }
        val last = sb.lastIndexOf(";")
        if (sb.length - 1 == last) {
            sb.deleteCharAt(last)
        }
        return sb.toString()
    }

    private fun saveCookie(url: String?, domain: String?, cookies: String) {
        url ?: return
        var spUrl: String by Preference(url, cookies)
        @Suppress("UNUSED_VALUE")
        spUrl = cookies
        domain ?: return
        var spDomain: String by Preference(domain, cookies)
        @Suppress("UNUSED_VALUE")
        spDomain = cookies
    }

}