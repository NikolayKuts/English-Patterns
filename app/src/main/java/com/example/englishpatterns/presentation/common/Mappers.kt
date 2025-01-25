package com.example.englishpatterns.presentation.common

import com.example.englishpatterns.domain.MarkColor
import com.example.englishpatterns.presentation.UiMarkColor

fun MarkColor.toUiMarkColor(): UiMarkColor = when (this) {
    MarkColor.Non -> UiMarkColor.MutedOliveGreen
    MarkColor.Accented -> UiMarkColor.SoftOrangePeach
    MarkColor.Warning -> UiMarkColor.WarmCoralRed
    MarkColor.Positive -> UiMarkColor.VividSkyBlue
}

fun UiMarkColor.toMarkColor(): MarkColor = when (this) {
    UiMarkColor.SoftOrangePeach -> MarkColor.Accented
    UiMarkColor.WarmCoralRed -> MarkColor.Warning
    UiMarkColor.VividSkyBlue -> MarkColor.Positive
    UiMarkColor.MutedOliveGreen -> MarkColor.Non
}