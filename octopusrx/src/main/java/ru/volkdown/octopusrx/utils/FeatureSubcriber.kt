package ru.volkdown.octopusrx.utils

import ru.volkdown.coreoctopus.FeatureSubscriber
import ru.volkdown.coreoctopus.InnerFeatureSubscriber
import ru.volkdown.octopusrx.RxFeatureSubscriber
import ru.volkdown.octopusrx.RxInnerFeatureSubscriber

fun FeatureSubscriber.asRxSubscriber(): RxFeatureSubscriber{
    val rxFeatureSubscriber = RxFeatureSubscriber()
    this.setEventListener(rxFeatureSubscriber)
    return rxFeatureSubscriber
}

fun InnerFeatureSubscriber.asRxSubscriber(): RxInnerFeatureSubscriber {
    val rxFeatureSubscriber = RxInnerFeatureSubscriber()
    this.setEventListener(rxFeatureSubscriber)
    return rxFeatureSubscriber
}