package com.example.system.ingredientsDB

import android.app.Application
import android.content.Context
import androidx.compose.runtime.MutableLongState
import androidx.lifecycle.LiveData
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import java.time.LocalDate

class IngredientRepository(context: Context) {


    private val ingredientDao: IngredientDao =
        IngredientDB.getDatabase(context).IngredientDao()

    suspend fun insertIngredient(ingredient: Ingredient) = ingredientDao.insert(ingredient)

    suspend fun updateIngredient(ingredient: Ingredient) = ingredientDao.update(ingredient)

    suspend fun deleteIngredient(ingredient: Ingredient) = ingredientDao.delete(ingredient)

    suspend fun deleteAllIngredients() = ingredientDao.deleteAllIngredients()

    suspend fun getIngredientList(): List<Ingredient> = ingredientDao.getAllIngredients()

    suspend fun getExpiredIngredientList(expiredDate: Long): List<Ingredient> =
        ingredientDao.getExpiredIngredients(expiredDate)

    suspend fun getWarningIngredientList(
        warningStartDate: Long,
        warningEndDate: Long
    ): List<Ingredient> = ingredientDao.getWarningIngredients(warningStartDate, warningEndDate)
}