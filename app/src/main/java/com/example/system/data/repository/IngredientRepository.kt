package com.example.system.data.repository

import com.example.system.ingredientsDB.Ingredient
import com.example.system.ingredientsDB.IngredientDao

class IngredientRepository(private val ingredientDao: IngredientDao) {

    //private val serviceProvider: ServiceProvider = ServiceProvider

    suspend fun getAll(): List<Ingredient> = ingredientDao.getAll()

    suspend fun removeIngredient(ingredient: Ingredient) = ingredientDao.delete(ingredient)

    suspend fun add(ingredient: Ingredient) = ingredientDao.insert(ingredient)

    suspend fun getExpiredIngredients(expiredDate: Long): List<Ingredient> =
        ingredientDao.getExpiredIngredients(expiredDate)

    suspend fun updateIngredient(ingredient: Ingredient) = ingredientDao.update(ingredient)

    suspend fun getAutoOrderIngredient() = ingredientDao.getAutoOrderIngredients()

    //suspend fun getIngredientName(imageUri: Uri) = serviceProvider.getOpenAIServiceInstance().getIngredientName()


    // 사용 x
    suspend fun getWarningIngredientList(
        warningStartDate: Long,
        warningEndDate: Long
    ): List<Ingredient> = ingredientDao.getWarningIngredients(warningStartDate, warningEndDate)

    // 사용 x
    suspend fun deleteAllIngredients() = ingredientDao.deleteAllIngredients()
}
