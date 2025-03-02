package com.example.englishpatterns.presentation.navigation

import android.net.Uri
import android.os.Bundle
import androidx.navigation.NavType
import com.example.englishpatterns.data.common.Constants
import com.example.englishpatterns.domain.PatternGroupResource
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

object CustomNavType {

    val PatternGroupResources: NavType<List<PatternGroupResource>> =
        object : NavType<List<PatternGroupResource>>(
            isNullableAllowed = false
        ) {
            override fun get(bundle: Bundle, key: String): List<PatternGroupResource>? {
                return Json.decodeFromString(bundle.getString(key) ?: return null)
            }

            override fun parseValue(value: String): List<PatternGroupResource> {
                return Json.decodeFromString(Uri.decode(value).decodePercentSymbol())
            }

            override fun serializeAsValue(value: List<PatternGroupResource>): String {
                return Uri.encode(Json.encodeToString(value).encodePercentSymbol())
            }

            override fun put(bundle: Bundle, key: String, value: List<PatternGroupResource>) {
                bundle.putString(key, Json.encodeToString(value))
            }
        }

    private fun String.encodePercentSymbol(): String {
        return this.replace("%", Constants.Uri.ENCODED_PERCENT_SYMBOL)
    }

    private fun String.decodePercentSymbol(): String {
        return this.replace(Constants.Uri.ENCODED_PERCENT_SYMBOL, "%")
    }
}