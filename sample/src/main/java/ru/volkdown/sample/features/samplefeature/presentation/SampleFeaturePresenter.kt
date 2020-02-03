package ru.volkdown.sample.features.samplefeature.presentation

import ru.volkdown.octopus.FeatureIdentifier
import ru.volkdown.sample.base.BasePresenter
import ru.volkdown.sample.base.BaseView
import javax.inject.Inject

class SampleFeaturePresenter @Inject constructor(featureIdentifier: FeatureIdentifier): BasePresenter<BaseView>(featureIdentifier){

}