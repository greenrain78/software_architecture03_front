package com.example.system.ingredientsDB

import android.app.Application
import androidx.compose.runtime.MutableLongState
import androidx.lifecycle.LiveData
import kotlinx.coroutines.flow.Flow
import java.time.LocalDate

class IngredientRepository(private val ingredientDao: IngredientDao) {
    suspend fun insertIngredient(ingredient: Ingredient) = ingredientDao.insert(ingredient)

    suspend fun updateIngredient(ingredient: Ingredient) = ingredientDao.update(ingredient)

    suspend fun deleteIngredient(ingredient: Ingredient) = ingredientDao.delete(ingredient)

    suspend fun deleteAllIngredients() = ingredientDao.deleteAllIngredients()

    suspend fun getIngredientList() : List<Ingredient> = ingredientDao.getAllIngredients()

    suspend fun getExpiredIngredientList(expiredDate: Long) : List<Ingredient> = ingredientDao.getExpiredIngredients(expiredDate)

    suspend fun getWarningIngredientList(warningStartDate: Long, warningEndDate: Long) : List<Ingredient> = ingredientDao.getWarningIngredients(warningStartDate, warningEndDate)
}