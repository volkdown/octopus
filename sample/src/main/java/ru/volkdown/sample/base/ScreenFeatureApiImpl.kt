package ru.volkdown.sample.base

import ru.terrakok.cicerone.Screen
import ru.terrakok.cicerone.android.support.SupportAppScreen
import ru.volkdown.coreoctopus.BaseFeatureApi
import ru.volkdown.coreoctopus.FeatureOwner

open class ScreenFeatureApiImpl : BaseFeatureApi(), ScreenFeatureApi {
    
    override fun getScreen(owner: FeatureOwner): Screen {
        val featureIdByOwner = getFeatureIdByOwner(owner)
        return getScreen(featureIdByOwner)
    }

    override fun getScreen(featureId: String): Screen {
        return object : SupportAppScreen(){}
    }
}