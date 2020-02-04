package ru.volkdown.octopuscoroutine

import kotlinx.coroutines.flow.Flow
import ru.volkdown.coreoctopus.BaseFeatureEvent

interface CoroutineSubscriber{

    suspend fun getEvents(): Flow<BaseFeatureEvent>
}