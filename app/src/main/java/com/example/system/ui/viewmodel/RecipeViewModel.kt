package com.example.system.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.system.data.repository.RecipeRepository
import com.example.system.ingredientsDB.Ingredient
import com.example.system.ingredientsDB.IngredientDao
import com.example.system.ingredientsDB.IngredientRepository
import kotlinx.coroutines.launch

class RecipeViewModel : ViewModel() {

    private val recipeRepository = RecipeRepository()


    fun getRecipes() =
        viewModelScope.launch {
            recipeRepository.getRecipes()
        }

    // TODO : IngredientRepository 에서 ingredients 를 가져와야 함
    fun postRecipe(ingredients: List<Ingredient>, name: String, description: String) =
        viewModelScope.launch {
            recipeRepository.registerRecipe(ingredients, name, description)
        }

    fun modifyRecipe(ingredients: List<Ingredient>, id: Int, name: String, description: String) =
        viewModelScope.launch {
            recipeRepository.putRecipe(ingredients, id, name, description)
        }

}