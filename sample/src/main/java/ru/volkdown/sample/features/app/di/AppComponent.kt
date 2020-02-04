package ru.volkdown.sample.features.app.di

import dagger.Component
import ru.volkdown.coreoctopus.FeatureIdentifier
import ru.volkdown.sample.di.ApplicationProvider
import ru.volkdown.sample.features.app.presentation.AppFragment
import ru.volkdown.sample.features.app.presentation.AppPresenter

@Component(
    dependencies = [ApplicationProvider::class, FeatureIdentifier::class]
)
interface AppComponent {

    fun getPresenter(): AppPresenter

    fun inject(d: AppFragment)

    companion object {
        fun build(applicationProvider: ApplicationProvider, featureIdentifier: FeatureIdentifier): AppComponent {
            return DaggerAppComponent.builder()
                .applicationProvider(applicationProvider)
                .featureIdentifier(featureIdentifier)
                .build()
        }
    }
}