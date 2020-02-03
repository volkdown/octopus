package ru.volkdown.sample.features.single.presentation

import androidx.fragment.app.Fragment
import ru.terrakok.cicerone.android.support.SupportAppScreen

class SingleScreen : SupportAppScreen(){

    override fun getFragment(): Fragment = SingleFragment.newInstance()
}