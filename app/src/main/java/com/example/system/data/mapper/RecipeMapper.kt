package com.example.system.data.mapper

import com.example.system.data.api.response.recipe.RecipeResponse
import com.example.system.data.mapper.IngredientMapper.toIngredient
import com.example.system.data.model.Recipe

object RecipeMapper {
    fun RecipeResponse.toRecipeList(): List<Recipe> {
        val recipeList = mutableListOf<Recipe>()
        this.forEach {
            recipeList.add(
                Recipe(
                    name = it.name,
                    ingredients = it.ingredients.map { ingredientResponse ->
                        ingredientResponse.toIngredient()
                    },
                    description = it.description
                )
            )
        }
        return recipeList
    }
}