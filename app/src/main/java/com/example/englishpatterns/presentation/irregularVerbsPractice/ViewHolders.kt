package com.example.englishpatterns.presentation.irregularVerbsPractice

import com.example.englishpatterns.domain.irregularVerbs.IrregularVerbGroupType

sealed interface IrregularVerbsGroupViewHolder {

    sealed interface SubGroupViewHolderProvider<T: SubGroupViewHolder> {

        val subGroups: List<T>
    }

    sealed interface SubGroupViewHolder {

        val subGroupName: String
        val isSelected: Boolean
    }

    val type: IrregularVerbGroupType
    val isSelected: Boolean

    data class Common(
        override val type: IrregularVerbGroupType,
        override val isSelected: Boolean = false,
    ) : IrregularVerbsGroupViewHolder

    data class FullyChanging(
        override val type: IrregularVerbGroupType,
        override val subGroups: List<FullyChangingSubGroupViewHolder>,
        override val isSelected: Boolean = false
    ) : IrregularVerbsGroupViewHolder, SubGroupViewHolderProvider<FullyChangingSubGroupViewHolder>

    data class PartiallyConsistent(
        override val type: IrregularVerbGroupType,
        override val subGroups: List<PartiallyConsistentSubGroupViewHolder>,
        override val isSelected: Boolean = false
    ) : IrregularVerbsGroupViewHolder, SubGroupViewHolderProvider<PartiallyConsistentSubGroupViewHolder>
}

data class FullyChangingSubGroupViewHolder(
    override val subGroupName: String,
    override val isSelected: Boolean = false,
) : IrregularVerbsGroupViewHolder.SubGroupViewHolder

data class PartiallyConsistentSubGroupViewHolder(
    override val subGroupName: String,
    override val isSelected: Boolean = false,
) : IrregularVerbsGroupViewHolder.SubGroupViewHolder