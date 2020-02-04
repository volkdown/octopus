package ru.volkdown.sample.features.main.presentation

import com.arellomobile.mvp.InjectViewState
import ru.volkdown.coreoctopus.FeatureIdentifier
import ru.volkdown.sample.base.BasePresenter
import ru.volkdown.sample.base.BaseView
import ru.volkdown.sample.features.main.api.MainInnerFeatureApi
import javax.inject.Inject

@InjectViewState
class MainPresenter @Inject constructor(
    featureIdentifier: FeatureIdentifier,
    private val mainInnerFeatureApi: MainInnerFeatureApi
) : BasePresenter<BaseView>(featureIdentifier) {

}