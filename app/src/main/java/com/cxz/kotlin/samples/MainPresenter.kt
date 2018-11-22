package com.cxz.kotlin.samples

import com.cxz.kotlin.baselibs.ext.ss
import com.cxz.kotlin.baselibs.ext.sss
import com.cxz.kotlin.baselibs.http.exception.ErrorStatus
import com.cxz.kotlin.baselibs.http.exception.ExceptionHandle
import com.cxz.kotlin.baselibs.mvp.BasePresenter
import com.cxz.kotlin.baselibs.rx.SchedulerUtils

/**
 * @author admin
 * @date 2018/11/20
 * @desc
 */
class MainPresenter : BasePresenter<MainContract.Model, MainContract.View>(), MainContract.Presenter {

    override fun createModel(): MainContract.Model? = MainModel()

    override fun getBanner() {

        mView?.showLoading()
        addDisposable(
            mModel?.getBanners()
                ?.compose(SchedulerUtils.ioToMain())
                ?.subscribe({
                    if (it.errorCode == ErrorStatus.SUCCESS) {
                        mView?.showBanners(it.data)
                    } else if (it.errorCode == ErrorStatus.TOKEN_INVAILD) {
                        // Token 过期，重新登录
                    } else {
                        mView?.showError(it.errorMsg)
                    }
                    mView?.hideLoading()
                }, {
                    ExceptionHandle.handleException(it)
                    mView?.hideLoading()
                })
        )

    }

    override fun getBanner2() {
        mModel?.getBanners()?.ss(mModel, mView) {
            mView?.showBanners(it.data)
        }
    }

    override fun getBanner3() {
        addDisposable(
            mModel?.getBanners()?.sss(mView) {
                mView?.showBanners(it.data)
            }
        )
    }

}