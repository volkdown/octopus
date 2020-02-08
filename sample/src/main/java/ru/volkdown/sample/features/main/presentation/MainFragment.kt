package ru.volkdown.sample.features.main.presentation

import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.arellomobile.mvp.presenter.InjectPresenter
import com.arellomobile.mvp.presenter.ProvidePresenter
import kotlinx.android.synthetic.main.fragment_main.*
import ru.volkdown.coreoctopus.utils.getFeatureIdentifier
import ru.volkdown.sample.App
import ru.volkdown.sample.R
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

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        btnSingle.setOnClickListener { presenter.onShowSingleFeatureClicked() }
        btnMulti.setOnClickListener { presenter.onShowMultiFeatureClicked() }
        swPendingEvent.setOnCheckedChangeListener { _, isChecked -> presenter.onChangePendingEventStatus(isChecked) }
    }

    companion object{

        fun newInstance(): Fragment = MainFragment()
    }
}