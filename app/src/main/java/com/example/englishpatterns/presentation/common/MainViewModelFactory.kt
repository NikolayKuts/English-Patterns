package com.example.englishpatterns.presentation.common

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.createSavedStateHandle
import androidx.lifecycle.viewmodel.CreationExtras
import com.example.englishpatterns.data.PatternRepository


class MainViewModelFactory(
    private val patternRepository: PatternRepository,
) : ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(
        modelClass: Class<T>,
        extras: CreationExtras,
    ): T = MainViewModel(
        patternRepository = patternRepository,
        savedStateHandle = extras.createSavedStateHandle()
    ) as T
}
