package com.example.system.ingredientsDB

import android.content.Context

class IngredientRepository(context: Context) {

    private val ingredientDao: IngredientDao =
        IngredientDB.getDatabase(context).IngredientDao()

    suspend fun getAll(): List<Ingredient> = ingredientDao.getAll()

    suspend fun removeIngredient(ingredient: Ingredient) = ingredientDao.delete(ingredient)

    suspend fun add(ingredient: Ingredient) = ingredientDao.insert(ingredient)

    suspend fun getExpiredIngredients(expiredDate: Long): List<Ingredient> =
        ingredientDao.getExpiredIngredients(expiredDate)

    suspend fun updateIngredient(ingredient: Ingredient) = ingredientDao.update(ingredient)

    // 사용 x
    suspend fun getWarningIngredientList(
        warningStartDate: Long,
        warningEndDate: Long
    ): List<Ingredient> = ingredientDao.getWarningIngredients(warningStartDate, warningEndDate)

    // 사용 x
    suspend fun deleteAllIngredients() = ingredientDao.deleteAllIngredients()
}
