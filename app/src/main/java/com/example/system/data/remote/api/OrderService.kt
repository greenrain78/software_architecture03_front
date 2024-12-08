package com.example.system.data.remote.api

import com.example.system.data.remote.api.request.order.OrderRequest
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface OrderService {

    @POST("orders")
    suspend fun postOrder(
        @Body order: OrderRequest
    )

    @POST("register")
    suspend fun register(
        @Body order: OrderRequest
    )
}