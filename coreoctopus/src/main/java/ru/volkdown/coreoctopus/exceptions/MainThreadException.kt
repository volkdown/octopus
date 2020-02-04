package ru.volkdown.coreoctopus.exceptions

/**
 * Describe [Exception] about action in not main thread
 */
class MainThreadException(message: String) : RuntimeException(message)