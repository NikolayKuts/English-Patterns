package com.example.englishpatterns.data.yandexApi

import com.example.englishpatterns.data.SecretConstants
import com.example.englishpatterns.data.common.LoadingState
import com.example.englishpatterns.presentation.patternPractisingScreen.SelectedTextInfo
import com.example.englishpatterns.data.yandexApi.entities.YandexWordInfo
import com.lib.lokdroid.core.logD
import com.lib.lokdroid.core.logW
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.engine.cio.CIO
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.defaultRequest
import io.ktor.client.request.get
import io.ktor.serialization.kotlinx.json.json
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.serialization.json.Json

class YandexWordInfoProvider {

    companion object {

        private const val BASE_URL = "https://dictionary.yandex.net/"
        private const val PATH = "api/v1/dicservice.json/lookup"
        private const val TIMEOUT = 10000L
    }

    private val client = HttpClient(CIO) {
        install(ContentNegotiation) {
            json(
                Json {
                    prettyPrint = true
                    isLenient = true
                    ignoreUnknownKeys = true
                }
            )
        }
        defaultRequest { url(urlString = BASE_URL) }
        engine { requestTimeout = TIMEOUT }
    }

    fun fetchTextInfo(word: String): Flow<LoadingState<SelectedTextInfo>> = flow {
        emit(value = LoadingState.Loading)

        val apiKey = SecretConstants.YandexApi.YANDEX_WORD_INFO_API_KEY
        val url = buildUrl(apiKey = apiKey, word = word)
        val yandexWordInfoAsString = client.get(url).body<String>()
        val wordInfo = client.get(url).body<YandexWordInfo>().toDomainEntity()

        emit(value = LoadingState.Success(data = wordInfo))

        logD {
            message("fetchWordInfo() called")
            message("wordInfo = $yandexWordInfoAsString")
        }
    }.catch { throwable ->
        logW("fetchWordInfo caught ERROR: ${throwable.stackTraceToString()}")
    }

    private fun buildUrl(apiKey: String, word: String): String {
        return "$PATH?key=$apiKey&lang=en-ru&text=$word"
    }
}

fun YandexWordInfo.toDomainEntity(): SelectedTextInfo = SelectedTextInfo(
    transcription = def?.firstOrNull()?.ts ?: "",
    translations = def?.flatMap { def -> def.tr.map { tr -> tr.text } } ?: emptyList()
)