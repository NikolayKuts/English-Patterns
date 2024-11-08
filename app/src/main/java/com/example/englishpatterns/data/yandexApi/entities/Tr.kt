package com.example.englishpatterns.data.yandexApi.entities

import kotlinx.serialization.Serializable

@Serializable
data class Tr(
    val asp: String? = null,
    val fr: Int,
    val mean: List<Mean>? = null,
    val pos: String? = null,
    val syn: List<Syn>? = null,
    val text: String,
)