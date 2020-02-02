package ru.volkdown.octopus.utils

import ru.volkdown.octopus.exceptions.MainThreadException
import java.util.*

class Threads {

    companion object {

        /**
         * Проверяет что поток главный
         */
        fun checkThreadIsMain() {
            if (Thread.currentThread().name != "main") {
                throw sanitizeStackTrace(MainThreadException("The current method can only be called from the main thread."))
            }
        }

        private fun <T : Throwable> sanitizeStackTrace(throwable: T): T {
            return sanitizeStackTrace(throwable, Companion::class.java.name)
        }

        /**
         * Убирает из стек трейса класс с указанным названием
         *
         * @param classNameToDrop название класса который нужно убрать
         */
        private fun <T : Throwable> sanitizeStackTrace(throwable: T, classNameToDrop: String): T {
            val stackTrace = throwable.stackTrace
            val size = stackTrace.size

            var lastIntrinsic = -1
            for (i in 0 until size) {
                if (classNameToDrop == stackTrace[i].className) {
                    lastIntrinsic = i
                }
            }

            val newStackTrace = Arrays.copyOfRange(stackTrace, lastIntrinsic + 1, size)
            throwable.stackTrace = newStackTrace
            return throwable
        }
    }
}


