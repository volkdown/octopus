package ru.volkdown.octopus

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class FeatureSubscriber internal constructor(val featureId: String) : BaseFeatureSubscriber(featureId), Parcelable