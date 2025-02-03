package com.example.englishpatterns.domain.irregularVerbs

sealed interface HidingMode {

    data object Non : HidingMode

    data object Third : HidingMode

    data object Second : HidingMode

    data object SecondAndThird : HidingMode

    data class ForcedShow(val previousMode: HidingMode) : HidingMode
}