package ru.volkdown.sample

import ru.volkdown.sample.di.ApplicationProvider


interface App {

    fun getApplicationProvider(): ApplicationProvider
}