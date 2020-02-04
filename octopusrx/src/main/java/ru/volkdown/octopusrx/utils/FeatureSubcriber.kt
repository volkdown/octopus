package ru.volkdown.octopusrx.utils

import ru.volkdown.coreoctopus.FeatureSubscriber
import ru.volkdown.coreoctopus.InnerFeatureSubscriber
import ru.volkdown.octopusrx.RxFeatureSubscriber
import ru.volkdown.octopusrx.RxInnerFeatureSubscriber

fun FeatureSubscriber.asRxSubscriber(): RxFeatureSubscriber{
    return RxFeatureSubscriber(this)
}

fun InnerFeatureSubscriber.asRxSubscriber(): RxInnerFeatureSubscriber {
    return RxInnerFeatureSubscriber(this)
}