package com.cxz.kotlin.baselibs.rx.scheduler

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.schedulers.Schedulers

/**
 * Created by chenxz on 2018/4/21.
 */
class TrampolineMainScheduler<T> private constructor() :
    BaseScheduler<T>(Schedulers.trampoline(), AndroidSchedulers.mainThread())
