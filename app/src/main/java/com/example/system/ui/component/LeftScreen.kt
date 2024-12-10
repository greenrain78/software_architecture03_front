package com.example.system.ui.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController

@Composable
fun LeftScreen(modifier: Modifier = Modifier, navController: NavHostController) {
    Column(
        modifier = modifier.padding(horizontal = 8.dp),
        verticalArrangement = Arrangement.SpaceEvenly,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        val cornerRadius = 16.dp
        val contentPadding = PaddingValues(4.dp) // 내부 여백 최소화
        val textSize = 14.sp // 텍스트 크기 조정

        // 홈 버튼
        Button(
            onClick = { navController.navigate("home") }, // 홈 화면
            modifier = Modifier
                .padding(vertical = 5.dp)
                .width(90.dp)
                .height(80.dp),
            shape = RoundedCornerShape(cornerRadius),
            contentPadding = contentPadding
        ) {
            Text(
                text = "홈",
                fontSize = textSize,
                modifier = Modifier
                    .fillMaxSize()
                    .wrapContentSize(align = Alignment.Center),
                maxLines = 1
            )
        }

        // 식재료 등록/관리 버튼
        Button(
            onClick = { navController.navigate("ingredient") }, // 식재료 등록/관리
            modifier = Modifier
                .padding(vertical = 5.dp)
                .width(90.dp)
                .height(80.dp),
            shape = RoundedCornerShape(cornerRadius),
            contentPadding = contentPadding
        ) {
            Column(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(text = "식재료", textAlign = TextAlign.Center)
                Text(
                    text = "등록/관리",
                    textAlign = TextAlign.Center,
                    fontSize = textSize,
                    maxLines = 1
                )
            }
        }

        // 레시피 등록/관리 버튼
        Button(
            onClick = { navController.navigate("recipe") }, // 레시피 등록/관리
            modifier = Modifier
                .padding(vertical = 5.dp)
                .width(90.dp)
                .height(80.dp),
            shape = RoundedCornerShape(cornerRadius),
            contentPadding = contentPadding
        ) {
            Column(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(text = "레시피", textAlign = TextAlign.Center)
                Text(
                    text = "등록/관리",
                    textAlign = TextAlign.Center,
                    fontSize = textSize,
                    maxLines = 1
                )
            }
        }

        // 장보기 예약/주문 버튼
        Button(
            onClick = { navController.navigate("market") }, // 장보기 예약/주문
            modifier = Modifier
                .padding(vertical = 5.dp)
                .width(90.dp)
                .height(80.dp),
            shape = RoundedCornerShape(cornerRadius),
            contentPadding = contentPadding
        ) {
            Column(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(text = "장보기", textAlign = TextAlign.Center)
                Text(
                    text = "예약/주문",
                    textAlign = TextAlign.Center,
                    fontSize = textSize,
                    maxLines = 1
                )
            }
        }
    }
}
