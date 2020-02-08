package ru.volkdown.sample.features.single.api

import androidx.fragment.app.Fragment
import ru.terrakok.cicerone.Screen
import ru.volkdown.coreoctopus.BaseFeatureApi
import ru.volkdown.coreoctopus.FeatureOwner
import ru.volkdown.octopuscicerone.FeatureScreen
import ru.volkdown.sample.features.single.presentation.SingleFragment
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SingleFeatureApiImpl @Inject constructor(): BaseFeatureApi(), SingleFeatureApi, SingleInnerFeatureApi {

    override fun getScreen(owner: FeatureOwner): Screen {
        val featureId = getFeatureIdByOwner(owner)
        return object: FeatureScreen(featureId) {
            override fun getFeatureFragment(): Fragment? = SingleFragment.newInstance()
        }
    }
}