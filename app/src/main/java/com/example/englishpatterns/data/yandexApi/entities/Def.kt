package com.example.englishpatterns.data.yandexApi.entities

import kotlinx.serialization.Serializable

@Serializable
data class Def(
    val fl: String? = null,
    val pos: String? = null,
    val text: String,
    val tr: List<Tr>,
    val ts: String? = null
)