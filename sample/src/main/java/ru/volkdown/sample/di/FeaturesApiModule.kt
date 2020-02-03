package ru.volkdown.sample.di

import dagger.Binds
import dagger.Module
import ru.volkdown.sample.features.main.api.MainFeatureApi
import ru.volkdown.sample.features.main.api.MainFeatureApiImpl
import ru.volkdown.sample.features.main.api.MainInnerFeatureApi
import ru.volkdown.sample.features.samplefeature.api.SampleFeatureApi
import ru.volkdown.sample.features.samplefeature.api.SampleFeatureApiImpl
import ru.volkdown.sample.features.samplefeature.api.SampleInnerFeatureApi

@Module
interface FeaturesApiModule {

    @Binds
    fun bindSampleFeatureApi(impl: SampleFeatureApiImpl): SampleFeatureApi

    @Binds
    fun bindSampleInnerFeatureApi(impl: SampleFeatureApiImpl): SampleInnerFeatureApi

    @Binds
    fun bindMainFeatureApi(impl: MainFeatureApiImpl): MainFeatureApi

    @Binds
    fun bindMainInnerFeatureApi(impl: MainFeatureApiImpl): MainInnerFeatureApi
}