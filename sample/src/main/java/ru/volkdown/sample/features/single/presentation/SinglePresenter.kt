package ru.volkdown.sample.features.single.presentation

import com.arellomobile.mvp.InjectViewState
import ru.volkdown.coreoctopus.FeatureIdentifier
import ru.volkdown.sample.R
import ru.volkdown.sample.base.BasePresenter
import ru.volkdown.sample.features.app.api.events.ShowMessagePendingEventFromApp
import ru.volkdown.sample.features.samplefeature.api.SampleFeatureApi
import ru.volkdown.sample.features.samplefeature.api.events.IAmFeatureFromSampleFeatureEvent
import ru.volkdown.sample.features.single.api.SingleInnerFeatureApi
import ru.volkdown.sample.features.single.api.events.ShowMessageFromSingleEvent
import ru.volkdown.sample.navigation.NavigationContainer
import javax.inject.Inject

@InjectViewState
class SinglePresenter @Inject constructor(private val featureIdentifier: FeatureIdentifier,
                                          private val navigationContainer: NavigationContainer,
                                          private val innerFeatureApi: SingleInnerFeatureApi,
                                          private val sampleFeatureApi: SampleFeatureApi): BasePresenter<SingleView>(featureIdentifier){

    private val route = navigationContainer.getRouter(featureIdentifier.featureId)

    override fun onFirstViewAttach() {
        super.onFirstViewAttach()
        registerFeatures(sampleFeatureApi)
        subscribeToSampleFeatureEvents()
        subscribeToInnerFeatureEvents()
        route.newRootScreen(sampleFeatureApi.getScreen(this))
    }

    private fun subscribeToInnerFeatureEvents(){
        subscribeToFeatureEvents(innerFeatureApi){
            if(it is ShowMessagePendingEventFromApp){
                viewState.showMessagePendingEvent()
            }
        }
    }

    private fun subscribeToSampleFeatureEvents(){
        subscribeToFeatureEvents(sampleFeatureApi){
            if(it is IAmFeatureFromSampleFeatureEvent){
                viewState.showSampleFeatureMessage()
            }
        }
    }

    override fun onDestroy() {
        navigationContainer.removeCicerone(featureIdentifier.featureId)
        super.onDestroy()
    }

    fun onSendEventToSampleFeatureClicked() {
        val event = ShowMessageFromSingleEvent(R.string.event_from_single_owner)
        sampleFeatureApi.sendEvents(this, event)
    }
}