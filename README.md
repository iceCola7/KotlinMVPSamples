# Kotlin版MVP框架

### 简介

> 快速搭建 Kotlin+MVP+RxJava+Retrofit+EventBus 的框架，方便快速开发新项目、减少开发成本，让所写的代码更加简洁，让开发者只需要关注业务的实现。

### 代码结构

![](/art/base.png)

### 如何使用？

> 只需要五步就可以实现MVP架构。

##### 第一步：导入baselibs库

##### 第二步：定义一个Contract

需要定义一个 `Contract` 接口，来抽象 `View`、`Presenter`、`Model` 的方法，并且它们需要分别继承 `IView`、`IPresenter`、`IModel` 接口。
> 案例： MainContract

```
interface MainContract {

    interface View : IView {
        fun showBanners(banners: MutableList<Banner>)
    }

    interface Presenter : IPresenter<View> {
        fun getBanner2()
    }

    interface Model : IModel {
        fun getBanners(): Observable<HttpResult<MutableList<Banner>>>
    }

}
```

##### 第三步：创建一个Model接口的实现类，需要继承 BaseModel 类 并实现Model接口
> 案例： MainModel

```
class MainModel : BaseModel(), MainContract.Model {

    override fun getBanners(): Observable<HttpResult<MutableList<Banner>>> {
        return MainRetrofit.service.getHomeBanner()
    }

}
```

##### 第四步：创建一个Presenter接口的实现类，需要继承 BasePresenter<M : IModel, V : IView> 并实现 MainContract.Presenter 接口

> 案例： MainPresenter

```
class MainPresenter : BasePresenter<MainContract.Model, MainContract.View>(), MainContract.Presenter {

    override fun createModel(): MainContract.Model? = MainModel()

    override fun getBanner2() {
        mModel?.getBanners()?.ss(mModel, mView) {
            mView?.showBanners(it.data)
        }
    }

}
```

##### 第五步：创建一个View的接口实现类，这是一个Activity或者Fragment，需要继承BaseMvpActivity<in V : IView, P : IPresenter<V>> 或者 BaseMvpFragment<in V : IView, P : IPresenter<V>> 并实现 View 接口
> 案例：MainActivity

```
class MainActivity : BaseMvpTitleActivity<MainContract.View, MainContract.Presenter>(), MainContract.View {

    override fun attachChildLayoutRes(): Int = R.layout.activity_main

    override fun createPresenter(): MainContract.Presenter = MainPresenter()

    override fun initView() {
    }

    override fun initData() {
    }

    override fun start() {
    }

    override fun showBanners(banners: MutableList<Banner>) {
        tv_result.text = banners.toString()
    }

}
```