package com.example.englishpatterns.data.common

sealed interface Constants {

    data object WordHunt : Constants {

        const val BASE_URL = "https://wooordhunt.com/word/"
        const val AUDIO_URI_TEMPLATE = "https://wooordhunt.ru/data/sound/sow/us/%s.mp3"
    }

    data object Klaf : Constants {

        const val PACKAGE_NAME = "com.kuts.klaf"
    }

    sealed interface Intent : Constants {

        data object Type : Intent {

            const val TEXT_PLAIN = "text/plain"
        }
    }

    data object ChatGpt : Constants {

        const val BASE_URL = "https://www.chatgpt.com/c/"
        const val CLIPBOARD_CLIP_DATA_LABEL = "sentence"
    }

    data object YouGlish : Constants {

        const val BASE_URL_WITH_PLACEHOLDER = "https://youglish.com/pronounce/%s/english/us"
    }

    data object Google : Constants {

        const val BASE_URL_WITH_PLACEHOLDER = "https://www.google.com/search?q=%s&tbm=isch"
    }
}