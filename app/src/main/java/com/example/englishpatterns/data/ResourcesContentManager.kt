package com.example.englishpatterns.data

import android.content.Context
import android.content.res.Resources
import kotlinx.coroutines.flow.firstOrNull

class ResourcesContentManager(private val context: Context) {

    suspend fun getStringArray(id: Int): Array<String> = try {
        context.resources.getStringArray(id)
    } catch (e: Resources.NotFoundException) {
        context.weekPatternStorage.data.firstOrNull()?.patterns?.map {
            "${it.native}==${it.translation}"
        }?.toTypedArray()
            ?: emptyArray()
    }
}