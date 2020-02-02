package ru.volkdown.octopuscicerone

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import ru.terrakok.cicerone.android.support.SupportAppScreen
import ru.volkdown.octopus.utils.FEATURE_ID_KEY
import ru.volkdown.octopus.utils.FEATURE_SCREEN_PATH

abstract class FeatureScreen(private val featureId: String, private val screenPath: String? = null) : SupportAppScreen(){

    open fun getFeatureFragment(): Fragment? = null

    open fun getFeatureIntent(): Intent? = null

    final override fun getFragment(): Fragment? {
        return getFeatureFragment()?.apply {
            val bundle = arguments ?: Bundle()
            bundle.putString(FEATURE_ID_KEY, featureId)
            bundle.putString(FEATURE_SCREEN_PATH, screenPath)
            arguments = bundle
        }
    }

    final override fun getActivityIntent(context: Context?): Intent? {
        return getFeatureIntent()?.apply {
            putExtra(FEATURE_ID_KEY, featureId)
            putExtra(FEATURE_SCREEN_PATH, screenPath)
        }
    }
}