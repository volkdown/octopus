package ru.volkdown.sample.features.single.presentation

import android.content.Context
import com.arellomobile.mvp.presenter.InjectPresenter
import com.arellomobile.mvp.presenter.ProvidePresenter
import ru.volkdown.sample.App
import ru.volkdown.sample.R
import ru.volkdown.sample.base.BaseFragment
import ru.volkdown.sample.base.BaseView
import ru.volkdown.sample.features.single.di.SingleComponent

class SingleFragment : BaseFragment<SinglePresenter>(), BaseView{

    private lateinit var component: SingleComponent

    override val layoutResId: Int = R.layout.fragment_single

    @InjectPresenter
    override lateinit var presenter: SinglePresenter

    @ProvidePresenter
    override fun providePresenter(): SinglePresenter {
        return component.getPresenter()
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        val applicationProvider = (requireActivity().application as App).getApplicationProvider()
        component = SingleComponent.build(applicationProvider)
        component.inject(this)
    }

    companion object{

        fun newInstance() = SingleFragment()
    }
}