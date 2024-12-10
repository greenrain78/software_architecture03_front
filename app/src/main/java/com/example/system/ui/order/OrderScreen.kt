package com.example.system.ui.order

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.system.ui.component.ForceLandscapeOrientation
import com.example.system.ui.component.LeftScreen

@Composable
fun OrderScreen(navController: NavHostController) {
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

        CenterMarketScreen(
            modifier = Modifier
                .weight(1f)
                .fillMaxHeight()
                .background(Color.White),
            navController = navController
        )
    }
}

@Composable
fun CenterMarketScreen(modifier: Modifier = Modifier, navController: NavHostController) {
    val itemsList = remember {
        mutableStateListOf(
            Pair("사과", "100"),
            Pair("양파", "500"),
            Pair("사과", "100"),
            Pair("양파", "500"),
            Pair("사과", "100"),
            Pair("양파", "500"),
            Pair("사과", "100"),
            Pair("양파", "500"),
            Pair("감자", "100")
        )
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(6.dp),
        verticalArrangement = Arrangement.SpaceBetween,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // 화면 상단 제목
        Text(
            text = "주문 예정 상품 목록",
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
                items(itemsList) { (itemName, quantity) ->
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 4.dp),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(
                            text = itemName,
                            fontSize = 14.sp,
                            textAlign = TextAlign.Center,
                            modifier = Modifier
                                .weight(1f)
                                .padding(start = 8.dp) // 좌측 간격 조정
                        )
                        Text(
                            text = quantity,
                            fontSize = 14.sp,
                            textAlign = TextAlign.Center,
                            modifier = Modifier
                                .weight(1f)
                                .padding(end = 8.dp) // 우측 간격 조정
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
            Button(
                onClick = { navController.navigate("orderIngredient") },
                modifier = Modifier.weight(1f),
                shape = RoundedCornerShape(8.dp)
            ) {
                Text(text = "주문하기", fontSize = 16.sp)
            }
            Spacer(modifier = Modifier.width(8.dp))
            Button(
                onClick = { navController.navigate("autoOrder") },
                modifier = Modifier.weight(1f),
                shape = RoundedCornerShape(8.dp)
            ) {
                Text(text = "자동 주문 설정", fontSize = 16.sp)
            }
        }
    }
}

// 프리뷰
@Preview(showBackground = true, widthDp = 600, heightDp = 400)
@Composable
fun PreviewMarketScreen() {
    OrderScreen(navController = rememberNavController())
}
