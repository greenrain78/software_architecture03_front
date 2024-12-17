package com.example.system.ui.viewmodel

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.util.Base64
import android.util.Log
import androidx.activity.compose.ManagedActivityResultLauncher
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.system.cameraApp.ImageLoader
import com.example.system.ingredientsDB.Ingredient
import com.example.system.ingredientsDB.fromLocalDate
import com.example.system.data.repository.IngredientRepository
import com.example.system.ingredientsDB.OrderItem
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.io.ByteArrayOutputStream
import java.io.InputStream
import java.time.LocalDate
import javax.inject.Inject

@HiltViewModel
class IngredientViewModel @Inject constructor(
    private val ingredientRepository: IngredientRepository,
) : ViewModel() {

    private val image: ImageLoader = ImageLoader()


    //db에 있는 식재료 리스트
    private val _ingredientList = MutableStateFlow<List<Ingredient>>(emptyList())
    val ingredientList: StateFlow<List<Ingredient>> = _ingredientList

    //db에 있는 유통 기한 지난 식재료 리스트
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


    //식재료 꺼내기 화면에서 꺼낼 수량 부분 UI 상태 관리
    private val _ingredientTakeOutUiState = MutableStateFlow<List<Int>>(emptyList())
    val ingredientTakeOutUiState: StateFlow<List<Int>> = _ingredientTakeOutUiState

    //식재료 유통기한 화면에서 변경할 유통 기한 부분 UI 상태 관리
    private val _ingredientExpirationUiState = MutableStateFlow<List<Ingredient>>(emptyList())
    val ingredientExpirationUiState: StateFlow<List<Ingredient>> = _ingredientExpirationUiState


    private val _autoOrderList = MutableStateFlow<List<OrderItem>>(emptyList())
    val autoOrderList: StateFlow<List<OrderItem>> = _autoOrderList

    private val _autoOrderUiState = MutableStateFlow<List<OrderItem>>(emptyList())
    val autoOrderUiState: StateFlow<List<OrderItem>> = _autoOrderUiState


    //식재료 리스트 갱신
    fun getIngredients() {
        viewModelScope.launch {
            _ingredientList.value = ingredientRepository.getAll()
        }
    }

    //유통 기한 지난 식재료 리스트 갱신
    fun getExpiredIngredients() {
        viewModelScope.launch {
            _expiredIngredientList.value =
                ingredientRepository.getExpiredIngredients(fromLocalDate(LocalDate.now())!!)
        }
    }


    //식재료 추가 화면 UI 관련 함수
    //텍스트 필드 UI 입력한 경우
    fun updateIngredientAddUiState(
        name: String = _ingredientAddUiState.value.name,
        quantity: Int = _ingredientAddUiState.value.quantity,
        expirationDate: Long = _ingredientAddUiState.value.expirationDate,
        uri: Uri = Uri.EMPTY
    ) {
        _ingredientAddUiState.value = _ingredientAddUiState.value.copy(
            name = name,
            quantity = quantity,
            expirationDate = expirationDate,
            uri = uri.toString()
        )
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

    //카메라 찍은 후 확인을 누르는 경우 AI서버로부터 식재료의 이름을 인식
    fun recognizeIngredientFromImage(
        imageUri: Uri,
        context: Context
    ) {
        if (_capturedImageUri.value != Uri.EMPTY) {
            viewModelScope.launch {

                val base64 = uriToBase64(imageUri, context) ?: ""

                val ingredientName = ingredientRepository.getPrompt(base64)
                _ingredientAddUiState.value = _ingredientAddUiState.value.copy(name = ingredientName)
                updateIngredientAddUiState(uri = imageUri)
            }
        }
    }

    private fun uriToBase64(
        imageUri: Uri,
        context: Context
    ): String? {
        _capturedImageUri.value = imageUri

        return try {
            // 1. URI에서 Bitmap을 가져오기
            val contentResolver = context.contentResolver
            val inputStream: InputStream? = contentResolver.openInputStream(imageUri)
            val bitmap = BitmapFactory.decodeStream(inputStream)
            inputStream?.close()

            // 2. Bitmap을 Base64로 변환
            val outputStream = ByteArrayOutputStream()
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, outputStream) // JPEG 형식, 100% 품질
            val imageBytes: ByteArray = outputStream.toByteArray()
            outputStream.close()

            // Base64 인코딩
            Base64.encodeToString(imageBytes, Base64.DEFAULT)
        } catch (e: Exception) {
            e.printStackTrace()
            Log.e("IMAGE_ERROR", "Failed to load image: ${e.message}")
            null
        }
    }


    //식재료 꺼내기 화면 부분 UI 관련 함수
    //꺼낼 수량 0으로 초기화
    fun initIngredientTakeOutUiState() {
        _ingredientTakeOutUiState.value = List(_ingredientList.value.size) { 0 }
    }

    //꺼낼 수량 변경(UI)
    fun updateIngredientTakeOutUiState(index: Int, quantity: Int) {
        val updatedIngredientList = _ingredientTakeOutUiState.value.toMutableList()
        updatedIngredientList[index] = quantity
        _ingredientTakeOutUiState.value = updatedIngredientList
    }

    //꺼내기(DB데이터 변경)
    fun takeoutIngredient() {
        viewModelScope.launch {
            _ingredientList.value.forEachIndexed { index, ingredient ->
                val withdrawQuantity = _ingredientTakeOutUiState.value[index]
                val updatedQuantity = ingredient.quantity - withdrawQuantity

                if (updatedQuantity == 0) {
                    ingredientRepository.removeIngredient(ingredient) {
                        getIngredients()
                    }
                } else {
                    ingredientRepository.updateIngredient(ingredient.copy(quantity = updatedQuantity)) {
                        getIngredients()
                    }
                }
            }
        }
    }


    //식재료 유통기한 변경 화면 부분 UI 관련 함수
    //유통기한 초기화
    fun initIngredientExpirationUiState() {
        _ingredientExpirationUiState.value = _ingredientList.value
    }

    //유통기한 변경(UI)
    fun updateIngredientExpirationUiState(index: Int, ingredient: Ingredient) {
        val updatedIngredientList = _ingredientExpirationUiState.value.toMutableList()
        updatedIngredientList[index] = ingredient
        _ingredientExpirationUiState.value = updatedIngredientList
    }

    //유통기한 변경(DB)
    fun changeIngredientExpiration() {
        viewModelScope.launch {
            for (ingredient in _ingredientExpirationUiState.value) {
                ingredientRepository.updateIngredient(ingredient) {
                    getIngredients()
                }
            }
        }
    }


    fun getAutoOrderItems() {
        viewModelScope.launch {
            _autoOrderList.value = ingredientRepository.getAllAutoOrderItems()
            _autoOrderUiState.value = ingredientRepository.getAllAutoOrderItems()
        }
    }

    fun changeOrderUiState(autoOrderList: List<OrderItem>) {
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