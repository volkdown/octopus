package ru.volkdown.sample.base

import android.annotation.SuppressLint
import com.arellomobile.mvp.MvpPresenter
import com.trello.rxlifecycle3.LifecycleProvider
import com.trello.rxlifecycle3.LifecycleTransformer
import com.trello.rxlifecycle3.RxLifecycle
import com.trello.rxlifecycle3.kotlin.bindUntilEvent
import io.reactivex.Observable
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.subjects.BehaviorSubject
import ru.volkdown.coreoctopus.BaseFeatureEvent
import ru.volkdown.coreoctopus.FeatureApi
import ru.volkdown.coreoctopus.FeatureIdentifier
import ru.volkdown.coreoctopus.FeatureOwner
import ru.volkdown.coreoctopus.InnerFeatureApi
import ru.volkdown.octopusrx.utils.asRxSubscriber
import ru.volkdown.sample.base.PresenterEvent.DESTROY
import timber.log.Timber
import java.util.UUID

abstract class BasePresenter<VIEW : BaseView> constructor(private val featureIdentifier: FeatureIdentifier?) : MvpPresenter<VIEW>(),
        LifecycleProvider<PresenterEvent>,
    FeatureOwner {

    private val lifecycleSubject: BehaviorSubject<PresenterEvent> = BehaviorSubject.create()

    private val registeredFeatures = HashSet<FeatureApi>()

    init {
        lifecycleSubject.onNext(PresenterEvent.CREATE)
    }

    override fun onFirstViewAttach() {
        super.onFirstViewAttach()
        lifecycleSubject.onNext(PresenterEvent.FIRST_VIEW_ATTACH)
    }

    override fun attachView(view: VIEW) {
        super.attachView(view)
        lifecycleSubject.onNext(PresenterEvent.VIEW_ATTACHED)
    }

    open fun onViewShowed() {
        lifecycleSubject.onNext(PresenterEvent.VIEW_SHOWED)
    }

    open fun onViewHidden() {
        lifecycleSubject.onNext(PresenterEvent.VIEW_HIDDEN)
    }

    override fun detachView(view: VIEW) {
        super.detachView(view)
        lifecycleSubject.onNext(PresenterEvent.VIEW_DETACHED)
    }

    override fun destroyView(view: VIEW) {
        super.destroyView(view)
        lifecycleSubject.onNext(PresenterEvent.VIEW_DESTROYED)
    }

    override fun onDestroy() {
        lifecycleSubject.onNext(PresenterEvent.DESTROY)
        unregisterFeatures()
        super.onDestroy()
    }

    override fun lifecycle(): Observable<PresenterEvent> = lifecycle()

    override fun <T : Any?> bindUntilEvent(event: PresenterEvent): LifecycleTransformer<T> {
        return RxLifecycle.bindUntilEvent(lifecycleSubject, event)
    }

    override fun <T : Any?> bindToLifecycle(): LifecycleTransformer<T> {
        return bindPresenter(lifecycle())
    }

    protected fun handleError(throwable: Throwable, showError: Boolean = true) {
        Timber.e(throwable)
    }

    @SuppressLint("CheckResult")
    protected fun subscribeToFeatureEvents(featureApi: FeatureApi, onNewEvent: (BaseFeatureEvent) -> Unit) {
        val newSubscriber = featureApi.newSubscriber(this)
        newSubscriber.asRxSubscriber().getEvents()
                .bindUntilEvent(this, DESTROY)
                .doOnTerminate { featureApi.unregisterSubscriber(newSubscriber) }
                .subscribeBy(
                        onNext = { onNewEvent.invoke(it) },
                        onError = { handleError(it) }
                )
        featureApi.registerSubscriber(this, newSubscriber)
    }

    @SuppressLint("CheckResult")
    protected fun subscribeToFeatureEvents(featureApi: FeatureApi, featureId: String, onNewEvent: (BaseFeatureEvent) -> Unit) {
        val newSubscriber = featureApi.newSubscriber(featureId)
        newSubscriber.asRxSubscriber().getEvents()
            .bindUntilEvent(this, DESTROY)
            .doOnTerminate { featureApi.unregisterSubscriber(newSubscriber) }
            .subscribeBy(
                onNext = { onNewEvent.invoke(it) },
                onError = { handleError(it) }
            )
        featureApi.registerSubscriber(this, newSubscriber)
    }

    protected fun registerFeatures(vararg featuresApi: FeatureApi){
        featuresApi.iterator().forEach { featureApi ->
            featureApi.registerOwner(this)
            registeredFeatures.add(featureApi)
        }
    }

    private fun unregisterFeatures(){
        registeredFeatures.forEach {
            it.unregisterOwner(this)
        }
        registeredFeatures.clear()
    }

    @SuppressLint("CheckResult")
    protected fun subscribeToFeatureEvents(featureApi: InnerFeatureApi, onNewEvent: (BaseFeatureEvent) -> Unit) {
        val innerSubscriber = featureApi.newInnerSubscriber(this)
        innerSubscriber.asRxSubscriber().getEvents()
                .bindUntilEvent(this, DESTROY)
                .doOnTerminate { featureApi.unregisterSubscriber(innerSubscriber) }
                .subscribeBy(
                        onNext = { onNewEvent.invoke(it) },
                        onError = { handleError(it) }
                )
        featureApi.registerSubscriber(this, innerSubscriber)
    }

    override val id: String = featureIdentifier?.featureId ?: UUID.randomUUID().toString()
}