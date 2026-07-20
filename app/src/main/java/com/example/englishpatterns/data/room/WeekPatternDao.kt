package com.example.englishpatterns.data.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface WeekPatternDao {

    @Query("SELECT * FROM week_pattern ORDER BY addedAt")
    fun observeAll(): Flow<List<WeekPatternEntity>>

    @Query("SELECT * FROM week_pattern ORDER BY addedAt")
    suspend fun getAll(): List<WeekPatternEntity>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(weekPatternEntity: WeekPatternEntity): Long
}
