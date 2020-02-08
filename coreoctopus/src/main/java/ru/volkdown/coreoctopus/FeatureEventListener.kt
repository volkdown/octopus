package ru.volkdown.coreoctopus

interface FeatureEventListener {

    fun handleEvent(event: BaseFeatureEvent)
}