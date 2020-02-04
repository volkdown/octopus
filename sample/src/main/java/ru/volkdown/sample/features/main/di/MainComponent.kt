package ru.volkdown.sample.features.main.di

import dagger.Component
import ru.volkdown.coreoctopus.FeatureIdentifier
import ru.volkdown.sample.di.ApplicationProvider
import ru.volkdown.sample.features.main.presentation.MainFragment
import ru.volkdown.sample.features.main.presentation.MainPresenter

@Component(
    dependencies = [ApplicationProvider::class, FeatureIdentifier::class]
)
interface MainComponent {

    fun getPresenter(): MainPresenter

    fun inject(d: MainFragment)

    companion object {
        fun build(applicationProvider: ApplicationProvider, featureIdentifier: FeatureIdentifier): MainComponent {
            return DaggerMainComponent.builder()
                .applicationProvider(applicationProvider)
                .featureIdentifier(featureIdentifier)
                .build()
        }
    }
}