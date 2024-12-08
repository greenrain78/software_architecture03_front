package com.example.system.data.remote.api

import com.example.system.data.remote.api.request.recipe.RegisterRecipeRequest
import com.example.system.data.remote.api.response.recipe.RecipeResponse
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface RecipeService {

    //    suspend fun getRecommendedReceipt(): RecommendedReceipt
    @GET("recipe")
    suspend fun getRecipe(): RecipeResponse

    @POST("recipe")
    suspend fun registerRecipe(
        @Body recipe: RegisterRecipeRequest
    )

    @PUT("recipe/{id}")
    suspend fun putRecipe(
        @Body recipe: RegisterRecipeRequest,
        @Path ("id") id: Int
    )
}