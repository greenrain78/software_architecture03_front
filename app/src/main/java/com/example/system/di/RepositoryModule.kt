package com.example.system.di

import com.example.system.data.repository.IngredientRepository
import com.example.system.data.repository.OrderRepository
import com.example.system.data.repository.RecipeRepository
import com.example.system.ingredientsDB.IngredientDao
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Inject
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {
    @Provides
    @Singleton
    fun bindRecipeRepository(
    ): RecipeRepository {
        return RecipeRepository()
    }

    @Provides
    @Singleton
    fun bindIngredientRepository(
        ingredientDao: IngredientDao
    ): IngredientRepository {
        return IngredientRepository(
            ingredientDao
        )
    }

//    @Binds
    @Provides
    @Singleton
    fun bindOrderRepository(
    ): OrderRepository {
        return OrderRepository()
    }
}

