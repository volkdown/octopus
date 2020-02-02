package ru.volkdown.octopus.utils

import ru.volkdown.octopus.FeatureOwner
import java.util.*

fun generateFeatureId(): String = UUID.randomUUID().toString()

fun generateFeatureId(owner: FeatureOwner, uniqueFeatureValue: String): String = owner.id + "_" + uniqueFeatureValue