package ru.volkdown.octopuscoroutine

import kotlinx.coroutines.flow.Flow
import ru.volkdown.octopus.BaseFeatureEvent

interface CoroutineSubscriber{

    suspend fun getEvents(): Flow<BaseFeatureEvent>
}