package ru.volkdown.sample.features.app.presentation

import ru.volkdown.coreoctopus.FeatureIdentifier
import ru.volkdown.sample.base.BasePresenter
import ru.volkdown.sample.base.BaseView
import ru.volkdown.sample.features.main.api.MainFeatureApi
import ru.volkdown.sample.navigation.NavigationContainer
import javax.inject.Inject

class AppPresenter @Inject constructor(
    navigationContainer: NavigationContainer,
    featureIdentifier: FeatureIdentifier,
    private val mainFeatureApi: MainFeatureApi
) : BasePresenter<BaseView>(featureIdentifier) {

    private val router = navigationContainer.getRouter(featureIdentifier.featureId)

    override fun onFirstViewAttach() {
        super.onFirstViewAttach()
        registerFeatures(mainFeatureApi)
        router.newRootScreen(mainFeatureApi.getScreen(this))
    }
}