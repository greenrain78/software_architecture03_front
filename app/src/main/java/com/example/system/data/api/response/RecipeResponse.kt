package com.example.system.data.api.response

class ReceiptResponse : ArrayList<RecipeResponseItem>()

data class RecipeResponseItem(
    val createdAt: String,
    val description: String,
    val id: Int,
    val ingredients: List<IngredientResponse>,
    val name: String,
    val updatedAt: String,
    val writer: String
)

