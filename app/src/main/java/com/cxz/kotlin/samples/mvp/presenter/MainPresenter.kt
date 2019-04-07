package com.cxz.kotlin.samples.mvp.presenter

import com.cxz.kotlin.baselibs.ext.ss
import com.cxz.kotlin.baselibs.ext.sss
import com.cxz.kotlin.baselibs.http.HttpStatus
import com.cxz.kotlin.baselibs.http.exception.ExceptionHandle
import com.cxz.kotlin.baselibs.mvp.BasePresenter
import com.cxz.kotlin.baselibs.rx.SchedulerUtils
import com.cxz.kotlin.samples.mvp.contract.MainContract
import com.cxz.kotlin.samples.mvp.model.MainModel

/**
 * @author admin
 * @date 2018/11/20
 * @desc
 */
class MainPresenter : BasePresenter<MainContract.Model, MainContract.View>(), MainContract.Presenter {

    override fun createModel(): MainContract.Model? = MainModel()

    override fun getBanner() {
        mView?.showLoading()
        addDisposable(mModel?.getBanners()
                ?.compose(SchedulerUtils.ioToMain())
                ?.subscribe({
                    mView?.hideLoading()
                    when {
                        it.errorCode == HttpStatus.SUCCESS -> mView?.showBanners(it.data)
                        it.errorCode == HttpStatus.TOKEN_INVALID -> {
                            // Token 过期，重新登录
                        }
                        else -> mView?.showError(it.errorMsg)
                    }
                }, {
                    mView?.hideLoading()
                    mView?.showDefaultMsg(ExceptionHandle.handleException(it))
                })
        )
    }

    override fun getBanner2() {
        mModel?.getBanners()?.ss(mModel, mView, onSuccess = {
            mView?.showBanners(it.data)
        })
    }

    override fun getBanner3() {
        addDisposable(mModel?.getBanners()?.sss(mView, onSuccess = {
            mView?.showBanners(it.data)
        }))
    }

    override fun login(username: String, password: String) {
        mModel?.login(username, password)?.ss(mModel, mView, onSuccess = {
            mView?.loginSuccess()
        })
    }

    override fun getBannerList() {
        mModel?.getBannerList()?.ss(mModel, mView, onSuccess = {
            mView?.showBannerList(it.data)
        })
    }

    override fun getCollectList(page: Int) {
        mModel?.getCollectList(page)?.ss(mModel, mView, onSuccess = {
            mView?.showCollectList(it.data)
        })
    }

    override fun logout() {
        mModel?.logout()?.ss(mModel, mView, onSuccess = {
            mView?.logoutSuccess()
        })
    }

}