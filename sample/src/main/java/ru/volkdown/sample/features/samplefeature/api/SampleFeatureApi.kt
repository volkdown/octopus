package ru.volkdown.sample.features.samplefeature.api

import ru.terrakok.cicerone.Screen
import ru.volkdown.octopus.FeatureApi
import ru.volkdown.octopus.FeatureOwner

interface SampleFeatureApi : FeatureApi{

    fun getScreen(owner: FeatureOwner): Screen
}