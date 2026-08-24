package com.example.englishpatterns.presentation.common

import android.app.Application
import com.lib.lokdroid.core.LoKdroid
import com.lib.lokdroid.data.default_implementation.FormatterBuilder

class App : Application() {

    override fun onCreate() {
        super.onCreate()

        LoKdroid.initialize(
            formatter = FormatterBuilder()
                .withPointer()
                .space()
                .withLineReference()
                .space()
                .message()
                .build()
        )
    }
}
