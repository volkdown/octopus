package ru.volkdown.octopus.utils

import android.app.Activity
import ru.volkdown.octopus.FeatureIdentifier

/**
 * Получение feature индефикатора для [Activity] из [Activity.getIntent]
 */
fun Activity.getFeatureIdentifier(): FeatureIdentifier {
    val featureId = intent?.getStringExtra(FEATURE_ID_KEY) ?: getDefaultFeatureId()
    val startScreenPath = intent?.getStringExtra(FEATURE_SCREEN_PATH)
    return FeatureIdentifier(featureId, startScreenPath)
}

private fun Activity.getDefaultFeatureId(): String {
    return this.javaClass.canonicalName
}