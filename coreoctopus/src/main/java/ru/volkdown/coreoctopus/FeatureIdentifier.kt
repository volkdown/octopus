package ru.volkdown.coreoctopus

import android.net.Uri

data class FeatureIdentifier(val featureId: String, val screenPath: String? = null){

    inline fun <reified T: Any> getParam(paramKey: String): T?{
        return try {
            Uri.parse(screenPath).getQueryParameter(paramKey) as? T
        } catch (e: Exception){
            null
        }
    }
}