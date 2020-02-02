package ru.volkdown.octopus

import androidx.annotation.MainThread
import ru.volkdown.octopus.utils.Threads.Companion.checkThreadIsMain
import ru.volkdown.octopus.utils.generateFeatureId
import java.util.*
import kotlin.collections.HashMap
import kotlin.collections.set

/**
 * Описывает связь между внутренним и внешним feature api
 *
 */
open class BaseFeatureApi : FeatureApi, InnerFeatureApi {

    /**
     * Подписчики, использующие внешнее апи
     */
    private val featureSubscribers: HashMap<FeatureSubscriberIdentifier, FeatureSubscriber> = HashMap()
    /**
     * Подписчики, использующие внутреннее апи
     */
    private val featureInnerSubscribers: HashMap<FeatureSubscriberIdentifier, InnerFeatureSubscriber> = HashMap()
    /**
     * Добавляются события в очередь, в случае не зарегистрированного подписчика на внутреннее feature api. Все события события группируются по ключу - feature id. В случае если подписчик внутреннего апи зарегистрирован, то события направляются напрямую к нему [FeatureSubscriber.handleEvent]. Также события будут направлены подписчику внутреннего апи как только он будет зарегистрирован.
     */
    private val pendingEvents: HashMap<String, Queue<BaseFeatureEvent>> = HashMap()

    private val featureKeysByOwnerId: HashMap<String, String> = HashMap()

    override fun unregisterOwner(owner: FeatureOwner) {
        checkThreadIsMain()
        val ownerId = owner.getId()
        if(featureKeysByOwnerId.containsKey(ownerId)){
            return
        }
        featureKeysByOwnerId.remove(ownerId)
    }

    override fun registerOwner(owner: FeatureOwner) {
        checkThreadIsMain()
        val ownerId = owner.getId()
        if(featureKeysByOwnerId.containsKey(ownerId)){
            return
        }
        featureKeysByOwnerId[ownerId] = generateFeatureId()
    }

    override fun getFeatureName(): String = ""

    @MainThread
    override fun registerSubscriber(featureOwner: FeatureOwner, subscriber: FeatureSubscriber) {
        checkThreadIsMain()
        val ownerId = featureOwner.getId()
        if(!featureKeysByOwnerId.containsKey(ownerId)){
            throw IllegalArgumentException("Owner must be registered")
        }
        val featureSubscriberIdentifier = FeatureSubscriberIdentifier(subscriber.featureId, ownerId)
        featureSubscribers[featureSubscriberIdentifier] = subscriber
    }

    @MainThread
    override fun unregisterSubscriber(subscriber: FeatureSubscriber) {
        checkThreadIsMain()
        val featureId = subscriber.featureId
        val featureSubscriberIdentifier = featureInnerSubscribers.keys.find {
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
    override fun registerSubscriber(featureOwner: FeatureOwner, subscriber: InnerFeatureSubscriber) {
        checkThreadIsMain()
        val featureId = subscriber.featureId
        featureInnerSubscribers[FeatureSubscriberIdentifier(featureId, featureOwner.getId())] = subscriber
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
        val featureId = featureKeysByOwnerId[featureOwner.getId()] ?: throw IllegalArgumentException("Owner must be registered or need send event by feature id")
        sendEvents(featureId)
    }

    override fun newSubscriber(featureOwner: FeatureOwner): FeatureSubscriber {
        checkThreadIsMain()
        val entry = featureSubscribers.entries.find {
            it.key.ownerId == featureOwner.getId()
        }
        var featureSubscriber = entry?.value
        if(featureSubscriber == null) {
            val featureId = featureKeysByOwnerId[featureOwner.getId()] ?: throw IllegalArgumentException("Owner must be registered")
            featureSubscriber = getFeatureSubscriberByFeatureId(featureId)
        }
        return featureSubscriber
    }

    @MainThread
    override fun sendInnerEvents(featureOwner: FeatureOwner, vararg events: BaseFeatureEvent) {
        checkThreadIsMain()
        val featureId = featureOwner.getId()

        val featureSubscriberIdentifier = featureSubscribers.entries.find {
            it.key.featureId == featureId
        }?.key
        val featureSubscriber = featureSubscribers[featureSubscriberIdentifier]

        featureSubscriber?.let { events.forEach { featureSubscriber.handleEvent(it) } }
    }

    @MainThread
    override fun newInnerSubscriber(featureOwner: FeatureOwner): InnerFeatureSubscriber {
        checkThreadIsMain()
        val ownerId = featureOwner.getId()
        val entry = featureInnerSubscribers.entries.find {
            it.key.ownerId == ownerId
        }
        var innerFeatureSubscriber = entry?.value
        if(innerFeatureSubscriber == null) {
            //Inner feature always has id as owner id
           innerFeatureSubscriber = object: InnerFeatureSubscriber{
               override val featureId: String = ownerId
               override fun handleEvent(event: BaseFeatureEvent) {}
           }
        }
        return innerFeatureSubscriber
    }

    @MainThread
    override fun newSubscriber(): FeatureSubscriber {
        checkThreadIsMain()
        return getFeatureSubscriberByFeatureId(generateFeatureId())
    }

    protected fun getFeatureIdByOwner(featureOwner: FeatureOwner): String{
        return featureKeysByOwnerId[featureOwner.getId()] ?: throw IllegalArgumentException("Owner must be registered")
    }

    private fun getFeatureSubscriberByFeatureId(featureId: String): FeatureSubscriber {
        return object: FeatureSubscriber{
            override val featureId: String = featureId
            override fun handleEvent(event: BaseFeatureEvent) {}
        }
    }

    private class FeatureSubscriberIdentifier(val featureId: String, val ownerId: String?)
}