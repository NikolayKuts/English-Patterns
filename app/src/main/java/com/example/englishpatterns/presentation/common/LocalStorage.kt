package com.example.englishpatterns.presentation.common

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import kotlin.properties.ReadWriteProperty
import kotlin.reflect.KProperty

private val Context.localStorage: DataStore<Preferences> by preferencesDataStore(name = "localStorage")

class LocalStorage private constructor(context: Context) {

    companion object {

        private val ruClipTextKey = stringPreferencesKey(name = "ruClipText")
        private val enClipTextKey = stringPreferencesKey(name = "enClipTextKey")
        private val selectedClipTextKey = stringPreferencesKey(name = "selectedClipTextKey")

        private var instance: LocalStorage? = null

        fun getInstance(context: Context): LocalStorage = synchronized(this) {
            return instance ?: LocalStorage(context = context).also { instance = it }
        }
    }

    private val localStorage = context.localStorage

    var ruClipText: String by customDelicate(key = ruClipTextKey)
    var enClipText: String by customDelicate(key = enClipTextKey)
    var selectedClipText: String by customDelicate(key = selectedClipTextKey)

    private fun customDelicate(
        key: Preferences.Key<String>,
        defaultValue: String = ""
    ): ReadWriteProperty<Any?, String> = object : ReadWriteProperty<Any?, String> {

        override fun getValue(thisRef: Any?, property: KProperty<*>): String = runBlocking {
            localStorage.data.first()[key] ?: defaultValue
        }

        override fun setValue(thisRef: Any?, property: KProperty<*>, value: String) {
            runBlocking {
                localStorage.edit { it[key] = value }
            }
        }
    }
}