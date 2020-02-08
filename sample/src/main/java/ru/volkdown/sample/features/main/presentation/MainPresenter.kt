package ru.volkdown.sample.features.main.presentation

import com.arellomobile.mvp.InjectViewState
import ru.volkdown.coreoctopus.FeatureIdentifier
import ru.volkdown.sample.base.BasePresenter
import ru.volkdown.sample.base.BaseView
import ru.volkdown.sample.features.main.api.MainInnerFeatureApi
import ru.volkdown.sample.features.main.api.events.ShowMultiImplementationEvent
import ru.volkdown.sample.features.main.api.events.ShowSingleImplementationEvent
import javax.inject.Inject

@InjectViewState
class MainPresenter @Inject constructor(
    featureIdentifier: FeatureIdentifier,
    private val mainInnerFeatureApi: MainInnerFeatureApi
) : BasePresenter<BaseView>(featureIdentifier) {

    fun onShowSingleFeatureClicked() {
        mainInnerFeatureApi.sendInnerEvents(this, ShowSingleImplementationEvent())
    }

    fun onShowMultiFeatureClicked() {
        mainInnerFeatureApi.sendInnerEvents(this, ShowMultiImplementationEvent())
    }
}