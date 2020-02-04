package ru.volkdown.sample.features.app.presentation

import android.content.Context
import com.arellomobile.mvp.presenter.InjectPresenter
import com.arellomobile.mvp.presenter.ProvidePresenter
import ru.terrakok.cicerone.Navigator
import ru.terrakok.cicerone.NavigatorHolder
import ru.terrakok.cicerone.android.support.SupportAppNavigator
import ru.volkdown.coreoctopus.utils.getFeatureIdentifier
import ru.volkdown.sample.App
import ru.volkdown.sample.R
import ru.volkdown.sample.base.BaseFragment
import ru.volkdown.sample.base.BaseView
import ru.volkdown.sample.features.app.di.AppComponent
import ru.volkdown.sample.navigation.NavigationContainer
import javax.inject.Inject

class AppFragment : BaseFragment<AppPresenter>(), BaseView {

    private lateinit var component: AppComponent

    private val navigator: Navigator by lazy {
        SupportAppNavigator(activity, childFragmentManager, R.id.flContent)
    }

    override val layoutResId: Int = R.layout.fragment_app

    @Inject
    lateinit var navigationContainer: NavigationContainer

    private val navigatorHolder: NavigatorHolder by lazy {
        val featureId = getFeatureIdentifier().featureId
        navigationContainer.getNavigatorHolder(featureId)
    }

    @InjectPresenter
    override lateinit var presenter: AppPresenter

    @ProvidePresenter
    override fun providePresenter(): AppPresenter {
        return component.getPresenter()
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        val applicationProvider = (requireActivity().application as App).getApplicationProvider()
        component = AppComponent.build(applicationProvider, getFeatureIdentifier())
        component.inject(this)
    }

    override fun onResume() {
        super.onResume()
        navigatorHolder.setNavigator(navigator)
    }

    override fun onPause() {
        navigatorHolder.removeNavigator()
        super.onPause()
    }
}