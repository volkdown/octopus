package ru.volkdown.sample.features.samplefeature.presentation

import android.content.Context
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.arellomobile.mvp.presenter.InjectPresenter
import com.arellomobile.mvp.presenter.ProvidePresenter
import kotlinx.android.synthetic.main.fragment_sample_feature.*
import ru.volkdown.coreoctopus.utils.getFeatureIdentifier
import ru.volkdown.sample.App
import ru.volkdown.sample.R
import ru.volkdown.sample.base.BaseFragment
import ru.volkdown.sample.features.samplefeature.di.SampleFeatureComponent

class SampleFeatureFragment : BaseFragment<SampleFeaturePresenter>(), SampleFeatureView {

    private lateinit var component: SampleFeatureComponent

    override val layoutResId: Int = R.layout.fragment_sample_feature

    @InjectPresenter
    override lateinit var presenter: SampleFeaturePresenter

    @ProvidePresenter
    override fun providePresenter(): SampleFeaturePresenter {
        return component.getPresenter()
    }

    override fun showSingleOwnerMessage(messageRes: Int) {
        Toast.makeText(requireContext(), messageRes, Toast.LENGTH_LONG).show()
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        val applicationProvider = (requireActivity().application as App).getApplicationProvider()
        component = SampleFeatureComponent.build(applicationProvider, getFeatureIdentifier())
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        btnSendInnerEvent.setOnClickListener { presenter.onSendInnerEventClicked() }
    }

    override fun showMultiOwnerMessage(messageResId: Int, featureId: String) {
        Toast.makeText(requireContext(), getString(messageResId, featureId), Toast.LENGTH_LONG).show()
    }

    companion object {

        fun newInstance() = SampleFeatureFragment()
    }
}