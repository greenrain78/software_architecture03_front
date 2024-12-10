package com.example.system.di

import android.content.Context
import androidx.room.Room
import com.example.system.ingredientsDB.IngredientDB
import com.example.system.ingredientsDB.IngredientDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Singleton
    @Provides
    fun provideIngredientDataBase(@ApplicationContext context: Context): IngredientDB {
        return Room.databaseBuilder(
            context.applicationContext,
            IngredientDB::class.java,
            "database"
        ).build()
    }

    @Provides
    fun provideIngredientDao(database: IngredientDB): IngredientDao = database.IngredientDao()
}