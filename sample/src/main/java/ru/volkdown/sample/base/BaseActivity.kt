package ru.volkdown.sample.base

import android.os.Build
import android.os.Bundle
import android.view.View.IMPORTANT_FOR_AUTOFILL_NO_EXCLUDE_DESCENDANTS
import androidx.annotation.LayoutRes
import androidx.annotation.Nullable
import com.arellomobile.mvp.MvpAppCompatActivity
import com.trello.rxlifecycle3.LifecycleProvider
import com.trello.rxlifecycle3.LifecycleTransformer
import com.trello.rxlifecycle3.RxLifecycle
import com.trello.rxlifecycle3.android.ActivityEvent
import com.trello.rxlifecycle3.android.RxLifecycleAndroid
import io.reactivex.Observable
import io.reactivex.subjects.BehaviorSubject


abstract class BaseActivity<PRESENTER : BasePresenter<*>> : MvpAppCompatActivity(),
        LifecycleProvider<LifeCycleEvent> {

    private val lifecycleSubject: BehaviorSubject<LifeCycleEvent> = BehaviorSubject.create()

    @get:LayoutRes
    protected abstract val layoutResId: Int

    protected abstract val presenter: PRESENTER?

    protected abstract fun providePresenter(): PRESENTER?

    override fun onCreate(@Nullable savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        lifecycleSubject.onNext(LifeCycleEvent.CREATE)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O_MR1) {
            window.decorView.importantForAutofill = IMPORTANT_FOR_AUTOFILL_NO_EXCLUDE_DESCENDANTS
        }
        setContentView(layoutResId)
    }

    override fun onDestroy() {
        lifecycleSubject.onNext(LifeCycleEvent.DESTROY)
        super.onDestroy()
    }

    override fun onBackPressed() {
        val fragmentManager = supportFragmentManager
        if (fragmentManager.backStackEntryCount > 0) {
            fragmentManager.popBackStack()
        } else {
            super.onBackPressed()
        }
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

    override fun lifecycle(): Observable<LifeCycleEvent> = lifecycleSubject

    override fun <T : Any?> bindUntilEvent(event: LifeCycleEvent): LifecycleTransformer<T> {
        return RxLifecycle.bindUntilEvent(lifecycleSubject, event)
    }

    override fun <T : Any?> bindToLifecycle(): LifecycleTransformer<T> {
        return RxLifecycleAndroid.bindActivity(getActivityLifecycle())
    }

    private fun getActivityLifecycle(): Observable<ActivityEvent> {
        return lifecycleSubject
                .filter {
                    it == LifeCycleEvent.CREATE ||
                            it == LifeCycleEvent.START ||
                            it == LifeCycleEvent.RESUME ||
                            it == LifeCycleEvent.PAUSE ||
                            it == LifeCycleEvent.STOP ||
                            it == LifeCycleEvent.DESTROY
                }
                .map {
                    when (it) {
                        LifeCycleEvent.CREATE -> ActivityEvent.CREATE
                        LifeCycleEvent.START -> ActivityEvent.START
                        LifeCycleEvent.RESUME -> ActivityEvent.RESUME
                        LifeCycleEvent.PAUSE -> ActivityEvent.PAUSE
                        LifeCycleEvent.STOP -> ActivityEvent.STOP
                        LifeCycleEvent.DESTROY -> ActivityEvent.DESTROY
                        else -> {
                            throw Throwable("not register event")
                        }
                    }
                }
    }
}