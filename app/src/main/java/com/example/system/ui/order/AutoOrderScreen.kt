package com.example.system.ui.order

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.system.PreviewObject
import com.example.system.ingredientsDB.Ingredient
import com.example.system.ui.component.ForceLandscapeOrientation
import com.example.system.ui.component.HorizontalButton
import com.example.system.ui.component.LeftScreen
import com.example.system.ui.viewmodel.IngredientViewModel

@Composable
fun AutoOrderScreen(
    navController: NavHostController,
    viewModel: IngredientViewModel = hiltViewModel()
) {
    ForceLandscapeOrientation()
    Row(
        modifier = Modifier.fillMaxSize()
    ) {

        LaunchedEffect(key1 = Unit) {
            viewModel.getAutoOrderIngredients()
        }
        val autoOrderList = viewModel.autoOrderUiState.collectAsState().value

        LeftScreen(
            modifier = Modifier
                .wrapContentSize()
                .fillMaxHeight()
                .background(Color.LightGray),
            navController = navController
        )

        CenterOrderScreen(
            modifier = Modifier
                .weight(1f)
                .fillMaxHeight()
                .background(Color.White),
            navController = navController,
            ingredients = autoOrderList
        )
    }
}

@Composable
fun CenterOrderScreen(
    modifier: Modifier = Modifier,
    navController: NavHostController,
    ingredients: List<Ingredient>
) {

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(6.dp),
        verticalArrangement = Arrangement.SpaceBetween,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // 화면 상단 제목
        Text(
            text = "자동 주문 상품 목록",
            fontSize = 20.sp,
            textAlign = TextAlign.Center,
            modifier = Modifier.padding(bottom = 16.dp)
        )

        // 상품명과 수량 타이틀
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 8.dp)
                .border(1.dp, Color.Gray, shape = MaterialTheme.shapes.medium)
                .background(color = Color(0xFFDBDBDB), shape = MaterialTheme.shapes.medium),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = "상품명",
                fontSize = 16.sp,
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .weight(1f)
                    .padding(vertical = 4.dp)
            )
            Text(
                text = "주문 수량",
                fontSize = 16.sp,
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .weight(1f)
                    .padding(vertical = 4.dp)

            )

        }

        // 상품 목록
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
                .background(Color.Transparent) // 배경 투명 설정
                .padding(8.dp)
        ) {
            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(ingredients) { ingredient ->
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 4.dp)
                            .border(1.dp, Color.Gray, shape = MaterialTheme.shapes.small),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(
                            text = ingredient.name,
                            fontSize = 14.sp,
                            textAlign = TextAlign.Center,
                            modifier = Modifier
                                .align(Alignment.CenterVertically)
                                .weight(1f)
                                .padding(
                                    start = 8.dp,
                                    top = 4.dp, bottom = 4.dp
                                ) // 좌측 간격 조정
                        )
                        Text(
                            text = ingredient.orderQuantity.toString(),
                            fontSize = 14.sp,
                            textAlign = TextAlign.Center,
                            modifier = Modifier
                                .weight(1f)
                                .align(Alignment.CenterVertically)
                                .padding(
                                    start = 8.dp,
                                    top = 4.dp, bottom = 4.dp
                                ) // 좌측 간격 조정
                        )

                    }
                }
            }
        }

        // 버튼 영역
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 8.dp),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            HorizontalButton(text = "자동 주문 설정") {
                navController.navigate("autoOrder")
            }
        }
    }
}

// 프리뷰
@Preview(showBackground = true, widthDp = 600, heightDp = 400)
@Composable
fun PreviewMarketScreen() {
    CenterOrderScreen(
        navController = rememberNavController(),
        ingredients = PreviewObject.previewIngredients
    )
}
