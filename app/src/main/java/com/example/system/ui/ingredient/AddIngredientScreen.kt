package com.example.system.ui.ingredient

import android.Manifest
import android.content.ContentValues
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.os.Environment
import android.provider.MediaStore
import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.result.launch
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.material3.Button
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableLongStateOf
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.ContextCompat
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.system.ingredientsDB.fromLocalDate
import com.example.system.ui.component.EditableTextField
import com.example.system.ui.component.ForceLandscapeOrientation
import com.example.system.ui.component.LeftScreen
import com.example.system.ui.viewmodel.IngredientViewModel
import java.time.LocalDate
import java.time.format.DateTimeFormatter

@Composable
fun AddIngredientScreen(navController: NavHostController) {
    ForceLandscapeOrientation()
    Row(
        modifier = Modifier.fillMaxSize()
    ) {
        LeftScreen(
            modifier = Modifier
                .wrapContentSize()
                .fillMaxHeight()
                .background(Color.LightGray),
            navController = navController
        )
        CenterIngredientInputScreen(
            modifier = Modifier
                .weight(2f)
                .fillMaxHeight()
                .background(Color.White)
        )
        RightIngredientInputScreen(
            modifier = Modifier
                .weight(1f)
                .fillMaxHeight()
                .background(Color.LightGray),
        )
    }
}

@Composable
fun CenterIngredientInputScreen(modifier: Modifier = Modifier, ingredientViewModel: IngredientViewModel = hiltViewModel()) {
    val ingredients by ingredientViewModel.ingredientAddUiState.collectAsState()

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(6.dp),
        verticalArrangement = Arrangement.SpaceBetween,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // 가운데 화면 최상단 제목
        Text(
            text = "식재료 정보 입력",
            fontSize = 20.sp,
            textAlign = TextAlign.Center,
            modifier = Modifier.padding(bottom = 16.dp)
        )

        // 데이터 제목
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 8.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically // 수직 정렬 추가
        ) {
            Text(
                text = "식재료",
                modifier = Modifier.weight(1f),
                textAlign = TextAlign.Center,
                fontSize = 16.sp
            )
            Text(
                text = "수량(g)",
                modifier = Modifier.weight(1f),
                textAlign = TextAlign.Center,
                fontSize = 16.sp
            )
            Text(
                text = "유통기한",
                modifier = Modifier.weight(1.5f),
                textAlign = TextAlign.Center,
                fontSize = 16.sp
            )
        }

        // 데이터 목록
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
        ) {
            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.spacedBy(8.dp) // 목록 간격 설정
            ) {
                itemsIndexed(ingredients) { index, ingredient ->
                    var tempName by remember { mutableStateOf("") }
                    var tempQuantity by remember { mutableStateOf("0") }
                    var tempExpiryDate by remember { mutableStateOf(LocalDate.now().toString().replace("-", ".")) }

                    val focusManager = LocalFocusManager.current

                    Row(
                        modifier = Modifier
                            .fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically // 수직 정렬 추가
                    ) {
                        EditableTextField(
                            value = tempName,
                            onValueChange = { tempName = it },
                            keyboardActions = KeyboardActions(
                                onDone = {
                                    focusManager.clearFocus()
                                }
                            ),
                            modifier = Modifier
                                .weight(1f)
                                .padding(horizontal = 4.dp)
                                .onFocusChanged { focusState ->
                                    if (!focusState.isFocused) {
                                        ingredientViewModel.updateIngredientAddUiState(index = index, ingredient.copy(name = tempName))
                                    }
                                }
                        )
                        EditableTextField(
                            value = tempQuantity,
                            onValueChange = { tempQuantity = it},
                            keyboardActions = KeyboardActions(
                                onDone = {
                                    focusManager.clearFocus()
                                }
                            ),
                            modifier = Modifier
                                .weight(1f)
                                .padding(horizontal = 4.dp)
                                .onFocusChanged { focusState ->
                                    if (!focusState.isFocused) {
                                        val quantity = tempQuantity.toIntOrNull() ?: 0
                                        tempQuantity = quantity.toString()
                                        ingredientViewModel.updateIngredientAddUiState(index = index, ingredient.copy(quantity = quantity))
                                    }
                                }
                        )
                        EditableTextField(
                            value = tempExpiryDate,
                            onValueChange = { newValue ->
                                tempExpiryDate = newValue
                            },
                            keyboardActions = KeyboardActions(
                                onDone = {
                                    focusManager.clearFocus()
                                }
                            ),
                            modifier = Modifier
                                .weight(1.5f)
                                .padding(horizontal = 4.dp)
                                .onFocusChanged { focusState ->
                                    if (!focusState.isFocused) {
                                        val expiryDate = try {
                                            LocalDate.parse(tempExpiryDate, DateTimeFormatter.ofPattern("yyyy.MM.dd"))
                                        } catch (e: Exception) {
                                            LocalDate.now()
                                        }

                                        tempExpiryDate = expiryDate.toString().replace("-", ".")
                                        ingredientViewModel.updateIngredientAddUiState(index = index, ingredient.copy(expirationDate = fromLocalDate(expiryDate)!!))
                                    }
                                }
                        )
                    }
                }
            }
        }

        // 작성칸 추가 버튼
        Button(
            onClick = {
                ingredientViewModel.addIngredientAddUiState()
            },
            modifier = Modifier
                .padding(8.dp)
                .fillMaxWidth(),
            shape = RoundedCornerShape(8.dp)
        ) {
            Text(text = "식재료 작성칸 추가", fontSize = 16.sp)
        }

        // 등록하기 버튼
        Button(
            onClick = { ingredientViewModel.addIngredients() },
            modifier = Modifier
                .padding(8.dp)
                .fillMaxWidth(),
            shape = RoundedCornerShape(8.dp)
        ) {
            Text(text = "등록하기", fontSize = 16.sp)
        }
    }
}

@Composable
fun RightIngredientInputScreen(modifier: Modifier = Modifier, ingredientViewModel: IngredientViewModel = hiltViewModel()) {
    val context = LocalContext.current
    //val capturedIngredient by ingredientViewModel.capturedIngredient.collectAsState()

    // 카메라 권한을 요청했을 때의 로직 코드
    val requestPermissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission()
    ) {}

    // 카메라 촬영 이후 로직 코드
    val takePhotoLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.TakePicturePreview()
    ) { takenPhoto: Bitmap? ->
        if (takenPhoto != null) {
            val contentValues = ContentValues().apply {
                put(
                    MediaStore.Images.Media.DISPLAY_NAME,
                    "captured_image_${System.currentTimeMillis()}.jpg"
                )
                put(MediaStore.Images.Media.MIME_TYPE, "image/jpeg")
                put(MediaStore.Images.Media.RELATIVE_PATH, Environment.DIRECTORY_PICTURES)
            }

            val uri = context.contentResolver.insert(
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                contentValues
            )

            if (uri != null) {
                //ingredientViewModel.recognizeIngredientFromImage()
                Log.d("CameraScreen", "capture complete")
            } else {
                Log.d("CameraScreen", "Failed to capture image.")
            }
        } else {
            Log.d("CameraScreen", "Photo capture was canceled.")
        }
    }

    Button(
        onClick = {
            //카메라 권한 요청
            if (ContextCompat.checkSelfPermission(context, Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED) {
                ingredientViewModel.captureIngredient(context, takePhotoLauncher)
            } else {
                requestPermissionLauncher.launch(Manifest.permission.CAMERA)
            }

        },
        modifier = modifier
            .fillMaxSize()
            .padding(8.dp),
        shape = RoundedCornerShape(8.dp)
    ) {
        Text(
            text = "카메라로\n등록하기",
            fontSize = 18.sp,
            textAlign = TextAlign.Center,
            color = Color.White
        )
    }
    
}


@Preview(showBackground = true, widthDp = 600, heightDp = 400)
@Composable
fun PreviewIngredientInputScreen() {
    AddIngredientScreen(navController = rememberNavController())
}
