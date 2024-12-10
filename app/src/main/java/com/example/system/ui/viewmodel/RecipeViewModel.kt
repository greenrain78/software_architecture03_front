package com.example.system.ui.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.system.data.model.Recipe
import com.example.system.data.repository.RecipeRepository
import com.example.system.ingredientsDB.Ingredient
import com.example.system.data.repository.IngredientRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RecipeViewModel @Inject constructor(
    private val recipeRepository: RecipeRepository,
    private val ingredientRepository: IngredientRepository
) : ViewModel() {

//    private val recipeRepository = RecipeRepository()
//    private val ingredientRepository = IngredientRepository()


    private val _uiState = MutableStateFlow(Recipe())
    val uiState: StateFlow<Recipe> = _uiState.asStateFlow()

    init {
        getRecipes()
    }

    private var _recipeList = MutableStateFlow<List<Recipe>>(emptyList())
    val recipeList: StateFlow<List<Recipe>> = _recipeList.asStateFlow()

    private fun getRecipes() =
        viewModelScope.launch {
            val a = recipeRepository.getRecipes()
            _recipeList.value = a
        }

    suspend fun postRecipe(ingredients: List<Ingredient>, name: String, description: String) =
        viewModelScope.launch {
            recipeRepository.registerRecipe(ingredients, name, description)
        }

    suspend fun modifyRecipe(ingredients: List<Ingredient>, id: Int, name: String, description: String) =
        viewModelScope.launch {
            recipeRepository.putRecipe(ingredients, id, name, description)
        }

}