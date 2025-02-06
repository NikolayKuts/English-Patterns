package com.example.englishpatterns.presentation.irregularVerbsPractice

import com.example.englishpatterns.domain.irregularVerbs.IrregularVerbGroupType

sealed interface IrregularVerbsGroupViewHolder {

    val type: IrregularVerbGroupType
    val isSelected: Boolean

    data class Common(
        override val type: IrregularVerbGroupType,
        override val isSelected: Boolean = false,
    ) : IrregularVerbsGroupViewHolder

    data class FullyChanging(
        override val type: IrregularVerbGroupType,
        val subGroups: List<FullyChangingSubGroupViewHolder>,
        override val isSelected: Boolean = false
    ) : IrregularVerbsGroupViewHolder
}

data class FullyChangingSubGroupViewHolder(
    val subGroupName: String,
    val isSelected: Boolean = false,
)