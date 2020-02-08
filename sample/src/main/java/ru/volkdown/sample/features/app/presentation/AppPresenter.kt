package ru.volkdown.sample.features.app.presentation

import ru.volkdown.coreoctopus.FeatureIdentifier
import ru.volkdown.sample.base.BasePresenter
import ru.volkdown.sample.base.BaseView
import ru.volkdown.sample.features.app.api.events.ShowMessagePendingEventFromApp
import ru.volkdown.sample.features.main.api.MainFeatureApi
import ru.volkdown.sample.features.main.api.events.ShowMultiImplementationFromMainEvent
import ru.volkdown.sample.features.main.api.events.ShowSingleImplementationFromMainEvent
import ru.volkdown.sample.features.multi.api.MultiFeatureApi
import ru.volkdown.sample.features.single.api.SingleFeatureApi
import ru.volkdown.sample.navigation.NavigationContainer
import javax.inject.Inject

class AppPresenter @Inject constructor(
    navigationContainer: NavigationContainer,
    featureIdentifier: FeatureIdentifier,
    private val mainFeatureApi: MainFeatureApi,
    private val singleFeatureApi: SingleFeatureApi,
    private val multiFeatureApi: MultiFeatureApi
) : BasePresenter<BaseView>(featureIdentifier) {

    private val router = navigationContainer.getRouter(featureIdentifier.featureId)

    override fun onFirstViewAttach() {
        super.onFirstViewAttach()
        registerFeatures(mainFeatureApi, singleFeatureApi, multiFeatureApi)
        router.newRootScreen(mainFeatureApi.getScreen(this))
        subscribeToMainFeatureApi()
    }

    private fun subscribeToMainFeatureApi(){
        subscribeToFeatureEvents(mainFeatureApi) {
            when(it){
                is ShowSingleImplementationFromMainEvent -> {
                    if(it.isNeedShowPendingEvent){
                        singleFeatureApi.sendEvents(this, ShowMessagePendingEventFromApp())
                    }
                    router.navigateTo(singleFeatureApi.getScreen(this))
                }
                is ShowMultiImplementationFromMainEvent -> {
                    if(it.isNeedShowPendingEvent){
                        multiFeatureApi.sendEvents(this, ShowMessagePendingEventFromApp())
                    }
                    router.navigateTo(multiFeatureApi.getScreen(this))
                }
            }
        }
    }
}