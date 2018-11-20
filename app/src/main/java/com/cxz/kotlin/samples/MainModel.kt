package com.cxz.kotlin.samples

import com.cxz.kotlin.baselibs.mvp.BaseModel

/**
 * @author admin
 * @date 2018/11/20
 * @desc
 */
class MainModel : BaseModel(), MainContract.Model {

    override fun getData(): String {
        return """{"data":{"id":"1","name":"Tom"}}"""
    }

}