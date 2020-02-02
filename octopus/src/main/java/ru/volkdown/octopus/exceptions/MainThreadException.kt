package ru.volkdown.octopus.exceptions

/**
 * Describe [Exception] about action in not main thread
 */
class MainThreadException(message: String) : RuntimeException(message)