package ru.volkdown.sample.base

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.fragment.app.Fragment
import com.arellomobile.mvp.MvpAppCompatFragment
import com.trello.rxlifecycle3.LifecycleProvider
import com.trello.rxlifecycle3.LifecycleTransformer
import com.trello.rxlifecycle3.RxLifecycle
import com.trello.rxlifecycle3.android.FragmentEvent
import com.trello.rxlifecycle3.android.RxLifecycleAndroid
import io.reactivex.Observable
import io.reactivex.subjects.BehaviorSubject

/**
 * Базовый [Fragment], который содержит
 * логику управления [BaseView].
 */
abstract class BaseFragment<PRESENTER : BasePresenter<*>> : MvpAppCompatFragment(),
        LifecycleProvider<LifeCycleEvent> {

    private val lifecycleSubject: BehaviorSubject<LifeCycleEvent> = BehaviorSubject.create()

    @get:LayoutRes
    protected abstract val layoutResId: Int

    protected abstract val presenter: PRESENTER?

    protected abstract fun providePresenter(): PRESENTER?

    override fun onAttach(context: Context) {
        super.onAttach(context)
        lifecycleSubject.onNext(LifeCycleEvent.ATTACH)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        lifecycleSubject.onNext(LifeCycleEvent.CREATE)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(layoutResId, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        lifecycleSubject.onNext(LifeCycleEvent.CREATE_VIEW)
    }

    override fun onStart() {
        super.onStart()
        lifecycleSubject.onNext(LifeCycleEvent.START)
        presenter?.onViewShowed()
    }

    override fun onResume() {
        super.onResume()
        lifecycleSubject.onNext(LifeCycleEvent.RESUME)
    }

    override fun onPause() {
        lifecycleSubject.onNext(LifeCycleEvent.PAUSE)
        super.onPause()
    }

    override fun onStop() {
        lifecycleSubject.onNext(LifeCycleEvent.STOP)
        presenter?.onViewHidden()
        super.onStop()
    }

    override fun onDestroyView() {
        lifecycleSubject.onNext(LifeCycleEvent.DESTROY_VIEW)
        super.onDestroyView()
    }

    override fun onDestroy() {
        lifecycleSubject.onNext(LifeCycleEvent.DESTROY)
        super.onDestroy()
    }

    override fun onDetach() {
        lifecycleSubject.onNext(LifeCycleEvent.DETACH)
        super.onDetach()
    }

    override fun lifecycle(): Observable<LifeCycleEvent> = lifecycleSubject

    override fun <T : Any?> bindUntilEvent(event: LifeCycleEvent): LifecycleTransformer<T> {
        return RxLifecycle.bindUntilEvent(lifecycleSubject, event)
    }

    override fun <T : Any?> bindToLifecycle(): LifecycleTransformer<T> {
        return RxLifecycleAndroid.bindFragment(getFragmentLifecycle())
    }

    private fun getFragmentLifecycle(): Observable<FragmentEvent> {
        return lifecycleSubject.map { event ->
            when (event) {
                LifeCycleEvent.ATTACH -> FragmentEvent.ATTACH
                LifeCycleEvent.CREATE -> FragmentEvent.CREATE
                LifeCycleEvent.CREATE_VIEW -> FragmentEvent.CREATE_VIEW
                LifeCycleEvent.START -> FragmentEvent.START
                LifeCycleEvent.RESUME -> FragmentEvent.RESUME
                LifeCycleEvent.PAUSE -> FragmentEvent.PAUSE
                LifeCycleEvent.STOP -> FragmentEvent.STOP
                LifeCycleEvent.DESTROY_VIEW -> FragmentEvent.DESTROY_VIEW
                LifeCycleEvent.DESTROY -> FragmentEvent.DESTROY
                LifeCycleEvent.DETACH -> FragmentEvent.DETACH
            }
        }
    }
}