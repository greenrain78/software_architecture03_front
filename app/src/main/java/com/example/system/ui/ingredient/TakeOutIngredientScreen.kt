package com.example.system.ui.ingredient

import android.util.Log
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
import com.example.system.ui.component.EditableTextField
import com.example.system.ui.component.ForceLandscapeOrientation
import com.example.system.ui.component.LeftScreen
import com.example.system.ui.viewmodel.IngredientViewModel

@Composable
fun TakeOutIngredientScreen(
    navController: NavHostController,
    ingredientViewModel: IngredientViewModel = hiltViewModel()
) {
    LaunchedEffect(key1 = true) {
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
        CenterIngredientOutputScreen(
            modifier = Modifier
                .weight(2f) // 가운데 화면이 우측 버튼의 공간까지 차지하도록 설정
                .fillMaxHeight()
                .background(Color.White)
        )
    }
}

@Composable
fun CenterIngredientOutputScreen(
    modifier: Modifier = Modifier,
    ingredientViewModel: IngredientViewModel = hiltViewModel()
) {
    val ingredients by ingredientViewModel.ingredientList.collectAsState()
    val ingredientTakeOutUiState by ingredientViewModel.ingredientTakeOutUiState.collectAsState()

    LaunchedEffect(ingredients) {
        ingredientViewModel.initIngredientTakeOutUiState()
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
            text = "전체 식재료 목록",
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
                text = "현재 수량(g)",
                modifier = Modifier.weight(1f),
                textAlign = TextAlign.Center,
                fontSize = 16.sp
            )
            Text(
                text = "꺼낼 수량(g)",
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
            if (ingredientTakeOutUiState.isNotEmpty()) {
            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.Top
            ) {
                itemsIndexed(ingredients) { index, ingredient ->
                    val focusManager = LocalFocusManager.current

                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 4.dp),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        EditableTextField(
                            value = ingredient.name,
                            onValueChange = {  },
                            readOnly = true,
                            modifier = Modifier.weight(1f)
                        )
                        EditableTextField(
                            value = ingredient.quantity.toString(),
                            onValueChange = {  },
                            readOnly = true,
                            modifier = Modifier.weight(1f)
                        )
                        EditableTextField(
                            value = ingredientTakeOutUiState[index].toString(),
                            onValueChange = { newText ->
                                var updatedValue = newText.toIntOrNull() ?: 0

                                if (updatedValue > ingredient.quantity)
                                    updatedValue = ingredient.quantity

                                if (updatedValue < 0)
                                    updatedValue = 0

                                ingredientViewModel.updateIngredientTakeOutUiState(index, updatedValue)
                                            },
                            keyboardActions = KeyboardActions(
                                onDone = {
                                    focusManager.clearFocus()
                                }
                            ),
                            modifier = Modifier
                                .weight(1f)
                                .padding(horizontal = 4.dp)
                        )
                    }
                }
            }
        }
            }

        // 식재료 꺼내기 버튼
        Button(
            onClick = {
                ingredientViewModel.takeoutIngredient()
            },
            modifier = Modifier
                .padding(8.dp)
                .fillMaxWidth(),
            shape = RoundedCornerShape(8.dp)
        ) {
            Text(text = "식재료 꺼내기", fontSize = 16.sp)
        }
    }
}

// 프리뷰
@Preview(showBackground = true, widthDp = 600, heightDp = 400)
@Composable
fun PreviewIngredientOutputScreen() {
    TakeOutIngredientScreen(navController = rememberNavController())
}
