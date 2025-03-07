package com.example.englishpatterns.presentation.common

import androidx.datastore.core.DataStore
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.createSavedStateHandle
import androidx.lifecycle.viewmodel.CreationExtras
import com.example.englishpatterns.data.PatternGroupResContainers
import com.example.englishpatterns.presentation.patternPractisingScreen.PracticingPatternGroup


class MainViewModelFactory(
    private val patternStorage: DataStore<PatternGroupResContainers>,
    private val weekPatternStorage: DataStore<PracticingPatternGroup>,
) : ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(
        modelClass: Class<T>,
        extras: CreationExtras,
    ): T = MainViewModel(
        patternStore = patternStorage,
        weekPatternStorage = weekPatternStorage,
        savedStateHandle = extras.createSavedStateHandle()
    ) as T
}