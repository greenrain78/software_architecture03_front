package com.example.system.ingredientsDB

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
//import dagger.hilt.android.qualifiers.ApplicationContext

@Database(entities = [Ingredient::class, OrderItem::class], version = 1)
abstract class IngredientDB : RoomDatabase() {
    abstract fun IngredientDao() : IngredientDao
    abstract fun OrderItemDao(): OrderItemDao
}