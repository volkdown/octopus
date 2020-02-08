package ru.volkdown.octopuscoroutine.utils

import ru.volkdown.coreoctopus.FeatureSubscriber
import ru.volkdown.coreoctopus.InnerFeatureSubscriber
import ru.volkdown.octopuscoroutine.CoroutineFeatureSubscriber
import ru.volkdown.octopuscoroutine.CoroutineInnerFeatureSubscriber

fun FeatureSubscriber.asCoroutineSubscriber(): CoroutineFeatureSubscriber {
    val coroutineFeatureSubscriber = CoroutineFeatureSubscriber()
    this.setEventListener(coroutineFeatureSubscriber)
    return coroutineFeatureSubscriber
}

fun InnerFeatureSubscriber.asCoroutineSubscriber(): CoroutineInnerFeatureSubscriber {
    val coroutineInnerFeatureSubscriber = CoroutineInnerFeatureSubscriber()
    this.setEventListener(coroutineInnerFeatureSubscriber)
    return coroutineInnerFeatureSubscriber
}