package com.example.system.ingredientsDB

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import dagger.hilt.android.qualifiers.ApplicationContext

@Database(entities = [Ingredient::class], version = 1)
abstract class IngredientDB : RoomDatabase() {
    abstract fun IngredientDao() : IngredientDao

    companion object {
        @Volatile
        private var INSTANCE: IngredientDB? = null

        fun getDatabase(context: Context): IngredientDB {
            return INSTANCE ?: synchronized(this) {
                Room.databaseBuilder(
                    context.applicationContext,
                    IngredientDB::class.java,
                    "database"
                ).build().also {
                    INSTANCE = it
                }
            }
        }
    }
}