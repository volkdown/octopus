package ru.volkdown.coreoctopus

data class InnerFeatureSubscriber(val featureId: String) : FeatureEventListener {

    private var eventListener: FeatureEventListener? = null

    override fun handleEvent(event: BaseFeatureEvent): FeatureEventListener {
        eventListener?.handleEvent(event)
        return this
    }

    fun setEventListener(eventListener: FeatureEventListener?): FeatureEventListener {
        if(this.eventListener == null){
            this.eventListener = eventListener
        }
        return this.eventListener!!
    }
}