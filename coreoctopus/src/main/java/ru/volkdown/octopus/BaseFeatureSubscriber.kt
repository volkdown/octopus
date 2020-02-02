package ru.volkdown.octopus

interface BaseFeatureSubscriber {

    val featureId: String

    fun handleEvent(event: BaseFeatureEvent)
}