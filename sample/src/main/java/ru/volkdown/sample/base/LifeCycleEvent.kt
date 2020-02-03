package ru.volkdown.sample.base

import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.Fragment

/**
 *  Описаны lifecycle события, которые могут возникнуть в [AppCompatActivity], [Fragment], [DialogFragment]
 */
enum class LifeCycleEvent {
    ATTACH,
    CREATE,
    CREATE_VIEW,
    START,
    RESUME,
    PAUSE,
    STOP,
    DESTROY_VIEW,
    DESTROY,
    DETACH
}