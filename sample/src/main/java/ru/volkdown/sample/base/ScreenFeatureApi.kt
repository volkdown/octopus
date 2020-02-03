package ru.volkdown.sample.base

import ru.terrakok.cicerone.Screen
import ru.volkdown.octopus.FeatureApi
import ru.volkdown.octopus.FeatureOwner

interface ScreenFeatureApi : FeatureApi{

    fun getScreen(owner: FeatureOwner): Screen
}