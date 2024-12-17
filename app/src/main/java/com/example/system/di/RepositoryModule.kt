package com.example.system.di

import com.example.system.data.repository.IngredientRepository
import com.example.system.data.repository.OrderRepository
import com.example.system.data.repository.RecipeRepository
import com.example.system.ingredientsDB.IngredientDao
import com.example.system.ingredientsDB.OrderItemDao
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
        ingredientDao: IngredientDao,
        orderItemDao: OrderItemDao
    ): IngredientRepository {
        return IngredientRepository(
            ingredientDao,
            orderItemDao
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

