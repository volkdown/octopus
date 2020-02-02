package ru.volkdown.octopusrx

import io.reactivex.Observable
import ru.volkdown.octopus.BaseFeatureEvent

interface RxSubscriber{

    fun getEvents(): Observable<BaseFeatureEvent>
}