package com.example.system.data.api.response.recipe


data class RecommendedRecipe(
    val ingredients: List<IngredientResponse>
)


data class IngredientResponse(
    val amount: Int,
    val name: String
)