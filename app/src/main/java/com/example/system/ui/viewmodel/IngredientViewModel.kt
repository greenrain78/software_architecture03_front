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

    private val _ingredientUiState = MutableStateFlow(Ingredient())
    val ingredientUiState: StateFlow<Ingredient> = _ingredientUiState

    // TODO : 자동 주문되는 식재료만 불러오는 기능
    var autoOrderUiState = MutableStateFlow(emptyList<Ingredient>())

    fun getAutoOrderIngredients() {

    }
    // TODO : 자동 주문 추가
    fun addAutoOrder() {

    }

    // TODO : 자동 주문 삭제
    fun removeAutoOrder() {

    }

    fun updateIngredientUiState(ingredient: Ingredient) {
        _ingredientUiState.value = ingredient
    }


    fun getIngredients() {
        viewModelScope.launch {
            _ingredientList.value = ingredientRepository.getAll()
        }
    }

    fun addIngredients() {
        viewModelScope.launch {
            ingredientRepository.add(_ingredientUiState.value)
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

    fun takeoutIngredient() {
        viewModelScope.launch {
            if (_ingredientUiState.value.quantity == 0)
                ingredientRepository.removeIngredient(_ingredientUiState.value)
            else {
                ingredientRepository.updateIngredient(_ingredientUiState.value)
            }
        }
    }
}