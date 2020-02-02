package ru.volkdown.octopusrx

import io.reactivex.Observable
import io.reactivex.subjects.PublishSubject
import ru.volkdown.octopus.BaseFeatureEvent
import ru.volkdown.octopus.InnerFeatureSubscriber

class RxInnerFeatureSubscriber internal constructor(private val innerFeatureSubscriber: InnerFeatureSubscriber) : InnerFeatureSubscriber by innerFeatureSubscriber, RxSubscriber{

    private val eventsSubject: PublishSubject<BaseFeatureEvent> = PublishSubject.create()

    override fun handleEvent(event: BaseFeatureEvent) {
        eventsSubject.onNext(event)
    }

    override fun getEvents(): Observable<BaseFeatureEvent> = eventsSubject
}