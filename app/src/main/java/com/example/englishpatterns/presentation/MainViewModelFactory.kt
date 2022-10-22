package com.example.englishpatterns.presentation

import android.app.Application
import androidx.datastore.core.DataStore
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.englishpatterns.data.PatternHolders


class MainViewModelFactory(
    private val context: Application,
    private val dataStore: DataStore<PatternHolders>,
) : ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return MainViewModel(context = context, dataStore = dataStore) as T
    }
}