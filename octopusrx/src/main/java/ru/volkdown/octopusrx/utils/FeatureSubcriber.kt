package ru.volkdown.octopusrx.utils

import ru.volkdown.octopus.FeatureSubscriber
import ru.volkdown.octopus.InnerFeatureSubscriber
import ru.volkdown.octopusrx.RxFeatureSubscriber
import ru.volkdown.octopusrx.RxInnerFeatureSubscriber

fun FeatureSubscriber.asRxSubscriber(): RxFeatureSubscriber{
    return RxFeatureSubscriber(this)
}

fun InnerFeatureSubscriber.asRxSubscriber(): RxInnerFeatureSubscriber {
    return RxInnerFeatureSubscriber(this)
}