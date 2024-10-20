package com.example.englishpatterns.presentation.navigation

import android.net.Uri
import android.os.Bundle
import androidx.navigation.NavType
import com.example.englishpatterns.domain.RawPatternGroup
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

object CustomNavType {

    val RawPatternGroupList: NavType<List<RawPatternGroup>> = object : NavType<List<RawPatternGroup>>(
        isNullableAllowed = false
    ) {
        override fun get(bundle: Bundle, key: String): List<RawPatternGroup>? {
            return Json.decodeFromString(bundle.getString(key) ?: return null)
        }

        override fun parseValue(value: String): List<RawPatternGroup> {
            return Json.decodeFromString(Uri.decode(value))
        }

        override fun serializeAsValue(value: List<RawPatternGroup>): String {
            return Uri.encode(Json.encodeToString(value))
        }

        override fun put(bundle: Bundle, key: String, value: List<RawPatternGroup>) {
            bundle.putString(key, Json.encodeToString(value))
        }
    }
}