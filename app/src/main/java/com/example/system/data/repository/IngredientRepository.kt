package com.example.system.data.repository

import android.net.Uri
import com.example.system.data.remote.api.request.ai.AIRequest
import com.example.system.data.remote.network.ServiceProvider
import com.example.system.ingredientsDB.Ingredient
import com.example.system.ingredientsDB.IngredientDao
import com.example.system.ingredientsDB.OrderItem
import com.example.system.ingredientsDB.OrderItemDao
import javax.inject.Inject

class IngredientRepository @Inject constructor(
    private val ingredientDao: IngredientDao,
    private val orderItemDao: OrderItemDao
) {

    private val openAIService = ServiceProvider.getOpenAIServiceInstance()

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

    // GPT 요청 보내는 로직
    suspend fun getPrompt(imageUri: Uri): String? {

        val request = AIRequest().apply {
            this.messages[0].content[0].imageUrl.url = imageUri
        }

        val response = openAIService.recognizeImage(request)

        return response.body()?.choices?.get(0)?.message?.content
    }


    suspend fun addAutoOrder(orderItem: OrderItem) = orderItemDao.insert(orderItem)

    suspend fun updateAutoOrder(orderItem: OrderItem) = orderItemDao.update(orderItem)

    suspend fun deleteAutoOrder(orderItem: OrderItem) = orderItemDao.delete(orderItem)

    suspend fun getAllAutoOrderItems(): List<OrderItem> = orderItemDao.getAutoOrderItems()
}
