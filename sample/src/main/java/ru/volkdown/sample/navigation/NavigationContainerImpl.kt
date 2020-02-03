package ru.volkdown.sample.navigation

import ru.terrakok.cicerone.Cicerone
import ru.terrakok.cicerone.NavigatorHolder
import ru.terrakok.cicerone.Router
import javax.inject.Inject

class NavigationContainerImpl @Inject constructor() : NavigationContainer {
    private val cicerones = HashMap<String, Cicerone<Router>>()

    override fun getRouter(key: String): Router {
        return cicerones.getOrPut(key) { Cicerone.create() }.router
    }

    override fun getNavigatorHolder(key: String): NavigatorHolder {
        return cicerones.getOrPut(key) { Cicerone.create() }.navigatorHolder
    }

    override fun removeCicerone(key: String) {
        cicerones.remove(key)
    }

}