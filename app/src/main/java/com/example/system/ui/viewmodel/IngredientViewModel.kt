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
import com.example.system.ingredientsDB.OrderItem
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
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
    val expiredIngredientList: StateFlow<List<Ingredient>> = _expiredIngredientList


    private val _ingredientAddUiState = MutableStateFlow<List<Ingredient>>(emptyList())
    val ingredientAddUiState: StateFlow<List<Ingredient>> = _ingredientAddUiState

    private val _ingredientTakeOutUiState = MutableStateFlow<List<Int>>(emptyList())
    val ingredientTakeOutUiState: StateFlow<List<Int>> = _ingredientTakeOutUiState

    private val _ingredientExpirationUiState = MutableStateFlow<List<Ingredient>>(emptyList())
    val ingredientExpirationUiState: StateFlow<List<Ingredient>> = _ingredientExpirationUiState

    private val _autoOrderUiState = MutableStateFlow<List<OrderItem>>(emptyList())
    val autoOrderUiState: StateFlow<List<OrderItem>> = _autoOrderUiState




    fun getIngredients() {
        viewModelScope.launch {
            _ingredientList.value = ingredientRepository.getAll()
        }
    }

    fun getExpiredIngredients() {
        viewModelScope.launch {
            _expiredIngredientList.value = ingredientRepository.getExpiredIngredients(fromLocalDate(LocalDate.now())!!)
        }
    }





    fun addIngredientAddUiState() {
        _ingredientAddUiState.value += Ingredient()
    }

    fun updateIngredientAddUiState(index: Int, ingredient: Ingredient) {
        val updatedIngredientList = _ingredientAddUiState.value.toMutableList()
        updatedIngredientList[index] = ingredient
        _ingredientAddUiState.value = updatedIngredientList
    }

    fun addIngredients() {
        viewModelScope.launch {
            for (ingredient in _ingredientAddUiState.value) {
                ingredientRepository.add(ingredient)
            }

            _ingredientAddUiState.value = emptyList()
        }
    }




    fun initIngredientTakeOutUiState() {
        _ingredientTakeOutUiState.value = List(_ingredientList.value.size) { 0 }
    }

    fun updateIngredientTakeOutUiState(index: Int, quantity: Int) {
        val updatedIngredientList = _ingredientTakeOutUiState.value.toMutableList()

        if (updatedIngredientList.isNotEmpty())
            updatedIngredientList[index] = quantity

        _ingredientTakeOutUiState.value = updatedIngredientList
    }

    fun takeoutIngredient() {
        viewModelScope.launch {
            _ingredientList.value.forEachIndexed { index, ingredient ->
                val withdrawQuantity = _ingredientTakeOutUiState.value[index]
                val updatedQuantity = ingredient.quantity - withdrawQuantity

                if (updatedQuantity == 0) {
                    ingredientRepository.removeIngredient(ingredient)
                    getIngredients()
                } else {
                    ingredientRepository.updateIngredient(ingredient.copy(quantity = updatedQuantity))
                    getIngredients()
                }
            }

            initIngredientTakeOutUiState()
        }
    }





    fun initIngredientExpirationUiState() {
        _ingredientExpirationUiState.value = _ingredientList.value
    }

    fun updateIngredientExpirationUiState(index: Int, ingredient: Ingredient) {
        val updatedIngredientList = _ingredientExpirationUiState.value.toMutableList()

        if (updatedIngredientList.isNotEmpty())
            updatedIngredientList[index] = ingredient

        _ingredientExpirationUiState.value = updatedIngredientList
    }

    fun changeIngredientExpiration() {
        viewModelScope.launch {
            for (ingredient in _ingredientExpirationUiState.value) {
                ingredientRepository.updateIngredient(ingredient)
            }

            getIngredients()
        }
    }





    fun getAutoOrderItems() {
        viewModelScope.launch {
            _autoOrderUiState.value = ingredientRepository.getAllAutoOrderItems()
        }
    }

    fun changeOrderUiState(autoOrderList: List<OrderItem>){
        _autoOrderUiState.value = autoOrderList
    }


    fun addAutoOrder() {
        viewModelScope.launch {
            for (orderItem in _autoOrderUiState.value)
                ingredientRepository.addAutoOrder(orderItem)

            getAutoOrderItems()
        }
    }

    fun updateAutoOrder() {
        viewModelScope.launch {
            for (orderItem in _autoOrderUiState.value)
                ingredientRepository.updateAutoOrder(orderItem)

            getAutoOrderItems()
        }
    }

    fun deleteAutoOrder() {
        viewModelScope.launch {
            for (orderItem in _autoOrderUiState.value)
                ingredientRepository.deleteAutoOrder(orderItem)

            getAutoOrderItems()
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