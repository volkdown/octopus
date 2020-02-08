package ru.volkdown.sample.features.multi.di

import dagger.Component
import ru.volkdown.coreoctopus.FeatureIdentifier
import ru.volkdown.sample.di.ApplicationProvider
import ru.volkdown.sample.features.multi.presentation.MultiPresenter
import ru.volkdown.sample.features.single.presentation.SingleFragment
import ru.volkdown.sample.features.single.presentation.SinglePresenter

@Component(
    dependencies = [ApplicationProvider::class, FeatureIdentifier::class]
)
interface MultiComponent {

    fun getPresenter(): MultiPresenter

    companion object {
        fun build(applicationProvider: ApplicationProvider, featureIdentifier: FeatureIdentifier): MultiComponent {
            return DaggerMultiComponent.builder()
                .applicationProvider(applicationProvider)
                .featureIdentifier(featureIdentifier)
                .build()
        }
    }
}