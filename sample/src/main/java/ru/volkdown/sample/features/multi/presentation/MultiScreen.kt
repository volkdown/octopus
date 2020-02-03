package ru.volkdown.sample.features.multi.presentation

import androidx.fragment.app.Fragment
import ru.terrakok.cicerone.android.support.SupportAppScreen

class MultiScreen : SupportAppScreen(){

    override fun getFragment(): Fragment = MultiFragment.newInstance()
}