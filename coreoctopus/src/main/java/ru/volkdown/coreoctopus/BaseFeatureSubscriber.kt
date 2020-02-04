package ru.volkdown.coreoctopus

interface BaseFeatureSubscriber {

    val featureId: String

    fun handleEvent(event: BaseFeatureEvent)
}