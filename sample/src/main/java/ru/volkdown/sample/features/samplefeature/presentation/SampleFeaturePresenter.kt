package ru.volkdown.sample.features.samplefeature.presentation

import com.arellomobile.mvp.InjectViewState
import ru.volkdown.coreoctopus.FeatureIdentifier
import ru.volkdown.sample.base.BasePresenter
import ru.volkdown.sample.features.multi.api.events.ShowMessageFromMultiEvent
import ru.volkdown.sample.features.samplefeature.api.SampleInnerFeatureApi
import ru.volkdown.sample.features.samplefeature.api.events.IAmFeatureFromSampleFeatureEvent
import ru.volkdown.sample.features.single.api.events.ShowMessageFromSingleEvent
import javax.inject.Inject

@InjectViewState
class SampleFeaturePresenter @Inject constructor(private val featureIdentifier: FeatureIdentifier,
                                                 private val innerFeatureApi: SampleInnerFeatureApi): BasePresenter<SampleFeatureView>(featureIdentifier){


    override fun onFirstViewAttach() {
        super.onFirstViewAttach()
        subscribeInnerEvents()
    }

    private fun subscribeInnerEvents(){
        subscribeToFeatureEvents(innerFeatureApi) {
            when(it){
                is ShowMessageFromSingleEvent -> viewState.showSingleOwnerMessage(it.messageResId)
                is ShowMessageFromMultiEvent -> viewState.showMultiOwnerMessage(it.messageResId, it.featureId)
            }
        }
    }

    fun onSendInnerEventClicked() {
        innerFeatureApi.sendInnerEvents(this, IAmFeatureFromSampleFeatureEvent(featureIdentifier.featureId))
    }
}