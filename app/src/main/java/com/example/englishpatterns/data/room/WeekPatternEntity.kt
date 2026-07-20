package com.example.englishpatterns.data.room

import androidx.room.Entity
import com.example.englishpatterns.data.Pattern

@Entity(
    tableName = "week_pattern",
    primaryKeys = ["nativeText", "translationText"]
)
data class WeekPatternEntity(
    val nativeText: String,
    val translationText: String,
    val addedAt: Long = System.currentTimeMillis(),
) {

    fun toPattern(): Pattern = Pattern(
        native = nativeText,
        translation = translationText
    )

    companion object {

        fun fromPattern(pattern: Pattern): WeekPatternEntity = WeekPatternEntity(
            nativeText = pattern.native,
            translationText = pattern.translation
        )
    }
}
