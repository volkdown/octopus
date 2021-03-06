package ru.volkdown.sample.features.samplefeature.api

import androidx.fragment.app.Fragment
import ru.terrakok.cicerone.Screen
import ru.volkdown.octopuscicerone.FeatureScreen
import ru.volkdown.sample.base.ScreenFeatureApiImpl
import ru.volkdown.sample.features.samplefeature.presentation.SampleFeatureFragment
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SampleFeatureApiImpl @Inject constructor(): ScreenFeatureApiImpl(), SampleFeatureApi, SampleInnerFeatureApi{

    override fun getScreen(featureId: String): Screen {
        return object: FeatureScreen(featureId){

            override fun getFeatureFragment(): Fragment? {
                return SampleFeatureFragment.newInstance()
            }
        }
    }
}