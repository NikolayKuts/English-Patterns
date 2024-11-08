package com.example.englishpatterns.data.common

sealed interface Constants {

    data object WordHunt : Constants {

        const val BASE_URL = "https://wooordhunt.ru/word/"
        const val AUDIO_URI_TEMPLATE = "https://wooordhunt.ru/data/sound/sow/us/%s.mp3"
    }
}