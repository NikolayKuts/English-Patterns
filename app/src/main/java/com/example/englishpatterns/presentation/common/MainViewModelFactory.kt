package com.example.englishpatterns.presentation.common

import androidx.datastore.core.DataStore
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.englishpatterns.data.RowPatternGroupHolders


class MainViewModelFactory(
    private val dataStore: DataStore<RowPatternGroupHolders>,
) : ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return MainViewModel(dataStore = dataStore) as T
    }
}