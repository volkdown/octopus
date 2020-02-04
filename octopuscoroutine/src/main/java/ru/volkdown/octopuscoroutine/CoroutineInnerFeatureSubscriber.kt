package ru.volkdown.octopuscoroutine

import kotlinx.coroutines.channels.BroadcastChannel
import kotlinx.coroutines.channels.sendBlocking
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.asFlow
import ru.volkdown.coreoctopus.BaseFeatureEvent
import ru.volkdown.coreoctopus.InnerFeatureSubscriber

class CoroutineInnerFeatureSubscriber internal constructor(private val innerFeatureSubscriber: InnerFeatureSubscriber) : InnerFeatureSubscriber by innerFeatureSubscriber,
    CoroutineSubscriber {

    private val eventsBroadcastChannel = BroadcastChannel<BaseFeatureEvent>(1)

    override fun handleEvent(event: BaseFeatureEvent) {
        eventsBroadcastChannel.sendBlocking(event)
    }

    override suspend fun getEvents(): Flow<BaseFeatureEvent> {
        return eventsBroadcastChannel.asFlow()
    }
}