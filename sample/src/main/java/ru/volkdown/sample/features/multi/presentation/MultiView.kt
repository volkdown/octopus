package ru.volkdown.sample.features.multi.presentation

import ru.volkdown.sample.base.BaseView

interface MultiView: BaseView {

    fun showMessagePendingEvent()

    fun showSampleFeatureMessage(featureId: String)
}