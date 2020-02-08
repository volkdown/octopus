package ru.volkdown.sample.features.app.presentation

import android.os.Bundle
import com.arellomobile.mvp.presenter.InjectPresenter
import com.arellomobile.mvp.presenter.ProvidePresenter
import ru.terrakok.cicerone.Navigator
import ru.terrakok.cicerone.NavigatorHolder
import ru.terrakok.cicerone.android.support.SupportAppNavigator
import ru.volkdown.coreoctopus.utils.getFeatureIdentifier
import ru.volkdown.sample.App
import ru.volkdown.sample.R
import ru.volkdown.sample.base.BaseActivity
import ru.volkdown.sample.base.BasePresenter
import ru.volkdown.sample.base.BaseView
import ru.volkdown.sample.features.app.di.AppComponent

class AppActivity : BaseActivity<BasePresenter<BaseView>>(), BaseView {

    override val layoutResId: Int = R.layout.activity_app

    private val navigator: Navigator by lazy {
        SupportAppNavigator(this, supportFragmentManager, R.id.flAppContainer)
    }

    private lateinit var navigatorHolder: NavigatorHolder

    @InjectPresenter
    override lateinit var presenter: AppPresenter

    @ProvidePresenter
    override fun providePresenter(): AppPresenter {
        val applicationProvider = (application as App).getApplicationProvider()
        val component = AppComponent.build(applicationProvider, getFeatureIdentifier())
        return component.getPresenter()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val applicationProvider = (application as App).getApplicationProvider()
        navigatorHolder = applicationProvider.provideNavigationContainer().getNavigatorHolder(getFeatureIdentifier().featureId)
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