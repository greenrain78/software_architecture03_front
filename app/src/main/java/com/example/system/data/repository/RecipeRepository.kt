package com.example.system.data.repository

import com.example.system.data.remote.api.request.recipe.IngredientRequest
import com.example.system.data.remote.api.request.recipe.RegisterRecipeRequest
import com.example.system.data.mapper.RecipeMapper.toRecipeList
import com.example.system.data.model.Recipe
import com.example.system.data.remote.network.ServiceProvider
import com.example.system.ingredientsDB.Ingredient

class RecipeRepository {
    private val recipeService = ServiceProvider.getRecipeServiceInstance()
//    fun getRecommendedrecipe() {
//        receiptService.getRecommendedrecipe()
//    }

    suspend fun getRecipes(): List<Recipe> {
        return recipeService.getRecipe().toRecipeList()
    }

    suspend fun registerRecipe(
        ingredients: List<Ingredient>,
        name: String,
        description: String
    ) {
        val recipeRequest =
            RegisterRecipeRequest(
                name = name,
                ingredients = ingredients.map { ingredient ->
                    IngredientRequest(
                        ingredient.quantity,
                        ingredient.name
                    )
                },
                description = description,
                writer = "writer" // TODO: writer
            )
        recipeService.registerRecipe(recipeRequest)
    }


    suspend fun putRecipe(
        ingredients: List<Ingredient>,
        id: Int,
        name: String,
        description: String
    ) {
        val recipeRequest =
            RegisterRecipeRequest(
                name = name,
                ingredients = ingredients.map { ingredient ->
                    IngredientRequest(
                        ingredient.quantity,
                        ingredient.name
                    )
                },
                description = description,
                writer = "writer" // TODO: writer
            )
        recipeService.putRecipe(
            recipe = recipeRequest, id = id
        )
    }
}