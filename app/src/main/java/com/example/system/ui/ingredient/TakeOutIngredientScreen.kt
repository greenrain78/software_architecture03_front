package com.example.system.ui.ingredient

import android.util.Log
import androidx.compose.runtime.Composable

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
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateListOf
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
    ingredientViewModel.getIngredients()

    val ingredients by ingredientViewModel.ingredientList.collectAsState()

    LaunchedEffect(ingredients) {
        ingredientViewModel.getIngredients()
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
            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.Top
            ) {
                itemsIndexed(ingredients) { index, ingredient ->
                    var name by remember { mutableStateOf(ingredient.name) }
                    var quantity by remember { mutableStateOf(ingredient.quantity) }
                    var withdrawQuantity by remember { mutableIntStateOf(0 ) }

                    var tempWithdrawQuantity by remember{ mutableStateOf(withdrawQuantity.toString()) }

                    val focusManager = LocalFocusManager.current

                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 4.dp),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        EditableTextField(
                            value = name,
                            onValueChange = { name = it },
                            readOnly = true,
                            modifier = Modifier.weight(1f)
                        )
                        EditableTextField(
                            value = quantity.toString(),
                            onValueChange = { quantity = it.toInt() },
                            readOnly = true,
                            modifier = Modifier.weight(1f)
                        )
                        EditableTextField(
                            value = tempWithdrawQuantity,
                            onValueChange = { tempWithdrawQuantity = it},
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
                                        withdrawQuantity = try {
                                            tempWithdrawQuantity.toInt()
                                        } catch (e: Exception) {
                                            0
                                        }

                                        if (withdrawQuantity > quantity)
                                            withdrawQuantity = quantity

                                        tempWithdrawQuantity = withdrawQuantity.toString()
                                        ingredientViewModel.updateIngredientTakeOutUiState(index, withdrawQuantity)
                                    }
                                }
                        )
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
