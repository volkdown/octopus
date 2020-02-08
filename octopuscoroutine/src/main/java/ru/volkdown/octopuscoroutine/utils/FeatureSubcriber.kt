package ru.volkdown.octopuscoroutine.utils

import ru.volkdown.coreoctopus.FeatureSubscriber
import ru.volkdown.coreoctopus.InnerFeatureSubscriber
import ru.volkdown.octopuscoroutine.CoroutineFeatureSubscriber
import ru.volkdown.octopuscoroutine.CoroutineInnerFeatureSubscriber

fun FeatureSubscriber.asCoroutineSubscriber(): CoroutineFeatureSubscriber {
    val coroutineFeatureSubscriber = CoroutineFeatureSubscriber()
    val coroutineFeatureListener = this.setEventListener(coroutineFeatureSubscriber) as? CoroutineFeatureSubscriber
    return coroutineFeatureListener ?: throw RuntimeException("Need use only one type subscriber")
}

fun InnerFeatureSubscriber.asCoroutineSubscriber(): CoroutineInnerFeatureSubscriber {
    val coroutineInnerFeatureSubscriber = CoroutineInnerFeatureSubscriber()
    val innerCoroutineFeatureListener = this.setEventListener(coroutineInnerFeatureSubscriber) as? CoroutineInnerFeatureSubscriber
    return innerCoroutineFeatureListener ?: throw RuntimeException("Need use only one type subscriber")
}