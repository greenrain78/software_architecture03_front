package com.example.system.ui.ingredient

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.system.ingredientsDB.fromLocalDate
import com.example.system.ingredientsDB.toLocalDate
import com.example.system.ui.component.EditableTextField
import com.example.system.ui.component.ForceLandscapeOrientation
import com.example.system.ui.component.LeftScreen
import com.example.system.ui.viewmodel.IngredientViewModel
import java.time.LocalDate
import java.time.format.DateTimeFormatter

@Composable
fun IngredientExpirationDateScreen(
    navController: NavHostController,
    ingredientViewModel: IngredientViewModel = hiltViewModel()
) {
    LaunchedEffect(key1 = Unit) {
        ingredientViewModel.getIngredients()
    }

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
        CenterIngredientDateScreen(
            modifier = Modifier
                .weight(2f) // 가운데 화면이 우측 버튼의 공간까지 차지하도록 설정
                .fillMaxHeight()
                .background(Color.White)
        )
    }
}

@Composable
fun CenterIngredientDateScreen(
    modifier: Modifier = Modifier,
    ingredientViewModel: IngredientViewModel = hiltViewModel()
) {
    val ingredients by ingredientViewModel.ingredientList.collectAsState()
    val ingredientExpirationUiState by ingredientViewModel.ingredientExpirationUiState.collectAsState()

    LaunchedEffect(ingredients) {
        ingredientViewModel.initIngredientExpirationUiState()
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(6.dp),
        verticalArrangement = Arrangement.SpaceBetween,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // 가운데 화면 최상단 제목
        Text(
            text = "유통기한 관리",
            fontSize = 20.sp,
            textAlign = TextAlign.Center,
            modifier = Modifier.padding(bottom = 16.dp)
        )

        // 데이터 제목
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 8.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = "식재료",
                modifier = Modifier.weight(1f),
                textAlign = TextAlign.Center,
                fontSize = 16.sp
            )
            Text(
                text = "현재 유통기한",
                modifier = Modifier.weight(1f),
                textAlign = TextAlign.Center,
                fontSize = 16.sp
            )
            Text(
                text = "유통기한 입력",
                modifier = Modifier.weight(1f),
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
                verticalArrangement = Arrangement.Top
            ) {
                if (ingredientExpirationUiState.isNotEmpty()) {
                    itemsIndexed(ingredients) { index, ingredient ->
                        var tempInputExpiryDate by remember {
                            mutableStateOf(
                                toLocalDate(ingredient.expirationDate).toString().replace("-", ".")
                            )
                        }

                        val focusManager = LocalFocusManager.current

                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 4.dp),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            EditableTextField(
                                value = ingredient.name,
                                onValueChange = {},
                                readOnly = true,
                                modifier = Modifier.weight(1f)
                            )
                            EditableTextField(
                                value = toLocalDate(ingredient.expirationDate).toString()
                                    .replace("-", "."),
                                onValueChange = {},
                                readOnly = true,
                                modifier = Modifier.weight(1f)
                            )
                            EditableTextField(
                                value = tempInputExpiryDate,
                                onValueChange = { tempInputExpiryDate = it },
                                keyboardActions = KeyboardActions(
                                    onDone = {
                                        focusManager.clearFocus()
                                    }
                                ),
                                modifier = Modifier
                                    .weight(1f)
                                    .onFocusChanged { focusState ->
                                        if (!focusState.isFocused) {
                                            val inputExpiryDate = try {
                                                LocalDate.parse(
                                                    tempInputExpiryDate,
                                                    DateTimeFormatter.ofPattern("yyyy.MM.dd")
                                                )
                                            } catch (e: Exception) {
                                                toLocalDate(ingredient.expirationDate)
                                            }

                                            tempInputExpiryDate =
                                                inputExpiryDate.toString().replace("-", ".")
                                            ingredientViewModel.updateIngredientExpirationUiState(
                                                index = index,
                                                ingredient.copy(
                                                    expirationDate = fromLocalDate(inputExpiryDate)!!
                                                )
                                            )
                                        }
                                    }
                            )
                        }

                    }
                }
            }
        }

        // 유통기한 등록 버튼
        Button(
            onClick = { ingredientViewModel.changeIngredientExpiration() },
            modifier = Modifier
                .padding(8.dp)
                .fillMaxWidth(),
            shape = RoundedCornerShape(8.dp)
        ) {
            Text(text = "유통기한 수정하기", fontSize = 16.sp)
        }
    }
}

// 프리뷰
@Preview(showBackground = true, widthDp = 600, heightDp = 400)
@Composable
fun PreviewIngredientDateScreen() {
    IngredientExpirationDateScreen(navController = rememberNavController())
}
