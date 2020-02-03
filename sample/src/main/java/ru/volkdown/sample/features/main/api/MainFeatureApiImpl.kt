package ru.volkdown.sample.features.main.api

import androidx.fragment.app.Fragment
import ru.terrakok.cicerone.Screen
import ru.volkdown.octopus.BaseFeatureApi
import ru.volkdown.octopus.FeatureOwner
import ru.volkdown.octopuscicerone.FeatureScreen
import ru.volkdown.sample.features.main.presentation.MainFragment
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MainFeatureApiImpl @Inject constructor() : BaseFeatureApi(), MainFeatureApi, MainInnerFeatureApi{

    override fun getScreen(owner: FeatureOwner): Screen {
        val featureIdByOwner = getFeatureIdByOwner(owner)
        return object: FeatureScreen(featureIdByOwner){

            override fun getFeatureFragment(): Fragment? = MainFragment.newInstance()
        }
    }

}