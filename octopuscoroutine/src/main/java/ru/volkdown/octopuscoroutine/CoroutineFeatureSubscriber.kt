package ru.volkdown.octopuscoroutine

import kotlinx.coroutines.channels.BroadcastChannel
import kotlinx.coroutines.channels.sendBlocking
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.asFlow
import ru.volkdown.coreoctopus.BaseFeatureEvent
import ru.volkdown.coreoctopus.FeatureEventListener


class CoroutineFeatureSubscriber internal constructor() : FeatureEventListener,
    CoroutineSubscriber {

    private val eventsBroadcastChannel = BroadcastChannel<BaseFeatureEvent>(1)

    override fun handleEvent(event: BaseFeatureEvent): FeatureEventListener {
        eventsBroadcastChannel.sendBlocking(event)
        return this
    }

    override suspend fun getEvents(): Flow<BaseFeatureEvent> {
        return eventsBroadcastChannel.asFlow()
    }
}