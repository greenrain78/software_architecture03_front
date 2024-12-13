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
import com.example.system.ingredientsDB.fromLocalDate
import com.example.system.data.repository.IngredientRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.time.LocalDate
import javax.inject.Inject

@HiltViewModel
class IngredientViewModel @Inject constructor(
    private val ingredientRepository: IngredientRepository
) : ViewModel() {

    private val image: ImageLoader = ImageLoader()

    private val _ingredientList = MutableStateFlow<List<Ingredient>>(emptyList())
    val ingredientList: StateFlow<List<Ingredient>> = _ingredientList

    private val _expiredIngredientList = MutableStateFlow<List<Ingredient>>(emptyList())
    val expiredIngredientList: StateFlow<List<Ingredient>> = _ingredientList

    private val _ingredientUiState = MutableStateFlow<List<Ingredient>>(emptyList())
    val ingredientUiState: StateFlow<List<Ingredient>> = _ingredientUiState

    private val _autoOrderUiState = MutableStateFlow<List<Ingredient>>(emptyList())
    val autoOrderUiState: StateFlow<List<Ingredient>> = _autoOrderUiState

    fun getAutoOrderIngredients() {
        viewModelScope.launch {
            _autoOrderUiState.value = ingredientRepository.getAutoOrderIngredient()
        }
    }

    fun takeoutIngredient() {
        viewModelScope.launch {
            /*
            if (_ingredientUiState.value.quantity == 0)
                ingredientRepository.removeIngredient(_ingredientUiState.value)
            else {
                ingredientRepository.updateIngredient(_ingredientUiState.value)
            }
        }

             */
        }
    }

    fun addAutoOrder() {

    }

    fun deleteAutoOrder() {

    }

    fun updateIngredient() {
        viewModelScope.launch {
            //ingredientRepository.updateIngredient(_ingredientUiState.value)
        }
    }

    fun removeIngredient() {
        viewModelScope.launch {
            //ingredientRepository.removeIngredient(_ingredientUiState.value)
        }
    }

    fun updateIngredientUiState(newIngredientUiState: List<Ingredient>) {
        _ingredientUiState.value = newIngredientUiState
    }

    fun addIngredientUiState() {
        _ingredientUiState.value += Ingredient()
    }


    fun getIngredients() {
        viewModelScope.launch {
            _ingredientList.value = ingredientRepository.getAll()
        }
    }

    fun addIngredients() {
        viewModelScope.launch {
            for (ingredient in _ingredientUiState.value) {
                ingredientRepository.add(ingredient)
            }

            updateIngredientUiState(emptyList())
        }
    }

    fun getExpiredIngredients() {
        viewModelScope.launch {
            _expiredIngredientList.value = fromLocalDate(LocalDate.now())?.let {
                ingredientRepository.getExpiredIngredients(
                    it
                )
            }!!
        }
    }

    fun captureIngredient(
        context: Context,
        cameraLauncher: ManagedActivityResultLauncher<Void?, Bitmap?>
    ) {
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
}