package com.example.system.ui.viewmodel

import android.content.Context
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableLongStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.system.ingredientsDB.Ingredient
import com.example.system.ingredientsDB.IngredientRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class IngredientViewModel(context: Context) : ViewModel() {

    private val ingredientRepository: IngredientRepository =
        IngredientRepository(context)

    var ingredientUiState by mutableStateOf(Ingredient(0, "name", 1, 0, "url"))
        private set
    var expiredDateUiState by mutableLongStateOf(0)
    var warningStartDateUiState by mutableLongStateOf(0)
    var warningEndDateUiState by mutableLongStateOf(0)

    private val _ingredientList = MutableStateFlow<List<Ingredient>>(emptyList())
    private val _expiredIngredientList = MutableStateFlow<List<Ingredient>>(emptyList())
    private val _warningIngredientList = MutableStateFlow<List<Ingredient>>(emptyList())

    val ingredientList: StateFlow<List<Ingredient>> = _ingredientList.asStateFlow()
    val expiredIngredientList: StateFlow<List<Ingredient>> = _expiredIngredientList.asStateFlow()
    val warningIngredientList: StateFlow<List<Ingredient>> = _warningIngredientList.asStateFlow()

    fun updateIngredientUiState(ingredient: Ingredient){
        ingredientUiState = ingredient
    }

    fun updateExpiredDateUiState(expiredDate: Long) {
        expiredDateUiState = expiredDate
    }

    fun updateWarningDateUiState(startDate: Long, endDate: Long) {
        warningStartDateUiState = startDate
        warningEndDateUiState = endDate
    }

    fun insertIngredient(){
        viewModelScope.launch {
            ingredientRepository.insertIngredient(ingredientUiState)
        }
    }

    fun deleteIngredient(){
        viewModelScope.launch {
            ingredientRepository.deleteIngredient(ingredientUiState)
        }
    }

    fun deleteAllIngredients() {
        viewModelScope.launch {
            ingredientRepository.deleteAllIngredients()
        }
    }

    fun getIngredientList(){
        viewModelScope.launch {
            _ingredientList.value = ingredientRepository.getIngredientList()
        }
    }

    fun getExpiredIngredientList(){
        viewModelScope.launch {
            _expiredIngredientList.value =
                ingredientRepository.getExpiredIngredientList(expiredDateUiState)
        }
    }

    fun getWarningIngredientList(){
        viewModelScope.launch {
            _warningIngredientList.value =
                ingredientRepository.getWarningIngredientList(warningStartDateUiState, warningEndDateUiState)
        }
    }
}