package ru.volkdown.sample.features.main.presentation

import android.content.Context
import androidx.fragment.app.Fragment
import com.arellomobile.mvp.presenter.InjectPresenter
import com.arellomobile.mvp.presenter.ProvidePresenter
import ru.volkdown.octopus.R
import ru.volkdown.octopus.utils.getFeatureIdentifier
import ru.volkdown.sample.App
import ru.volkdown.sample.base.BaseFragment
import ru.volkdown.sample.base.BaseView
import ru.volkdown.sample.features.main.di.MainComponent

class MainFragment : BaseFragment<MainPresenter>(), BaseView{

    private lateinit var component: MainComponent

    override val layoutResId: Int = R.layout.fragment_main

    @InjectPresenter
    override lateinit var presenter: MainPresenter

    @ProvidePresenter
    override fun providePresenter(): MainPresenter {
        return component.getPresenter()
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        val applicationProvider = (requireActivity().application as App).getApplicationProvider()
        component = MainComponent.build(applicationProvider, getFeatureIdentifier())
        component.inject(this)
    }

    companion object{

        fun newInstance(): Fragment = MainFragment()
    }
}