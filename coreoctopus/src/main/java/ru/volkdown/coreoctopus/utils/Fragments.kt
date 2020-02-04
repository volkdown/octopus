package ru.volkdown.coreoctopus.utils

import androidx.fragment.app.Fragment
import ru.volkdown.coreoctopus.FeatureIdentifier


/**
 * Получение feature индефикатора для [Fragment] из [Fragment.getArguments]
 */
fun Fragment.getFeatureIdentifier(): FeatureIdentifier {
    val featureId = arguments?.getString(FEATURE_ID_KEY, getDefaultFeatureId()) ?: getDefaultFeatureId()
    val startScreenPath = arguments?.getString(FEATURE_SCREEN_PATH)
    return FeatureIdentifier(featureId, startScreenPath)
}

private fun Fragment.getDefaultFeatureId(): String{
    return this.javaClass.canonicalName
}
