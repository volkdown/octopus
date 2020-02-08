package ru.volkdown.coreoctopus

class FeatureSubscriber(val featureId: String) : FeatureEventListener {

    private var eventListener: FeatureEventListener? = null

    override fun handleEvent(event: BaseFeatureEvent) {
        eventListener?.handleEvent(event)
    }

    fun setEventListener(eventListener: FeatureEventListener?) {
        this.eventListener = eventListener
    }
}