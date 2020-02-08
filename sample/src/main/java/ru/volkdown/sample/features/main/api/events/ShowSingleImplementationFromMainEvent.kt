package ru.volkdown.sample.features.main.api.events

import ru.volkdown.coreoctopus.BaseFeatureEvent

class ShowSingleImplementationFromMainEvent(val isNeedShowPendingEvent: Boolean) : BaseFeatureEvent()