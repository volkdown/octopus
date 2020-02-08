package ru.volkdown.sample.di

import ru.volkdown.sample.features.main.api.MainFeatureApi
import ru.volkdown.sample.features.main.api.MainInnerFeatureApi
import ru.volkdown.sample.features.samplefeature.api.SampleFeatureApi
import ru.volkdown.sample.features.samplefeature.api.SampleInnerFeatureApi
import ru.volkdown.sample.features.single.api.SingleFeatureApi
import ru.volkdown.sample.features.single.api.SingleInnerFeatureApi
import ru.volkdown.sample.navigation.NavigationContainer

interface ApplicationProvider: FeaturesApiProvider, NavigationProvider

interface FeaturesApiProvider{

    fun provideSampleFeatureApi(): SampleFeatureApi

    fun provideSampleInnerFeatureApi(): SampleInnerFeatureApi

    fun provideMainFeatureApi(): MainFeatureApi

    fun provideMainInnerFeatureApi(): MainInnerFeatureApi

    fun provideSingleFeatureApi(): SingleFeatureApi

    fun provideSingleInnerFeatureApi(): SingleInnerFeatureApi
}

interface NavigationProvider{

    fun provideNavigationContainer(): NavigationContainer
}