package ru.volkdown.sample.di

import dagger.Binds
import dagger.Module
import ru.volkdown.sample.features.samplefeature.api.SampleFeatureApi
import ru.volkdown.sample.features.samplefeature.api.SampleFeatureApiImpl
import ru.volkdown.sample.features.samplefeature.api.SampleInnerFeatureApi

@Module
interface FeaturesApiModule {

    @Binds
    fun bindSampleFeatureApi(impl: SampleFeatureApiImpl): SampleFeatureApi

    @Binds
    fun bindSampleInnerFeatureApi(impl: SampleFeatureApiImpl): SampleInnerFeatureApi
}