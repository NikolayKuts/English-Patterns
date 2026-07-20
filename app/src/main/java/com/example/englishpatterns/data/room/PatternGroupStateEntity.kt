package com.example.englishpatterns.data.room

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "pattern_group_state")
data class PatternGroupStateEntity(
    @PrimaryKey val groupKey: String,
    val isChosen: Boolean,
    val markColor: String,
)
