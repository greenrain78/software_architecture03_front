package com.example.system.ui.viewmodel

import android.content.Context
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableLongStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.system.cameraApp.ImageLoader
import com.example.system.ingredientsDB.Ingredient
import com.example.system.ingredientsDB.IngredientRepository
import com.example.system.ingredientsDB.fromLocalDate
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.time.LocalDate

class IngredientViewModel(context: Context) : ViewModel() {

    private val ingredientRepository: IngredientRepository = IngredientRepository(context)
    private val image: ImageLoader = ImageLoader()



    suspend fun getIngredients() : List<Ingredient> = ingredientRepository.getAll()

    //suspend fun takeIngredient(s : String): Ingredient = ingredientRepository.getIngredientName(image)

    fun addIngredients(ingredient: Ingredient) {
        viewModelScope.launch {
            ingredientRepository.add(ingredient)
        }
    }

    suspend fun getExpirationIngredients(): List<Ingredient> = fromLocalDate(LocalDate.now())?.let {
        ingredientRepository.getExpiredIngredients(it)
    }!!
}