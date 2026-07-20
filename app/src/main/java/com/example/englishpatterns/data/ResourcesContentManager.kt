package com.example.englishpatterns.data

import android.content.Context
import android.content.res.Resources

class ResourcesContentManager(
    private val context: Context,
    private val patternRepository: PatternRepository,
) {

    suspend fun getStringArray(id: Int): Array<String> = try {
        context.resources.getStringArray(id)
    } catch (e: Resources.NotFoundException) {
        patternRepository.getWeekPatterns().map {
            "${it.native}==${it.translation}"
        }.toTypedArray()
    }
}
