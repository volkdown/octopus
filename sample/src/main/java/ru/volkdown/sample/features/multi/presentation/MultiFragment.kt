package ru.volkdown.sample.features.multi.presentation

import androidx.fragment.app.Fragment
import ru.volkdown.octopus.R
import ru.volkdown.sample.base.BaseFragment
import ru.volkdown.sample.base.BasePresenter
import ru.volkdown.sample.base.BaseView

class MultiFragment : BaseFragment<BasePresenter<BaseView>>() {

    override val layoutResId: Int = R.layout.fragment_multi

    override val presenter: BasePresenter<BaseView>?
        get() = TODO("not implemented") //To change initializer of created properties use File | Settings | File Templates.

    override fun providePresenter(): BasePresenter<BaseView>? {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    companion object {

        fun newInstance(): Fragment = MultiFragment()
    }
}