package ru.volkdown.octopusrx.utils

import ru.volkdown.coreoctopus.FeatureSubscriber
import ru.volkdown.coreoctopus.InnerFeatureSubscriber
import ru.volkdown.octopusrx.RxFeatureSubscriber
import ru.volkdown.octopusrx.RxInnerFeatureSubscriber

fun FeatureSubscriber.asRxSubscriber(): RxFeatureSubscriber{
    val rxFeatureSubscriber = RxFeatureSubscriber()
    val rxFeatureListener = this.setEventListener(rxFeatureSubscriber) as? RxFeatureSubscriber
    return rxFeatureListener ?: throw RuntimeException("Need use only one type subscriber")
}

fun InnerFeatureSubscriber.asRxSubscriber(): RxInnerFeatureSubscriber {
    val rxFeatureSubscriber = RxInnerFeatureSubscriber()
    val rxFeatureListener = this.setEventListener(rxFeatureSubscriber) as? RxInnerFeatureSubscriber
    return rxFeatureListener ?: throw RuntimeException("Need use only one type subscriber")
}