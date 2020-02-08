package ru.volkdown.sample.features.main.api.events

import ru.volkdown.coreoctopus.BaseFeatureEvent

class ShowMultiImplementationFromMainEvent(val isNeedShowPendingEvent: Boolean) : BaseFeatureEvent()