package ru.volkdown.sample.features.samplefeature.api.events

import ru.volkdown.coreoctopus.BaseFeatureEvent


class IAmFeatureFromSampleFeatureEvent(val featureId: String): BaseFeatureEvent()