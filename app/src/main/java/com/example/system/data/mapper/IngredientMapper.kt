package com.example.system.data.mapper

import com.example.system.data.remote.api.response.recipe.IngredientResponse
import com.example.system.ingredientsDB.Ingredient

object IngredientMapper {
    fun IngredientResponse.toIngredient(): Ingredient {
        return Ingredient(
            name = name,
            quantity = amount
        )
    }
}