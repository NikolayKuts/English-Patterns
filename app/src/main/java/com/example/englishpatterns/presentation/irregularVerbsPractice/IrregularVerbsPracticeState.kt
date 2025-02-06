package com.example.englishpatterns.presentation.irregularVerbsPractice

import com.example.englishpatterns.domain.irregularVerbs.HidingMode
import com.example.englishpatterns.domain.irregularVerbs.VerbDetails

data class IrregularVerbsPracticeState(
    val verbsGroupViewHolders: List<IrregularVerbsGroupViewHolder>,
    val currentVerbDetails: VerbDetails? =  null,
    val verbHighlightModeOn: Boolean = false,
    val isShufflingModeOn: Boolean = false,
    val hidingMode: HidingMode = HidingMode.Non
)