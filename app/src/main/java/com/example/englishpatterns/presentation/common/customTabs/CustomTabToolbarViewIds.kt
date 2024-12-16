package com.example.englishpatterns.presentation.common.customTabs

import androidx.annotation.IntegerRes
import com.example.englishpatterns.R

enum class CustomTabToolbarViewIds(@IntegerRes val id: Int) {

    RussianButton(id = R.id.russian_button),
    EnglishButton(id = R.id.english_button),
    SelectedButton(id = R.id.selected_button);

    companion object {

        fun clickableId(): IntArray = intArrayOf(
            RussianButton.id,
            EnglishButton.id,
            SelectedButton.id
        )
    }
}