package ru.volkdown.octopusrx

import io.reactivex.Observable
import ru.volkdown.coreoctopus.BaseFeatureEvent

interface RxSubscriber{

    fun getEvents(): Observable<BaseFeatureEvent>
}