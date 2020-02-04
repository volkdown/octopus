package ru.volkdown.octopuscoroutine.utils

import ru.volkdown.coreoctopus.FeatureSubscriber
import ru.volkdown.coreoctopus.InnerFeatureSubscriber
import ru.volkdown.octopuscoroutine.CoroutineFeatureSubscriber
import ru.volkdown.octopuscoroutine.CoroutineInnerFeatureSubscriber

fun FeatureSubscriber.asCoroutineSubscriber(): CoroutineFeatureSubscriber {
    return CoroutineFeatureSubscriber(this)
}

fun InnerFeatureSubscriber.asCoroutineSubscriber(): CoroutineInnerFeatureSubscriber {
    return CoroutineInnerFeatureSubscriber(this)
}