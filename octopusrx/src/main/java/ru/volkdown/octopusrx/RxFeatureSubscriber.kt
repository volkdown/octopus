package ru.volkdown.octopusrx

import io.reactivex.Observable
import io.reactivex.subjects.PublishSubject
import ru.volkdown.coreoctopus.BaseFeatureEvent
import ru.volkdown.coreoctopus.FeatureEventListener


class RxFeatureSubscriber internal constructor() : FeatureEventListener, RxSubscriber{

    private val eventsSubject: PublishSubject<BaseFeatureEvent> = PublishSubject.create()

    override fun handleEvent(event: BaseFeatureEvent) {
        eventsSubject.onNext(event)
    }

    override fun getEvents(): Observable<BaseFeatureEvent> = eventsSubject
}