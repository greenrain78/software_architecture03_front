package com.example.system.data.repository

import com.example.system.ingredientsDB.Ingredient
import com.example.system.ingredientsDB.IngredientDao
import com.example.system.ingredientsDB.OrderItem
import javax.inject.Inject

class IngredientRepository @Inject constructor(
    private val ingredientDao: IngredientDao
) {

    //private val serviceProvider: ServiceProvider = ServiceProvider

    suspend fun getAll(): List<Ingredient> = ingredientDao.getAll()

    suspend fun removeIngredient(ingredient: Ingredient) = ingredientDao.delete(ingredient)

    suspend fun add(ingredient: Ingredient) = ingredientDao.insert(ingredient)

    suspend fun getExpiredIngredients(expiredDate: Long): List<Ingredient> =
        ingredientDao.getExpiredIngredients(expiredDate)

    suspend fun updateIngredient(ingredient: Ingredient) = ingredientDao.update(ingredient)

    //suspend fun getIngredientName(imageUri: Uri) = serviceProvider.getOpenAIServiceInstance().getIngredientName()




    suspend fun addAutoOrder(orderItem: OrderItem) = ingredientDao.insertAutoOrderItem(orderItem)

    suspend fun updateAutoOrder(orderItem: OrderItem) = ingredientDao.updateAutoOrderItem(orderItem)

    suspend fun deleteAutoOrder(orderItem: OrderItem) = ingredientDao.deleteAutoOrderItem(orderItem)

    suspend fun getAllAutoOrderItems() : List<OrderItem> = ingredientDao.getAutoOrderItems()



    // 사용 x
    suspend fun deleteAllIngredients() = ingredientDao.deleteAllIngredients()
}
