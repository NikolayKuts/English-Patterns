package com.example.englishpatterns.data.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(
    entities = [
        PatternGroupStateEntity::class,
        WeekPatternEntity::class,
    ],
    version = 1,
    exportSchema = false
)
abstract class EnglishPatternsDatabase : RoomDatabase() {

    abstract fun patternGroupStateDao(): PatternGroupStateDao

    abstract fun weekPatternDao(): WeekPatternDao

    companion object {

        @Volatile
        private var instance: EnglishPatternsDatabase? = null

        fun getInstance(context: Context): EnglishPatternsDatabase {
            return instance ?: synchronized(this) {
                instance ?: Room.databaseBuilder(
                    context = context.applicationContext,
                    klass = EnglishPatternsDatabase::class.java,
                    name = "english_patterns.db"
                ).build().also { instance = it }
            }
        }
    }
}
