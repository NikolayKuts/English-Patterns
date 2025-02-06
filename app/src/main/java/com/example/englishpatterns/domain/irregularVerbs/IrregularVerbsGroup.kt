package com.example.englishpatterns.domain.irregularVerbs

import com.example.englishpatterns.domain.irregularVerbs.IrregularVerbGroupType.FullyChanging
import com.example.englishpatterns.domain.irregularVerbs.IrregularVerbGroupType.PartiallyChanging
import com.example.englishpatterns.domain.irregularVerbs.IrregularVerbGroupType.PartiallyConsistent
import com.example.englishpatterns.domain.irregularVerbs.IrregularVerbGroupType.Unchanging

sealed class IrregularVerbsGroup(val type: IrregularVerbGroupType) {

    operator fun plus(other: IrregularVerbsGroup): List<IrregularVerbsGroup> {
        return listOf(this) + listOf(other)
    }

    data class Unchanging(val details: List<VerbDetails>) : IrregularVerbsGroup(type = Unchanging)

    data class PartiallyChanging(val details: List<VerbDetails>) :
        IrregularVerbsGroup(type = PartiallyChanging)

    data class FullyChanging(
        val first: SubGroup.First,
        val x_anX_uXX: SubGroup.X_anX_uXX,
        val x_oXe_Xen: SubGroup.X_oXe_Xen,
        val iXe_oXe_Xen: SubGroup.IXe_oXe_Xen,
        val some: SubGroup.Some,
        val x_ew_wn: SubGroup.X_ew_wn,
        val x_aXe_Xen: SubGroup.X_aXe_Xen,
    ) : IrregularVerbsGroup(type = FullyChanging) {

        sealed class SubGroup(val name: String, val details: List<VerbDetails>) {

            data class First(val data: List<VerbDetails>) : SubGroup(name = "First", details = data)
            data class X_anX_uXX(val data: List<VerbDetails>) :
                SubGroup(name = "X_anX_uXX", details = data)

            data class X_oXe_Xen(val data: List<VerbDetails>) :
                SubGroup(name = "X_oXe_Xen", details = data)

            data class IXe_oXe_Xen(val data: List<VerbDetails>) :
                SubGroup(name = "IXe_oXe_Xen", details = data)

            data class Some(val data: List<VerbDetails>) : SubGroup(name = "Fifth", details = data)

            data class X_ew_wn(val data: List<VerbDetails>) :
                SubGroup(name = "X_ew_wn", details = data)

            data class X_aXe_Xen(val data: List<VerbDetails>) :
                SubGroup(name = "X_aXe_Xen", details = data)
        }

        val subGroups get() = listOf(
            first,
            x_anX_uXX,
            x_oXe_Xen,
            iXe_oXe_Xen,
            some,
            x_ew_wn,
            x_aXe_Xen,
        )
    }

    data class PartiallyConsistent(val details: List<VerbDetails>) :
        IrregularVerbsGroup(type = PartiallyConsistent)
}