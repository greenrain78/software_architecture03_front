package com.example.system

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.system.ingredientsDB.Ingredient
import com.example.system.ingredientsDB.IngredientRepository
import kotlinx.coroutines.launch
import java.time.LocalDate

class IngredientViewModel(private val ingredientRepository: IngredientRepository) : ViewModel() {
    var ingredientUiState by mutableStateOf(Ingredient(0, "name", 1, LocalDate.of(1970, 1, 1), "url"))
        private set

    fun updateIngredientUiState(ingredient: Ingredient){
        ingredientUiState = ingredient
    }

    fun insertIngredient(){
        viewModelScope.launch {
            ingredientRepository.insertIngredient(ingredientUiState)
        }
    }
}