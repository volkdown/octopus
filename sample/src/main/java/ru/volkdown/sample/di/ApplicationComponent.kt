package ru.volkdown.sample.di

import dagger.Component
import ru.volkdown.sample.navigation.NavigationContainer
import ru.volkdown.sample.navigation.NavigationContainerImpl
import javax.inject.Singleton

@Singleton
@Component(
    dependencies = [NavigationContainer::class],
    modules = [FeaturesApiModule::class]
)
interface ApplicationComponent : ApplicationProvider {

    companion object {

        fun build(): ApplicationComponent {
            return DaggerApplicationComponent.builder()
                .navigationContainer(NavigationContainerImpl())
                .build()
        }
    }
}