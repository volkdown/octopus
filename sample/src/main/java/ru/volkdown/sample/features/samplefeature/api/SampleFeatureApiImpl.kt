package ru.volkdown.sample.features.samplefeature.api

import androidx.fragment.app.Fragment
import ru.terrakok.cicerone.Screen
import ru.volkdown.coreoctopus.BaseFeatureApi
import ru.volkdown.coreoctopus.FeatureOwner
import ru.volkdown.octopuscicerone.FeatureScreen
import ru.volkdown.sample.features.samplefeature.presentation.SampleFeatureFragment
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SampleFeatureApiImpl @Inject constructor(): BaseFeatureApi(), SampleFeatureApi, SampleInnerFeatureApi{

    override fun getScreen(owner: FeatureOwner) : Screen{
        val featureIdByOwner = getFeatureIdByOwner(owner)
        return object: FeatureScreen(featureIdByOwner){

            override fun getFeatureFragment(): Fragment? {
                return SampleFeatureFragment.newInstance()
            }
        }
    }
}