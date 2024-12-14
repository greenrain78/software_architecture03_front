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



    //식재료 추가 화면 입력 칸 UI 상태 관리
    private val _ingredientAddUiState = MutableStateFlow<Ingredient>(Ingredient())
    val ingredientAddUiState: StateFlow<Ingredient> = _ingredientAddUiState

    //식재료 추가 화면 찍은 사진 보여주는 UI 상태 관리
    //ai서버로 데이터 보내는 용도
    private val _capturedImageUri = MutableStateFlow<Uri>(Uri.EMPTY)
    val capturedImageUri: StateFlow<Uri> = _capturedImageUri
    
    //UI에 이미지 표시하는 용도
    private val _capturedImageBitmap = MutableStateFlow<Bitmap?>(null)
    val capturedImageBitmap: StateFlow<Bitmap?> = _capturedImageBitmap



    private val _ingredientTakeOutUiState = MutableStateFlow<List<Int>>(emptyList())
    val ingredientTakeOutUiState: StateFlow<List<Int>> = _ingredientTakeOutUiState

    private val _ingredientExpirationUiState = MutableStateFlow<List<Ingredient>>(emptyList())
    val ingredientExpirationUiState: StateFlow<List<Ingredient>> = _ingredientExpirationUiState

    private val _autoOrderList = MutableStateFlow<List<OrderItem>>(emptyList())
    val autoOrderList: StateFlow<List<OrderItem>> = _autoOrderList

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




    //식재료 추가 화면 UI 관련 함수
    //텍스트 필드 UI 입력한 경우
    fun updateIngredientAddUiState(
        name: String = _ingredientAddUiState.value.name,
        quantity: Int = _ingredientAddUiState.value.quantity,
        expirationDate: Long = _ingredientAddUiState.value.expirationDate
    ) {
        _ingredientAddUiState.value.name = name
        _ingredientAddUiState.value.quantity = quantity
        _ingredientAddUiState.value.expirationDate = expirationDate
    }

    //식재료 추가 버튼 누른 경우
    fun addIngredient() {
        viewModelScope.launch {
            ingredientRepository.add(_ingredientAddUiState.value)
            _ingredientAddUiState.value = Ingredient()
        }
    }
    
    //카메라로 등록하기 버튼 누른 경우
    fun captureIngredient(
        context: Context,
        cameraLauncher: ManagedActivityResultLauncher<Void?, Bitmap?>
    ) {
        image.captureImage(context, cameraLauncher)
        _capturedImageUri.value = image.getImageUri()
    }

    //UI에 표시되는 bitmap 이미지 변경
    fun updateCapturedImageBitmapState(bitmap: Bitmap?) {
        _capturedImageBitmap.value = bitmap
    }

    //카메라 찍고 다시 안 찍고 확인을 누르는 경우 AI서버로부터 식재료의 이름을 인식
    fun recognizeIngredientFromImage() {
        if (_capturedImageUri.value != Uri.EMPTY) {
            viewModelScope.launch {
                //_ingredientAddUiState.value.name = ingredientRepository.getIngredientName(imageUri)
                _ingredientAddUiState.value.name = "사과"
            }
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
            _autoOrderList.value = ingredientRepository.getAllAutoOrderItems()
            _autoOrderUiState.value = ingredientRepository.getAllAutoOrderItems()
        }
    }

    fun changeOrderUiState(autoOrderList: List<OrderItem>){
        _autoOrderUiState.value = autoOrderList
    }


    fun addAutoOrder() {
        _autoOrderUiState.value += OrderItem(name = "")
    }

    fun insertAutoOrder() {
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





}