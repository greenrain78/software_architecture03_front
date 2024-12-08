package com.example.system.data.api

import com.example.system.data.api.request.RegisterRecipeRequest
import com.example.system.data.api.response.ReceiptResponse
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface RecipeService {

    //    suspend fun getRecommendedReceipt(): RecommendedReceipt
    @GET("recipe")
    suspend fun getReceipt(): ReceiptResponse

    @POST("recipe")
    suspend fun registerReceipt(
        @Body recipe: RegisterRecipeRequest
    )

    @PUT("recipe/{id}")
    suspend fun putReceipt(
        @Body recipe: RegisterRecipeRequest,
        @Path ("id") id: Int
    )
}