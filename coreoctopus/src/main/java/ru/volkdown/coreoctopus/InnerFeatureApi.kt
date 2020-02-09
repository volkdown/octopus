package ru.volkdown.coreoctopus

import androidx.annotation.MainThread

interface InnerFeatureApi {

    @MainThread
    fun sendInnerEvents(featureOwner: FeatureOwner, vararg events: BaseFeatureEvent)

    @MainThread
    fun sendInnerEventsToOwners(vararg events: BaseFeatureEvent)

    @MainThread
    fun registerSubscriber(featureOwner: FeatureOwner, subscriber: InnerFeatureSubscriber)

    @MainThread
    fun unregisterSubscriber(subscriber: InnerFeatureSubscriber)

    @MainThread
    fun newInnerSubscriber(featureOwner: FeatureOwner): InnerFeatureSubscriber
}