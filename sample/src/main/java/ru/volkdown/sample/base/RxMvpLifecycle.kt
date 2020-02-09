package ru.volkdown.sample.base

import androidx.annotation.CheckResult
import com.trello.rxlifecycle3.LifecycleTransformer
import com.trello.rxlifecycle3.OutsideLifecycleException
import com.trello.rxlifecycle3.RxLifecycle.bind
import io.reactivex.Observable
import io.reactivex.functions.Function

enum class PresenterEvent {

    CREATE,

    FIRST_VIEW_ATTACH,

    VIEW_ATTACHED,

    VIEW_DETACHED,

    VIEW_SHOWED,

    VIEW_HIDDEN,

    VIEW_DESTROYED,

    DESTROY
}

@CheckResult
fun <T> bindPresenter(lifecycle: Observable<PresenterEvent>): LifecycleTransformer<T> {
    return bind<T, PresenterEvent>(lifecycle,
        presenterLifecycle
    )
}

private val presenterLifecycle = Function<PresenterEvent, PresenterEvent> { lastEvent ->
    when (lastEvent) {
        PresenterEvent.CREATE -> PresenterEvent.DESTROY
        PresenterEvent.FIRST_VIEW_ATTACH -> PresenterEvent.DESTROY
        PresenterEvent.VIEW_ATTACHED -> PresenterEvent.VIEW_DETACHED
        PresenterEvent.VIEW_SHOWED -> PresenterEvent.VIEW_HIDDEN
        PresenterEvent.VIEW_DETACHED -> PresenterEvent.DESTROY
        PresenterEvent.VIEW_DESTROYED -> PresenterEvent.DESTROY
        PresenterEvent.DESTROY -> throw OutsideLifecycleException("Cannot bind to Presenter lifecycle when outside of it.")
        else -> throw UnsupportedOperationException("Binding to $lastEvent not yet implemented")
    }
}