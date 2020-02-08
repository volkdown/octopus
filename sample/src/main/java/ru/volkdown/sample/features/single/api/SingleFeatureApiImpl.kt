package ru.volkdown.sample.features.single.api

import androidx.fragment.app.Fragment
import ru.terrakok.cicerone.Screen
import ru.volkdown.octopuscicerone.FeatureScreen
import ru.volkdown.sample.base.ScreenFeatureApiImpl
import ru.volkdown.sample.features.single.presentation.SingleFragment
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SingleFeatureApiImpl @Inject constructor(): ScreenFeatureApiImpl(), SingleFeatureApi, SingleInnerFeatureApi {

    override fun getScreen(featureId: String): Screen {
        return object: FeatureScreen(featureId) {
            override fun getFeatureFragment(): Fragment? = SingleFragment.newInstance()
        }
    }
}