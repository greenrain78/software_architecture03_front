package com.example.system.data.remote.api.response.recipe

class RecipeResponse : ArrayList<RecipeResponseItem>()

data class RecipeResponseItem(
    val createdAt: String,
    val description: String,
    val id: Int,
    val ingredients: List<IngredientResponse>,
    val name: String,
    val updatedAt: String,
    val writer: String
)

