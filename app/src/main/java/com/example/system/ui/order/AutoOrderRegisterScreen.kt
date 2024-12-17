package com.example.system.ui.order

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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
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
import com.example.system.ui.component.ForceLandscapeOrientation
import com.example.system.ui.component.LeftScreen
import com.example.system.ui.viewmodel.IngredientViewModel
import com.example.system.ui.viewmodel.RecipeViewModel

@Composable
fun AutoOrderRegisterScreen(
    navController: NavHostController,
    ingredientViewModel: IngredientViewModel = hiltViewModel(),
    recipeViewModel: RecipeViewModel = hiltViewModel()
) {
    LaunchedEffect(key1 = Unit) {
        ingredientViewModel.getAutoOrderItems()
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

        CenterMarketAutoScreen(
            modifier = Modifier
                .weight(1f)
                .fillMaxHeight()
                .background(Color.White),
            navController = navController,

            )
    }
}

@Composable
fun CenterMarketAutoScreen(
    modifier: Modifier = Modifier,
    navController: NavHostController,
    ingredientViewModel: IngredientViewModel = hiltViewModel()
) {
    val autoOrderList by ingredientViewModel.autoOrderUiState.collectAsState()

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(6.dp),
        verticalArrangement = Arrangement.SpaceBetween,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // 화면 상단 제목
        Text(
            text = "주문 내역을 수정해 주세요",
            fontSize = 20.sp,
            textAlign = TextAlign.Center,
            modifier = Modifier.padding(bottom = 16.dp)
        )

        // 상품명과 수량 타이틀
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 8.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = "상품명",
                fontSize = 16.sp,
                textAlign = TextAlign.Center,
                modifier = Modifier.weight(1f)
            )
            Text(
                text = "수량(g)",
                fontSize = 16.sp,
                textAlign = TextAlign.Center,
                modifier = Modifier.weight(1f)
            )
        }

        // 상품 목록 (수정 가능)
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
                //.background(Color.Transparent) // 배경 투명 설정
                .padding(8.dp)
        ) {

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 4.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                // 상품명 (수정 가능)
                TextField(
                    value = "",
                    onValueChange = {  },
                    modifier = Modifier
                        .weight(1f)
                        //.background(Color.White, RoundedCornerShape(8.dp))
                        .padding(4.dp),
                    singleLine = true,
                    placeholder = { Text("상품명을 입력하세요") }
                )

                // 수량 (수정 가능)
                TextField(
                    value = "quantity.value",
                    onValueChange = {  },
                    modifier = Modifier
                        .weight(1f)
                        //.background(Color.White, RoundedCornerShape(8.dp))
                        .padding(4.dp),
                    singleLine = true,
                    placeholder = { Text("수량을 입력하세요") }
                )
            }
        }
    }

    Button(
        onClick = { ingredientViewModel.addAutoOrder() },
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        shape = RoundedCornerShape(8.dp) // 모서리 둥근 사각형
    ) {
        Text(text = "주문 내역 추가", fontSize = 16.sp)
    }

    // 버튼 영역
    Button(
        onClick = { ingredientViewModel.addAutoOrder() },
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        shape = RoundedCornerShape(8.dp) // 모서리 둥근 사각형
    ) {
        Text(text = "저장하기", fontSize = 16.sp)
    }
}


// 프리뷰
@Preview(showBackground = true, widthDp = 600, heightDp = 400)
@Composable
fun PreviewMarketAutoScreen() {
    CenterMarketAutoScreen(navController = rememberNavController())
}


