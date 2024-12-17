package com.example.system.data.repository

import android.content.ContentResolver
import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.util.Base64
import android.util.Log
import androidx.compose.ui.graphics.ImageShader
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import com.example.system.data.remote.api.request.ai.AIRequest
import com.example.system.data.remote.api.request.ai.ImageUrl
import com.example.system.data.remote.network.ServiceProvider
import com.example.system.ingredientsDB.Ingredient
import com.example.system.ingredientsDB.IngredientDao
import com.example.system.ingredientsDB.OrderItem
import com.example.system.ingredientsDB.OrderItemDao
import com.google.gson.Gson
import dagger.Provides
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.ByteArrayOutputStream
import java.io.InputStream
import javax.inject.Inject

class IngredientRepository @Inject constructor(
    private val ingredientDao: IngredientDao,
    private val orderItemDao: OrderItemDao,
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

    //더미 데이터 용
    suspend fun deleteAll() = ingredientDao.deleteAll()
    // GPT 요청 보내는 로직
    suspend fun getPrompt(base64: String): String {

        val request = AIRequest().apply {
            this.messages[0].content[0].imageUrl = ImageUrl(
                "data:image/jpeg;base64,$base64"
            )
        }

        val response = openAIService.recognizeImage(request = request)
        if (response.isSuccessful) {
            val interpretationText = response.body()?.choices?.firstOrNull()
//                    println("Interpretation Text: $interpretationText")
            val message = interpretationText?.message?.content
            Log.d("Interpretation Text", message.toString())
        } else {
            println("ERROR CODE: ${response.code()}")
            println("API request failed: ${response.errorBody()?.string()}")
        }

        return response.body()?.choices?.firstOrNull()?.message?.content ?: ""
    }




    suspend fun addAutoOrder(orderItem: OrderItem) = orderItemDao.insert(orderItem)

    suspend fun updateAutoOrder(orderItem: OrderItem) = orderItemDao.update(orderItem)

    suspend fun deleteAutoOrder(orderItem: OrderItem) = orderItemDao.delete(orderItem)

    suspend fun getAllAutoOrderItems(): List<OrderItem> = orderItemDao.getAutoOrderItems()
}
