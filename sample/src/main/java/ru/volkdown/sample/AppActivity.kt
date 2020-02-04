package ru.volkdown.sample

import ru.volkdown.sample.base.BaseActivity
import ru.volkdown.sample.base.BasePresenter
import ru.volkdown.sample.base.BaseView

class AppActivity : BaseActivity<BasePresenter<BaseView>>() {

    override val layoutResId: Int = R.layout.activity_app

    override val presenter: BasePresenter<BaseView>? = null

    override fun providePresenter(): BasePresenter<BaseView>? = null
}