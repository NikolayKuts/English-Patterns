package com.example.englishpatterns.presentation.patternPractisingScreen

sealed interface PatternPracticingAction {

    data class ChangePatternGroupHolderChoosingState(val position: Int) : PatternPracticingAction

    data object NextPatter : PatternPracticingAction

    data object PreviousPatter : PatternPracticingAction

    data object ShufflePatternPairs : PatternPracticingAction

    data object ChangeAllPatternGroupHoldersSelectionState : PatternPracticingAction

    data object SelectNextPatternGroup : PatternPracticingAction

    data object SelectPreviousPatternGroup : PatternPracticingAction

    data object AddPatternAsWeaklyMemorized : PatternPracticingAction

    data object ChangeTranslationVisibilityState: PatternPracticingAction

    data class TextPronunciationRequired(val text: String) : PatternPracticingAction

    data class SelectedTextInfoRequired(val text: String) : PatternPracticingAction

    data class SelectedTextSearchRequired(val text: String) : PatternPracticingAction

    data class RedirectionToKlafAppRequired(val text: String) : PatternPracticingAction

    data class RedirectionToChatGptAppRequired(val text: String) : PatternPracticingAction

    data class RedirectionToYouGlishPageRequired(val text: String) : PatternPracticingAction
}