package com.example.system.data.repository

import com.example.system.ingredientsDB.Ingredient
import com.example.system.ingredientsDB.IngredientDao
import com.example.system.ingredientsDB.OrderItem
import com.example.system.ingredientsDB.OrderItemDao
import javax.inject.Inject

class IngredientRepository @Inject constructor(
    private val ingredientDao: IngredientDao,
    private val orderItemDao: OrderItemDao
) {

    //private val serviceProvider: ServiceProvider = ServiceProvider

    suspend fun getAll(): List<Ingredient> = ingredientDao.getAll()

    suspend fun removeIngredient(ingredient: Ingredient, callback: () -> Unit) {
        ingredientDao.delete(ingredient)
        callback()
    }

    suspend fun add(ingredient: Ingredient) = ingredientDao.insert(ingredient)

    suspend fun getExpiredIngredients(expiredDate: Long): List<Ingredient> =
        ingredientDao.getExpiredIngredients(expiredDate)

    suspend fun updateIngredient(ingredient: Ingredient, callback: () -> Unit) {
        ingredientDao.update(ingredient)
        callback()
    }

    //suspend fun getIngredientName(imageUri: Uri) = serviceProvider.getOpenAIServiceInstance().getIngredientName()

    //더미 데이터 용
    suspend fun deleteAll() = ingredientDao.deleteAll()



    suspend fun addAutoOrder(orderItem: OrderItem) = orderItemDao.insert(orderItem)

    suspend fun updateAutoOrder(orderItem: OrderItem) = orderItemDao.update(orderItem)

    suspend fun deleteAutoOrder(orderItem: OrderItem) = orderItemDao.delete(orderItem)

    suspend fun getAllAutoOrderItems() : List<OrderItem> = orderItemDao.getAutoOrderItems()
}
