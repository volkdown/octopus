package ru.volkdown.sample.features.single.presentation

import ru.volkdown.sample.base.BaseView

interface SingleView : BaseView{

    fun showSampleFeatureMessage()

    fun showMessagePendingEvent()
}