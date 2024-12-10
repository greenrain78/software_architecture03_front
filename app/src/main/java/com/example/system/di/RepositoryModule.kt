package com.example.system.di

import com.example.system.data.repository.IngredientRepository
import com.example.system.data.repository.OrderRepository
import com.example.system.data.repository.RecipeRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {
    @Binds
    @Singleton
    abstract fun bindRecipeRepository(
        recipeRepository: RecipeRepository
    ): RecipeRepository

    @Binds
    @Singleton
    abstract fun bindIngredientRepository(
        ingredientRepository: IngredientRepository
    ): IngredientRepository

    @Binds
    @Singleton
    abstract fun bindOrderRepository(
        orderRepository: OrderRepository
    ): OrderRepository

}