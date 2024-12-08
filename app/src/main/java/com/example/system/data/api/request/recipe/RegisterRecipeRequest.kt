package com.example.system.data.api.request.recipe


data class RegisterRecipeRequest(
    val description: String,
    val ingredients: List<IngredientRequest>,
    val name: String,
    val writer: String
)

data class IngredientRequest(
    val amount: Int,
    val name: String
)