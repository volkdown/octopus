package ru.volkdown.sample.features.main.api

import androidx.fragment.app.Fragment
import ru.terrakok.cicerone.Screen
import ru.volkdown.octopuscicerone.FeatureScreen
import ru.volkdown.sample.base.ScreenFeatureApiImpl
import ru.volkdown.sample.features.main.presentation.MainFragment
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MainFeatureApiImpl @Inject constructor() : ScreenFeatureApiImpl(), MainFeatureApi, MainInnerFeatureApi{


    override fun getScreen(featureId: String): Screen {
        return object: FeatureScreen(featureId){

            override fun getFeatureFragment(): Fragment? = MainFragment.newInstance()
        }
    }
}