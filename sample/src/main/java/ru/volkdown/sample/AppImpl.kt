package ru.volkdown.sample

import android.app.Application
import ru.volkdown.sample.di.ApplicationComponent
import ru.volkdown.sample.di.ApplicationProvider

class AppImpl : Application(), App {

    private val applicationProvider: ApplicationComponent by lazy { ApplicationComponent.build() }

    override fun onCreate() {
        super.onCreate()
    }

    override fun getApplicationProvider(): ApplicationProvider = applicationProvider
}