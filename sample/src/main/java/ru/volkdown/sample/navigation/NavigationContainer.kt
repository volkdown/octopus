package ru.volkdown.sample.navigation

import ru.terrakok.cicerone.NavigatorHolder
import ru.terrakok.cicerone.Router

interface NavigationContainer {

    fun getRouter(key: String): Router

    fun removeCicerone(key: String)

    fun getNavigatorHolder(key: String): NavigatorHolder
}