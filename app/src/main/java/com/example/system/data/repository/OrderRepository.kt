package com.example.system.data.repository

import android.content.Context
import com.example.system.data.remote.api.request.order.OrderRequest
import com.example.system.data.remote.network.ServiceProvider
import com.example.system.ingredientsDB.Ingredient
import com.example.system.ingredientsDB.IngredientDB
import com.example.system.ingredientsDB.IngredientDao

class OrderRepository {
    private val orderService = ServiceProvider.getOrderServiceInstance()

    suspend fun order(ingredient: Ingredient) {
        orderService.postOrder(
            OrderRequest(
                "name",
                ingredient.name,
                ingredient.quantity
            )
        )
    }

    suspend fun register(ingredient: Ingredient) {
    }

}