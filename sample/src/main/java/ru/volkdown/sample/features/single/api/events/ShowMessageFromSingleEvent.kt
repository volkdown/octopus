package ru.volkdown.sample.features.single.api.events

import androidx.annotation.StringRes
import ru.volkdown.coreoctopus.BaseFeatureEvent


class ShowMessageFromSingleEvent(@StringRes val messageResId: Int): BaseFeatureEvent()