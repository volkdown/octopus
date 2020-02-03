package ru.volkdown.sample.features.single.di

import dagger.Component
import ru.volkdown.sample.di.ApplicationProvider
import ru.volkdown.sample.features.single.presentation.SingleFragment
import ru.volkdown.sample.features.single.presentation.SinglePresenter

@Component(
    dependencies = [ApplicationProvider::class]
)
interface SingleComponent {

    fun getPresenter(): SinglePresenter

    fun inject(d: SingleFragment)

    companion object {
        fun build(applicationProvider: ApplicationProvider): SingleComponent {
            return DaggerSingleComponent.builder()
                .applicationProvider(applicationProvider)
                .build()
        }
    }
}