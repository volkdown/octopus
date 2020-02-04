package ru.volkdown.octopusrx

import io.reactivex.Observable
import io.reactivex.subjects.PublishSubject
import ru.volkdown.coreoctopus.BaseFeatureEvent
import ru.volkdown.coreoctopus.FeatureSubscriber


class RxFeatureSubscriber internal constructor(private val featureSubscriber: FeatureSubscriber) : FeatureSubscriber by featureSubscriber, RxSubscriber{

    private val eventsSubject: PublishSubject<BaseFeatureEvent> = PublishSubject.create()

    override fun handleEvent(event: BaseFeatureEvent) {
        eventsSubject.onNext(event)
    }

    override fun getEvents(): Observable<BaseFeatureEvent> = eventsSubject
}