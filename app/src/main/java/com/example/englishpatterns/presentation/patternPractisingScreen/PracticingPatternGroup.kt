package com.example.englishpatterns.presentation.patternPractisingScreen

import com.example.englishpatterns.data.Pattern
import com.example.englishpatterns.data.IdentifiablePattern
import kotlinx.serialization.Serializable

@Serializable
data class PracticingPatternGroup(
    val patterns: List<Pattern> = emptyList(),
    val isWeaklyMemorized: Boolean = false,
    val isChosen: Boolean = false,
)

@Serializable
data class IdentifiablePracticingPatternGroup(
    val id: Int,
    val identifiablePatterns: List<IdentifiablePattern>,
    val isWeaklyMemorized: Boolean = false,
    val isChosen: Boolean = false,
) {

    fun noId(): PracticingPatternGroup = PracticingPatternGroup(
        patterns = identifiablePatterns.map { it.value },
        isWeaklyMemorized = isWeaklyMemorized,
        isChosen = isChosen
    )
}