package ru.volkdown.sample.features.multi.api.events

import androidx.annotation.StringRes
import ru.volkdown.coreoctopus.BaseFeatureEvent

class ShowMessageFromMultiEvent(@StringRes val messageResId: Int, val featureId: String): BaseFeatureEvent()