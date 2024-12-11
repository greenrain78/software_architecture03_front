package com.example.system.ui.viewmodel

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.system.data.model.Recipe
import com.example.system.data.repository.RecipeRepository
import com.example.system.ingredientsDB.Ingredient
import com.example.system.ingredientsDB.IngredientDao
import com.example.system.ingredientsDB.IngredientRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class RecipeViewModel(context: Context) : ViewModel() {
/*
    private val recipeRepository = RecipeRepository()
    private val ingredientRepository = IngredientRepository(context)

    private val _uiState = MutableStateFlow(Recipe())
    val uiState: StateFlow<Recipe> = _uiState.asStateFlow()

    private var _recipeList = MutableStateFlow<List<Recipe>>(emptyList())
    val recipeList: StateFlow<List<Recipe>> = _recipeList.asStateFlow()

    fun getRecipes() =
        viewModelScope.launch {
            _recipeList.value = recipeRepository.getRecipes()
        }

    fun postRecipe(ingredients: List<Ingredient>, name: String, description: String) =
        viewModelScope.launch {
            recipeRepository.registerRecipe(ingredients, name, description)
        }

    fun modifyRecipe(ingredients: List<Ingredient>, id: Int, name: String, description: String) =
        viewModelScope.launch {
            recipeRepository.putRecipe(ingredients, id, name, description)
        }


 */
}