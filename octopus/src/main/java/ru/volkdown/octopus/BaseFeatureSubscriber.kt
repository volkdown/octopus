package ru.volkdown.octopus

open class BaseFeatureSubscriber internal constructor(private val featureId: String) {

    fun handleEvent(event: BaseFeatureEvent){
        //Need handle events and send receivers
    }
}