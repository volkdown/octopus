package ru.volkdown.octopus

import androidx.annotation.MainThread

interface FeatureApi {

    @MainThread
    fun sendEvents(featureId: String, vararg events: BaseFeatureEvent)

    @MainThread
    fun sendEvents(featureOwner: FeatureOwner, vararg events: BaseFeatureEvent)

    @MainThread
    fun registerOwner(owner: FeatureOwner)

    @MainThread
    fun unregisterOwner(owner: FeatureOwner)

    @MainThread
    fun registerSubscriber(featureOwner: FeatureOwner, subscriber: FeatureSubscriber)

    @MainThread
    fun unregisterSubscriber(subscriber: FeatureSubscriber)

    @MainThread
    fun newSubscriber(): FeatureSubscriber

    @MainThread
    fun newSubscriber(featureOwner: FeatureOwner): FeatureSubscriber

    fun getFeatureName(): String
}