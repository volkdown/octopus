package ru.volkdown.octopuscoroutine.utils

import ru.volkdown.octopus.FeatureSubscriber
import ru.volkdown.octopus.InnerFeatureSubscriber
import ru.volkdown.octopuscoroutine.CoroutineFeatureSubscriber
import ru.volkdown.octopuscoroutine.CoroutineInnerFeatureSubscriber

fun FeatureSubscriber.asCoroutineSubscriber(): CoroutineFeatureSubscriber {
    return CoroutineFeatureSubscriber(this)
}

fun InnerFeatureSubscriber.asCoroutineSubscriber(): CoroutineInnerFeatureSubscriber {
    return CoroutineInnerFeatureSubscriber(this)
}