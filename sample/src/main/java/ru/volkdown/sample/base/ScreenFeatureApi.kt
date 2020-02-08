package ru.volkdown.sample.base

import ru.terrakok.cicerone.Screen
import ru.volkdown.coreoctopus.FeatureApi
import ru.volkdown.coreoctopus.FeatureOwner

interface ScreenFeatureApi : FeatureApi {

    fun getScreen(owner: FeatureOwner): Screen

    fun getScreen(featureId: String): Screen
}