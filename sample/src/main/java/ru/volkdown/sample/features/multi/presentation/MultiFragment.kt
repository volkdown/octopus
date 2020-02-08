package ru.volkdown.sample.features.multi.presentation

import android.content.Context
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.arellomobile.mvp.presenter.InjectPresenter
import com.arellomobile.mvp.presenter.ProvidePresenter
import kotlinx.android.synthetic.main.fragment_multi.*
import ru.terrakok.cicerone.Navigator
import ru.terrakok.cicerone.NavigatorHolder
import ru.terrakok.cicerone.android.support.SupportAppNavigator
import ru.volkdown.coreoctopus.utils.getFeatureIdentifier
import ru.volkdown.sample.App
import ru.volkdown.sample.R
import ru.volkdown.sample.base.BaseFragment
import ru.volkdown.sample.features.multi.di.MultiComponent
import ru.volkdown.sample.features.multi.presentation.MultiPresenter.Companion.SAMPLE_FEATURE_1_KEY
import ru.volkdown.sample.features.multi.presentation.MultiPresenter.Companion.SAMPLE_FEATURE_2_KEY

class MultiFragment : BaseFragment<MultiPresenter>(), MultiView {

    private lateinit var component: MultiComponent

    override val layoutResId: Int = R.layout.fragment_multi

    private val navigator1: Navigator by lazy {
        SupportAppNavigator(requireActivity(), childFragmentManager, R.id.flFeature1)
    }

    private val navigator2: Navigator by lazy {
        SupportAppNavigator(requireActivity(), childFragmentManager, R.id.flFeature2)
    }

    private lateinit var navigatorHolder1: NavigatorHolder

    private lateinit var navigatorHolder2: NavigatorHolder

    @InjectPresenter
    override lateinit var presenter: MultiPresenter

    @ProvidePresenter
    override fun providePresenter(): MultiPresenter {
        return component.getPresenter()
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        val applicationProvider = (requireActivity().application as App).getApplicationProvider()
        component = MultiComponent.build(applicationProvider, getFeatureIdentifier())
        navigatorHolder1 = applicationProvider.provideNavigationContainer().getNavigatorHolder(SAMPLE_FEATURE_1_KEY)
        navigatorHolder2 = applicationProvider.provideNavigationContainer().getNavigatorHolder(SAMPLE_FEATURE_2_KEY)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        btnSendEventToFeature1.setOnClickListener {
            presenter.onSentEventToFeature1Clicked()
        }
        btnSendEventToFeature2.setOnClickListener {
            presenter.onSentEventToFeature2Clicked()
        }
    }

    override fun onResume() {
        super.onResume()
        navigatorHolder1.setNavigator(navigator1)
        navigatorHolder2.setNavigator(navigator2)
    }

    override fun onPause() {
        navigatorHolder1.setNavigator(navigator1)
        navigatorHolder2.setNavigator(navigator2)
        super.onPause()
    }

    override fun showMessagePendingEvent() {
        Toast.makeText(requireContext(), R.string.this_pending_event, Toast.LENGTH_LONG).show()
    }

    override fun showSampleFeatureMessage(featureId: String) {
        Toast.makeText(requireContext(), getString(R.string.i_am_feature_with_id, featureId), Toast.LENGTH_LONG).show()
    }

    companion object {

        fun newInstance(): Fragment = MultiFragment()
    }
}