package ru.volkdown.sample.di

import ru.volkdown.sample.features.samplefeature.api.SampleFeatureApi
import ru.volkdown.sample.features.samplefeature.api.SampleInnerFeatureApi
import ru.volkdown.sample.navigation.NavigationContainer

interface ApplicationProvider: FeaturesApiProvider, NavigationProvider

interface FeaturesApiProvider{

    fun provideSampleFeatureApi(): SampleFeatureApi

    fun provideSampleInnerFeatureApi(): SampleInnerFeatureApi
}

interface NavigationProvider{

    fun provideNavigationContainer(): NavigationContainer
}