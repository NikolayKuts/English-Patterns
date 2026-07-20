package com.example.englishpatterns.data.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface PatternGroupStateDao {

    @Query("SELECT * FROM pattern_group_state")
    fun observeAll(): Flow<List<PatternGroupStateEntity>>

    @Query("SELECT * FROM pattern_group_state WHERE groupKey = :groupKey LIMIT 1")
    suspend fun getByKey(groupKey: String): PatternGroupStateEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsert(patternGroupStateEntity: PatternGroupStateEntity)
}
