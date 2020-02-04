package ru.volkdown.coreoctopus.utils

import ru.volkdown.coreoctopus.FeatureOwner
import java.util.*

fun generateFeatureId(): String = UUID.randomUUID().toString()

fun generateFeatureId(owner: FeatureOwner, uniqueFeatureValue: String): String = owner.id + "_" + uniqueFeatureValue