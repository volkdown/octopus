package ru.volkdown.sample.features.single.presentation

import android.content.Context
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.arellomobile.mvp.presenter.InjectPresenter
import com.arellomobile.mvp.presenter.ProvidePresenter
import kotlinx.android.synthetic.main.fragment_single.*
import ru.terrakok.cicerone.Navigator
import ru.terrakok.cicerone.NavigatorHolder
import ru.terrakok.cicerone.android.support.SupportAppNavigator
import ru.volkdown.coreoctopus.utils.getFeatureIdentifier
import ru.volkdown.sample.App
import ru.volkdown.sample.R
import ru.volkdown.sample.base.BaseFragment
import ru.volkdown.sample.features.single.di.SingleComponent

class SingleFragment : BaseFragment<SinglePresenter>(), SingleView{

    private lateinit var component: SingleComponent

    override val layoutResId: Int = R.layout.fragment_single

    private val navigator: Navigator by lazy {
        SupportAppNavigator(requireActivity(), childFragmentManager, R.id.flContainer)
    }

    private lateinit var navigatorHolder: NavigatorHolder

    @InjectPresenter
    override lateinit var presenter: SinglePresenter

    @ProvidePresenter
    override fun providePresenter(): SinglePresenter {
        return component.getPresenter()
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        val applicationProvider = (requireActivity().application as App).getApplicationProvider()
        component = SingleComponent.build(applicationProvider, getFeatureIdentifier())
        component.inject(this)
        navigatorHolder = applicationProvider.provideNavigationContainer().getNavigatorHolder(getFeatureIdentifier().featureId)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        btnSendEvent.setOnClickListener { presenter.onSendEventToSampleFeatureClicked() }
    }

    override fun onResume() {
        super.onResume()
        navigatorHolder.setNavigator(navigator)
    }

    override fun onPause() {
        navigatorHolder.removeNavigator()
        super.onPause()
    }

    override fun showSampleFeatureMessage() {
        Toast.makeText(requireContext(), R.string.i_am_feature, Toast.LENGTH_LONG).show()
    }

    override fun showMessagePendingEvent() {
        Toast.makeText(requireContext(), R.string.this_pending_event, Toast.LENGTH_LONG).show()
    }

    companion object{

        fun newInstance() = SingleFragment()
    }
}