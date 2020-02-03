package ru.volkdown.sample.base

import androidx.annotation.CheckResult
import com.trello.rxlifecycle3.LifecycleTransformer
import com.trello.rxlifecycle3.OutsideLifecycleException
import com.trello.rxlifecycle3.RxLifecycle.bind
import io.reactivex.Observable
import io.reactivex.functions.Function

/**
 *  Описаны lifecycle события, которые могут возникнуть в [BasePresenter]
 */
enum class PresenterEvent {

    /**
     * Презентер создан
     */
    CREATE,

    /**
     * View первый раз добавлена
     */
    FIRST_VIEW_ATTACH,

    /**
     * View attached к презентеру
     */
    VIEW_ATTACHED,

    /**
     * View detached
     */
    VIEW_DETACHED,

    /**
     * View показана
     */
    VIEW_SHOWED,

    /**
     * View скрыта
     */
    VIEW_HIDDEN,

    /**
     * View разрушена
     */
    VIEW_DESTROYED,

    /**
     * Presenter на пути к разрушению
     */
    DESTROY
}

/**
 * Биндим текущее событие к lifecycle [BasePresenter]
 *
 * @param lifecycle текущее событие презентера
 */
@CheckResult
fun <T> bindPresenter(lifecycle: Observable<PresenterEvent>): LifecycleTransformer<T> {
    return bind<T, PresenterEvent>(lifecycle,
        presenterLifecycle
    )
}

/**
 * Определяет на каком методе жизненного цикла [BasePresenter] необходимо отписаться от rx события
 *
 * Например, если подписались на rx событие, в момент жизнего цикла презентера, когда он создан [PresenterEvent.CREATE],
 * то событие на котором нужно будет отписаться это [PresenterEvent.DESTROY]
 *
 */
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