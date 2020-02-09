package ru.volkdown.coreoctopus

import androidx.annotation.MainThread
import ru.volkdown.coreoctopus.utils.Threads.Companion.checkThreadIsMain
import ru.volkdown.coreoctopus.utils.generateFeatureId
import java.util.LinkedList
import java.util.Queue
import kotlin.collections.set

open class BaseFeatureApi : FeatureApi, InnerFeatureApi {

    private val featureSubscribers: HashMap<FeatureSubscriberIdentifier, FeatureSubscriber> = HashMap()

    private val featureInnerSubscribers: HashMap<FeatureSubscriberIdentifier, InnerFeatureSubscriber> = HashMap()

    private val pendingEvents: HashMap<String, Queue<BaseFeatureEvent>> = HashMap()

    private val featureKeysByOwnerId: HashMap<String, String> = HashMap()

    override fun unregisterOwner(owner: FeatureOwner) {
        checkThreadIsMain()
        val ownerId = owner.id
        if (featureKeysByOwnerId.containsKey(ownerId)) {
            return
        }
        featureKeysByOwnerId.remove(ownerId)
    }

    override fun registerOwner(owner: FeatureOwner) {
        checkThreadIsMain()
        val ownerId = owner.id
        if (featureKeysByOwnerId.containsKey(ownerId)) {
            return
        }
        featureKeysByOwnerId[ownerId] = generateFeatureId()
    }

    override fun getFeatureName(): String = ""

    @MainThread
    override fun registerSubscriber(featureOwner: FeatureOwner, subscriber: FeatureSubscriber) {
        checkThreadIsMain()
        val ownerId = featureOwner.id
        if (!featureKeysByOwnerId.containsKey(ownerId)) {
            throw IllegalArgumentException("Owner must be registered")
        }
        val featureSubscriberIdentifier =
                FeatureSubscriberIdentifier(
                        subscriber.featureId,
                        ownerId
                )
        featureSubscribers[featureSubscriberIdentifier] = subscriber
    }

    @MainThread
    override fun unregisterSubscriber(subscriber: FeatureSubscriber) {
        checkThreadIsMain()
        val featureId = subscriber.featureId
        val featureSubscriberIdentifier = featureSubscribers.keys.find {
            it.featureId == featureId
        }
        featureSubscriberIdentifier?.let {
            featureSubscribers.remove(it)
        }
        pendingEvents.remove(featureId)
    }

    @MainThread
    override fun unregisterSubscriber(subscriber: InnerFeatureSubscriber) {
        checkThreadIsMain()
        val featureId = subscriber.featureId
        val featureSubscriberIdentifier = featureInnerSubscribers.entries.find {
            it.key.featureId == featureId
        }?.key
        featureSubscriberIdentifier?.let {
            featureInnerSubscribers.remove(it)
        }
    }

    @MainThread
    override fun registerSubscriber(
            featureOwner: FeatureOwner,
            subscriber: InnerFeatureSubscriber
    ) {
        checkThreadIsMain()
        val featureId = subscriber.featureId
        featureInnerSubscribers[FeatureSubscriberIdentifier(featureId, featureOwner.id)] =
                subscriber
        pendingEvents[featureId]?.forEach {
            subscriber.handleEvent(it)
        }
        pendingEvents.clear()
    }

    @MainThread
    override fun sendEvents(featureId: String, vararg events: BaseFeatureEvent) {
        checkThreadIsMain()
        val entry = featureInnerSubscribers.entries.find {
            it.key.featureId == featureId
        }
        val featureInnerSubscriber = entry?.value
        if (featureInnerSubscriber == null) {
            pendingEvents.getOrPut(featureId, { LinkedList() }).addAll(events)
        } else {
            events.forEach { featureInnerSubscriber.handleEvent(it) }
        }
    }

    override fun sendEvents(featureOwner: FeatureOwner, vararg events: BaseFeatureEvent) {
        checkThreadIsMain()
        val featureId = featureKeysByOwnerId[featureOwner.id]
                ?: throw IllegalArgumentException("Owner must be registered or need send event by feature id")
        sendEvents(featureId, *events)
    }

    override fun newSubscriber(featureOwner: FeatureOwner): FeatureSubscriber {
        checkThreadIsMain()
        val entry = featureSubscribers.entries.find {
            it.key.ownerId == featureOwner.id
        }
        var featureSubscriber = entry?.value
        if (featureSubscriber == null) {
            val featureId = featureKeysByOwnerId[featureOwner.id] ?: throw IllegalArgumentException(
                    "Owner must be registered"
            )
            featureSubscriber = newSubscriber(featureId)
        }
        return featureSubscriber
    }

    @MainThread
    override fun sendInnerEvents(featureOwner: FeatureOwner, vararg events: BaseFeatureEvent) {
        checkThreadIsMain()
        val featureId = featureOwner.id

        val featureSubscriberIdentifier = featureSubscribers.entries.find {
            it.key.featureId == featureId
        }?.key
        val featureSubscriber = featureSubscribers[featureSubscriberIdentifier]

        featureSubscriber?.let { subscriber -> events.forEach { subscriber.handleEvent(it) } }
    }

    override fun sendInnerEventsToOwners(vararg events: BaseFeatureEvent) {
        checkThreadIsMain()
        val uniqueOwners = featureSubscribers.keys
                .map { it.ownerId }
                .toSet()
        val subscribersList = featureSubscribers.toList()
        uniqueOwners.forEach {ownerId ->
            val firstSubscriberForOwner = subscribersList.find { it.first.ownerId == ownerId }?.second
            firstSubscriberForOwner?.let { subscriber -> events.forEach { subscriber.handleEvent(it) } }
        }
    }

    @MainThread
    override fun newInnerSubscriber(featureOwner: FeatureOwner): InnerFeatureSubscriber {
        checkThreadIsMain()
        val ownerId = featureOwner.id
        val entry = featureInnerSubscribers.entries.find {
            it.key.ownerId == ownerId
        }
        var innerFeatureSubscriber = entry?.value
        if (innerFeatureSubscriber == null) {
            //Inner feature always has id as owner id
            innerFeatureSubscriber = InnerFeatureSubscriber(ownerId)
        }
        return innerFeatureSubscriber
    }

    @MainThread
    override fun newSubscriber(featureId: String): FeatureSubscriber {
        checkThreadIsMain()
        return FeatureSubscriber(featureId)
    }

    protected fun getFeatureIdByOwner(featureOwner: FeatureOwner): String {
        return featureKeysByOwnerId[featureOwner.id]
                ?: throw IllegalArgumentException("Owner must be registered")
    }

    private data class FeatureSubscriberIdentifier(val featureId: String, val ownerId: String)

    private data class FeatureOwnerImpl(override val id: String): FeatureOwner
}