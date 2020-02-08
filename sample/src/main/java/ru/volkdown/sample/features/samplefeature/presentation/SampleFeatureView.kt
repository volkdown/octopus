package ru.volkdown.sample.features.samplefeature.presentation

import androidx.annotation.StringRes
import com.arellomobile.mvp.viewstate.strategy.SingleStateStrategy
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType
import ru.volkdown.sample.base.BaseView


interface SampleFeatureView : BaseView{

    @StateStrategyType(SingleStateStrategy::class)
    fun showSingleOwnerMessage(@StringRes messageRes: Int)

    fun showMultiOwnerMessage(@StringRes messageResId: Int, featureId: String)
}