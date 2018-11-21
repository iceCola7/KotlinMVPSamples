package com.cxz.kotlin.samples

import com.cxz.kotlin.baselibs.ext.ss
import com.cxz.kotlin.baselibs.mvp.BasePresenter

/**
 * @author admin
 * @date 2018/11/20
 * @desc
 */
class MainPresenter : BasePresenter<MainContract.Model, MainContract.View>(), MainContract.Presenter {

    override fun createModel(): MainContract.Model? = MainModel()

    override fun getData() {
        val data = mModel?.getData()
        mView?.showData(data.toString())
    }

    override fun getBanner() {

//        addDisposable(
//            mModel?.getBanners()
//                ?.subscribe({
//                    if (it.errorCode == 0) {
//                        mView?.showBanners(it.data)
//                    }
//                }, {
//                    ExceptionHandle.handleException(it)
//                })
//        )

        mModel?.getBanners()?.ss {
            mView?.showBanners(it.data)
        }

    }

}