package com.example.system.ingredientsDB

import android.app.Application
import androidx.lifecycle.LiveData
import kotlinx.coroutines.flow.Flow
import java.time.LocalDate

class IngredientRepository(private val ingredientDao: IngredientDao) {
    suspend fun insertIngredient(ingredient: Ingredient) = ingredientDao.insert(ingredient)

    suspend fun updateIngredient(ingredient: Ingredient) = ingredientDao.update(ingredient)

    suspend fun deleteIngredient(ingredient: Ingredient) = ingredientDao.delete(ingredient)

    fun getIngredientList() : Flow<List<Ingredient>> = ingredientDao.getAllIngredients()
}