package ru.volkdown.sample.features.multi.presentation

import com.arellomobile.mvp.InjectViewState
import ru.volkdown.coreoctopus.BaseFeatureEvent
import ru.volkdown.coreoctopus.FeatureIdentifier
import ru.volkdown.coreoctopus.utils.generateFeatureId
import ru.volkdown.sample.R
import ru.volkdown.sample.base.BasePresenter
import ru.volkdown.sample.features.app.api.events.ShowMessagePendingEventFromApp
import ru.volkdown.sample.features.multi.api.MultiInnerFeatureApi
import ru.volkdown.sample.features.multi.api.events.ShowMessageFromMultiEvent
import ru.volkdown.sample.features.samplefeature.api.SampleFeatureApi
import ru.volkdown.sample.features.samplefeature.api.events.IAmFeatureFromSampleFeatureEvent
import ru.volkdown.sample.navigation.NavigationContainer
import javax.inject.Inject

@InjectViewState
class MultiPresenter @Inject constructor(featureIdentifier: FeatureIdentifier,
                                         private val innerFeatureApi: MultiInnerFeatureApi,
                                         private val sampleFeatureApi: SampleFeatureApi,
                                         private val navigationContainer: NavigationContainer) : BasePresenter<MultiView>(featureIdentifier){

    private val routerSampleFeature1 = navigationContainer.getRouter(SAMPLE_FEATURE_1_KEY)

    private val routerSampleFeature2 = navigationContainer.getRouter(SAMPLE_FEATURE_2_KEY)

    override fun onFirstViewAttach() {
        super.onFirstViewAttach()
        registerFeatures(sampleFeatureApi)
        subscribeToInnerFeatureEvents()
        subscribeToSampleFeatureEvents()
        routerSampleFeature1.newRootScreen(sampleFeatureApi.getScreen(generateFeatureId(this, SAMPLE_FEATURE_1_KEY)))
        routerSampleFeature2.newRootScreen(sampleFeatureApi.getScreen(generateFeatureId(this, SAMPLE_FEATURE_2_KEY)))
    }

    private fun subscribeToInnerFeatureEvents(){
        subscribeToFeatureEvents(innerFeatureApi){
            if(it is ShowMessagePendingEventFromApp){
                viewState.showMessagePendingEvent()
            }
        }
    }

    private fun subscribeToSampleFeatureEvents(){
        val eventListener: (BaseFeatureEvent) -> Unit = {
            if(it is IAmFeatureFromSampleFeatureEvent){
                viewState.showSampleFeatureMessage(it.featureId)
            }
        }
        subscribeToFeatureEvents(sampleFeatureApi, generateFeatureId(this, SAMPLE_FEATURE_1_KEY), eventListener)
        subscribeToFeatureEvents(sampleFeatureApi, generateFeatureId(this, SAMPLE_FEATURE_2_KEY), eventListener)
    }

    override fun onDestroy() {
        navigationContainer.removeCicerone(SAMPLE_FEATURE_1_KEY)
        navigationContainer.removeCicerone(SAMPLE_FEATURE_2_KEY)
        super.onDestroy()
    }

    fun onSentEventToFeature1Clicked() {
        val featureId = generateFeatureId(this, SAMPLE_FEATURE_1_KEY)
        sampleFeatureApi.sendEvents(featureId, ShowMessageFromMultiEvent(R.string.event_from_multi_owner, featureId))
    }

    fun onSentEventToFeature2Clicked() {
        val featureId = generateFeatureId(this, SAMPLE_FEATURE_2_KEY)
        sampleFeatureApi.sendEvents(featureId, ShowMessageFromMultiEvent(R.string.event_from_multi_owner, featureId))
    }

    companion object{

        const val SAMPLE_FEATURE_1_KEY = "SAMPLE_FEATURE_1_KEY"

        const val SAMPLE_FEATURE_2_KEY = "SAMPLE_FEATURE_2_KEY"

    }
}