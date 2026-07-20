package com.example.englishpatterns.data

import com.example.englishpatterns.data.room.PatternGroupStateDao
import com.example.englishpatterns.data.room.PatternGroupStateEntity
import com.example.englishpatterns.data.room.WeekPatternDao
import com.example.englishpatterns.data.room.WeekPatternEntity
import com.example.englishpatterns.domain.MarkColor
import com.example.englishpatterns.domain.PatternGroupResContainer
import com.example.englishpatterns.domain.storageKey
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class PatternRepository(
    private val patternGroupStateDao: PatternGroupStateDao,
    private val weekPatternDao: WeekPatternDao,
) {

    private val defaultContainers: List<PatternGroupResContainer> = PatternGroupResContainers.Default.content

    private val defaultContainersByKey: Map<String, PatternGroupResContainer> =
        defaultContainers.associateBy { it.patternGroupResource.storageKey }

    fun observePatternGroupResContainers(): Flow<PatternGroupResContainers> {
        return patternGroupStateDao.observeAll().map { states ->
            val statesByKey = states.associateBy { it.groupKey }

            PatternGroupResContainers(
                content = defaultContainers.map { defaultContainer ->
                    val state = statesByKey[defaultContainer.patternGroupResource.storageKey]
                    if (state != null) {
                        defaultContainer.copy(
                            patternGroupResource = defaultContainer.patternGroupResource.toNew(
                                markColor = state.markColor.toMarkColor()
                            ),
                            isChosen = state.isChosen
                        )
                    } else {
                        defaultContainer
                    }
                }
            )
        }
    }

    suspend fun setPatternGroupChosen(groupKey: String, isChosen: Boolean) {
        val defaultContainer = defaultContainersByKey.getValue(groupKey)
        val currentState = patternGroupStateDao.getByKey(groupKey = groupKey)

        patternGroupStateDao.upsert(
            PatternGroupStateEntity(
                groupKey = groupKey,
                isChosen = isChosen,
                markColor = currentState?.markColor
                    ?: defaultContainer.patternGroupResource.markColor.toDbValue()
            )
        )
    }

    suspend fun setPatternGroupMarkColor(groupKey: String, markColor: MarkColor) {
        val defaultContainer = defaultContainersByKey.getValue(groupKey)
        val currentState = patternGroupStateDao.getByKey(groupKey = groupKey)

        patternGroupStateDao.upsert(
            PatternGroupStateEntity(
                groupKey = groupKey,
                isChosen = currentState?.isChosen ?: defaultContainer.isChosen,
                markColor = markColor.toDbValue()
            )
        )
    }

    fun observeWeekPatterns(): Flow<List<Pattern>> {
        return weekPatternDao.observeAll().map { entities ->
            entities.map { entity -> entity.toPattern() }
        }
    }

    suspend fun getWeekPatterns(): List<Pattern> {
        return weekPatternDao.getAll().map { entity -> entity.toPattern() }
    }

    suspend fun addWeekPattern(pattern: Pattern): Boolean {
        return weekPatternDao.insert(WeekPatternEntity.fromPattern(pattern)) != -1L
    }

    private fun MarkColor.toDbValue(): String = when (this) {
        MarkColor.Accented -> "accented"
        MarkColor.Highlighted -> "highlighted"
        MarkColor.Non -> "non"
        MarkColor.Positive -> "positive"
        MarkColor.Warning -> "warning"
    }

    private fun String.toMarkColor(): MarkColor = when (this) {
        "accented" -> MarkColor.Accented
        "highlighted" -> MarkColor.Highlighted
        "positive" -> MarkColor.Positive
        "warning" -> MarkColor.Warning
        else -> MarkColor.Non
    }
}
