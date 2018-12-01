package com.cxz.kotlin.samples.mvp.presenter

import com.cxz.kotlin.baselibs.mvp.BasePresenter
import com.cxz.kotlin.samples.mvp.contract.TestContract
import com.cxz.kotlin.samples.mvp.model.TestModel

/**
 * @author chenxz
 * @date 2018/12/1
 * @desc
 */
class TestPresenter : BasePresenter<TestContract.Model, TestContract.View>(), TestContract.Presenter {

    override fun createModel(): TestContract.Model? = TestModel()

}