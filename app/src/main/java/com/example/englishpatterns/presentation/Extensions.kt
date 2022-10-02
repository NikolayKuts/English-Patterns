package com.example.englishpatterns.presentation

import android.util.Log

fun <T> log(
    message: T,
    pointerMessage: String = "",
    tag: String = "app_log",
    pointer: String =
        if (pointerMessage.isEmpty()) "***********" else "****** $pointerMessage ******",
) {
    Log.i(tag, "$pointer $message")
}