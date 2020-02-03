package ru.volkdown.sample.features.app.di

import dagger.Component
import ru.volkdown.sample.di.ApplicationProvider
import ru.volkdown.sample.features.app.presentation.AppFragment
import ru.volkdown.sample.features.app.presentation.AppPresenter
import ru.volkdown.sample.features.single.presentation.SingleFragment
import ru.volkdown.sample.features.single.presentation.SinglePresenter

@Component(
    dependencies = [ApplicationProvider::class]
)
interface AppComponent {

    fun getPresenter(): AppPresenter

    fun inject(d: AppFragment)

    companion object {
        fun build(applicationProvider: ApplicationProvider): AppComponent {
            return DaggerAppComponent.builder()
                .applicationProvider(applicationProvider)
                .build()
        }
    }
}