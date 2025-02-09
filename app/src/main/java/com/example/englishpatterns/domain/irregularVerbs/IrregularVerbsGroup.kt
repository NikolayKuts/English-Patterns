package com.example.englishpatterns.domain.irregularVerbs

import com.example.englishpatterns.domain.irregularVerbs.IrregularVerbGroupType.FullyChanging
import com.example.englishpatterns.domain.irregularVerbs.IrregularVerbGroupType.PartiallyChanging
import com.example.englishpatterns.domain.irregularVerbs.IrregularVerbGroupType.PartiallyConsistent
import com.example.englishpatterns.domain.irregularVerbs.IrregularVerbGroupType.Unchanging

sealed class IrregularVerbsGroup(val type: IrregularVerbGroupType) {

    interface SubGroupProvider<T> {

        val subGroups: List<T>
    }

    interface SubGroupNameProvider {

        val name: String
    }

    interface SubGroupDetailsProvider {

        val details: List<VerbDetails>
    }

    operator fun plus(other: IrregularVerbsGroup): List<IrregularVerbsGroup> {
        return listOf(this) + listOf(other)
    }

    data class Unchanging(val details: List<VerbDetails>) : IrregularVerbsGroup(type = Unchanging)

    data class PartiallyChanging(val details: List<VerbDetails>) :
        IrregularVerbsGroup(type = PartiallyChanging)

    data class FullyChanging(
        val first: SubGroup.First,
        val second: SubGroup.Second,
        val third: SubGroup.Third,
        val fourth: SubGroup.Fourth,
        val fifth: SubGroup.Fifth,
        val sixth: SubGroup.Sixth,
        val seventh: SubGroup.Seventh,
    ) : IrregularVerbsGroup(type = FullyChanging), SubGroupProvider<FullyChanging.SubGroup> {

        sealed class SubGroup(
            override val name: String,
            override val details: List<VerbDetails>
        ): SubGroupNameProvider, SubGroupDetailsProvider {

            data class First(
                val data: List<VerbDetails>,
            ) : SubGroup(name = "First", details = data)

            data class Second(
                val data: List<VerbDetails>,
            ) : SubGroup(name = "X_anX_uXX", details = data)

            data class Third(
                val data: List<VerbDetails>,
            ) : SubGroup(name = "X_oXe_Xen", details = data)

            data class Fourth(
                val data: List<VerbDetails>,
            ) : SubGroup(name = "IXe_oXe_Xen", details = data)

            data class Fifth(
                val data: List<VerbDetails>,
            ) : SubGroup(name = "Fifth", details = data)

            data class Sixth(
                val data: List<VerbDetails>,
            ) : SubGroup(name = "X_ew_wn", details = data)

            data class Seventh(
                val data: List<VerbDetails>,
            ) : SubGroup(name = "X_aXe_Xen", details = data)
        }

        override val subGroups
            get() = listOf(
                first,
                second,
                third,
                fourth,
                fifth,
                sixth,
                seventh,
            )
    }

    data class PartiallyConsistent(
        val first: SubGroup.First,
        val second: SubGroup.Second,
        val third: SubGroup.Third,
        val fourth: SubGroup.Fourth,
        val fifth: SubGroup.Fifth,
        val sixth: SubGroup.Sixth,
        val seventh: SubGroup.Seventh,
        val eighth: SubGroup.Eighth,
    ) : IrregularVerbsGroup(type = PartiallyConsistent),
        SubGroupProvider<PartiallyConsistent.SubGroup> {

        sealed class SubGroup(
            override val name: String,
            override val details: List<VerbDetails>
        ) : SubGroupNameProvider, SubGroupDetailsProvider {

            data class First(
                val data: List<VerbDetails>
            ) : SubGroup(name = "x_o_o", details = data)

            data class Second(
                val data: List<VerbDetails>
            ) : SubGroup(name = "x_e_e_1", details = data)

            data class Third(
                val data: List<VerbDetails>
            ) : SubGroup(name = "x_e_e_2", details = data)

            data class Fourth(
                val data: List<VerbDetails>
            ) : SubGroup(name = "x_found_found", details = data)

            data class Fifth(
                val data: List<VerbDetails>
            ) : SubGroup(name = "el_ould_ould", details = data)

            data class Sixth(
                val data: List<VerbDetails>
            ) : SubGroup(name = "stand_ud_ud", details = data)

            data class Seventh(
                val data: List<VerbDetails>
            ) : SubGroup(name = "shuffled_1", details = data)

            data class Eighth(
                val data: List<VerbDetails>
            ) : SubGroup(name = "shuffled_2", details = data)
        }

        override val subGroups: List<SubGroup>
            get() = listOf(
                first,
                second,
                third,
                fourth,
                fifth,
                sixth,
                seventh,
                eighth,
            )
    }
}