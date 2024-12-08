package com.example.system.data.api.response


data class RecommendedRecipe(
    val ingredients: List<IngredientResponse>
)


data class IngredientResponse(
    val amount: Int,
    val name: String
)