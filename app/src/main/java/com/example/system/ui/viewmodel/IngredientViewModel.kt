package com.example.system.ui.viewmodel

import android.content.Context
import android.graphics.Bitmap
import android.net.Uri
import android.util.Log
import androidx.activity.compose.ManagedActivityResultLauncher
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

class IngredientViewModel(private val ingredientRepository: IngredientRepository) : ViewModel() {

    private val image: ImageLoader = ImageLoader()

    private val _ingredients = MutableStateFlow<List<Ingredient>>(emptyList())
    val ingredients: StateFlow<List<Ingredient>> = _ingredients

    private val _capturedIngredient = MutableStateFlow<Ingredient>(Ingredient())
    val capturedIngredient: StateFlow<Ingredient> = _capturedIngredient

    private val _expirationIngredients = MutableStateFlow<List<Ingredient>>(emptyList())
    val expirationIngredients: StateFlow<List<Ingredient>> = _expirationIngredients


    fun getIngredients() {
        viewModelScope.launch {
            _ingredients.value = ingredientRepository.getAll()
        }
    }

    fun getCapturedIngredient(
        context: Context,
        cameraLauncher: ManagedActivityResultLauncher<Void?, Bitmap?>
    ){
        image.captureImage(context, cameraLauncher)
    }

    fun recognizeIngredientFromImage() {
        val imageUri = image.getImageUri()

        if (imageUri != Uri.EMPTY) {
            viewModelScope.launch {
                //capturedIngredient.value.name = ingredientRepository.getIngredientName(imageUri)
            }
        }
    }

    fun takeoutIngredient(ingredient: Ingredient) {
        viewModelScope.launch {
            if (ingredient.quantity == 0)
                ingredientRepository.removeIngredient(ingredient)
            else {
                ingredientRepository.updateIngredient(ingredient)
            }
        }
    }

    fun addIngredients(ingredient: Ingredient) {
        viewModelScope.launch {
            ingredientRepository.add(ingredient)
        }
    }

    fun getExpirationIngredients() {
        viewModelScope.launch {
            _expirationIngredients.value = fromLocalDate(LocalDate.now())?.let {
                ingredientRepository.getExpiredIngredients(it)
            }!!
        }
    }
}