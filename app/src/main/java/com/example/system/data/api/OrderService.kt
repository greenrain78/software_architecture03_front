package com.example.system.data.api

import com.example.system.data.api.request.order.OrderRequest
import com.example.system.data.api.response.order.OrderResponse
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface OrderService {

    @GET("order/user")
    suspend fun getOrders(
        @Query("customerName ") customerName: String
    ): OrderResponse

    @POST("orders")
    suspend fun postOrder(
        @Body order: OrderRequest
    )
}