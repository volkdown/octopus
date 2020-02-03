package ru.volkdown.sample.features.app.presentation

import ru.volkdown.sample.base.BasePresenter
import ru.volkdown.sample.base.BaseView
import ru.volkdown.sample.navigation.NavigationContainer
import javax.inject.Inject

class AppPresenter @Inject constructor(private val navigationContainer: NavigationContainer): BasePresenter<BaseView>(null){

    val router = navigationContainer.getRouter("app")

    override fun onDestroy() {
        navigationContainer.removeCicerone("app")
        super.onDestroy()
    }
}