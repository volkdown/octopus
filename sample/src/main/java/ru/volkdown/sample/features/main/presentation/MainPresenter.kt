package ru.volkdown.sample.features.main.presentation

import com.arellomobile.mvp.InjectViewState
import ru.volkdown.coreoctopus.FeatureIdentifier
import ru.volkdown.sample.base.BasePresenter
import ru.volkdown.sample.base.BaseView
import ru.volkdown.sample.features.main.api.MainInnerFeatureApi
import ru.volkdown.sample.features.main.api.events.ShowMultiImplementationFromMainEvent
import ru.volkdown.sample.features.main.api.events.ShowSingleImplementationFromMainEvent
import javax.inject.Inject

@InjectViewState
class MainPresenter @Inject constructor(
    featureIdentifier: FeatureIdentifier,
    private val mainInnerFeatureApi: MainInnerFeatureApi
) : BasePresenter<BaseView>(featureIdentifier) {

    private var isPendingEvent: Boolean = false

    fun onChangePendingEventStatus(status: Boolean){
        isPendingEvent = status
    }

    fun onShowSingleFeatureClicked() {
        mainInnerFeatureApi.sendInnerEvents(this, ShowSingleImplementationFromMainEvent(isPendingEvent))
    }

    fun onShowMultiFeatureClicked() {
        mainInnerFeatureApi.sendInnerEvents(this, ShowMultiImplementationFromMainEvent(isPendingEvent))
    }
}