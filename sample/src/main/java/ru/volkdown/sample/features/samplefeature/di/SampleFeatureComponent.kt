package ru.volkdown.sample.features.samplefeature.di

import dagger.Component
import ru.volkdown.coreoctopus.FeatureIdentifier
import ru.volkdown.sample.di.ApplicationProvider
import ru.volkdown.sample.features.samplefeature.presentation.SampleFeaturePresenter

@Component(
    dependencies = [ApplicationProvider::class, FeatureIdentifier::class]
)
interface SampleFeatureComponent {

    fun getPresenter(): SampleFeaturePresenter

    companion object {
        fun build(
            applicationProvider: ApplicationProvider,
            featureIdentifier: FeatureIdentifier
        ): SampleFeatureComponent {
            return DaggerSampleFeatureComponent.builder()
                .applicationProvider(applicationProvider)
                .featureIdentifier(featureIdentifier)
                .build()
        }
    }
}