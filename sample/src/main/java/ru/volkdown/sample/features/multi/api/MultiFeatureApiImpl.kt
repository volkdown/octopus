package ru.volkdown.sample.features.multi.api

import androidx.fragment.app.Fragment
import ru.terrakok.cicerone.Screen
import ru.volkdown.octopuscicerone.FeatureScreen
import ru.volkdown.sample.base.ScreenFeatureApiImpl
import ru.volkdown.sample.features.multi.presentation.MultiFragment
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MultiFeatureApiImpl @Inject constructor(): ScreenFeatureApiImpl(), MultiFeatureApi, MultiInnerFeatureApi {

    override fun getScreen(featureId: String): Screen {
        return object: FeatureScreen(featureId){

            override fun getFeatureFragment(): Fragment = MultiFragment.newInstance()
        }
    }
}