package ru.volkdown.sample.di

import dagger.Binds
import dagger.Module
import ru.volkdown.sample.features.main.api.MainFeatureApi
import ru.volkdown.sample.features.main.api.MainFeatureApiImpl
import ru.volkdown.sample.features.main.api.MainInnerFeatureApi
import ru.volkdown.sample.features.multi.api.MultiFeatureApi
import ru.volkdown.sample.features.multi.api.MultiFeatureApiImpl
import ru.volkdown.sample.features.multi.api.MultiInnerFeatureApi
import ru.volkdown.sample.features.samplefeature.api.SampleFeatureApi
import ru.volkdown.sample.features.samplefeature.api.SampleFeatureApiImpl
import ru.volkdown.sample.features.samplefeature.api.SampleInnerFeatureApi
import ru.volkdown.sample.features.single.api.SingleFeatureApi
import ru.volkdown.sample.features.single.api.SingleFeatureApiImpl
import ru.volkdown.sample.features.single.api.SingleInnerFeatureApi

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

    @Binds
    fun bindSingleFeatureApi(impl: SingleFeatureApiImpl): SingleFeatureApi

    @Binds
    fun bindSingleInnerFeatureApi(impl: SingleFeatureApiImpl): SingleInnerFeatureApi

    @Binds
    fun bindMultiFeatureApiImpl(impl: MultiFeatureApiImpl): MultiFeatureApi

    @Binds
    fun bindMultiInnerFeatureApiImpl(impl: MultiFeatureApiImpl): MultiInnerFeatureApi
}