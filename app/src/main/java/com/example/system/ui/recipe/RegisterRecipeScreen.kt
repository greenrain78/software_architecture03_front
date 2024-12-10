package com.example.system.ui.recipe

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
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
fun RegisterRecipeScreen(navController: NavHostController) {
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

        CenterRecipeInputScreen(
            modifier = Modifier
                .weight(1f)
                .fillMaxHeight()
                .background(Color.White)
        )
    }
}

@Composable
fun CenterRecipeInputScreen(modifier: Modifier = Modifier) {
    val recipeName = remember { mutableStateOf("") }
    val ingredients = remember { mutableStateOf("") }
    val instructions = remember { mutableStateOf("") }

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(6.dp),
        verticalArrangement = Arrangement.SpaceBetween,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // 화면 상단 제목
        Text(
            text = "레시피 등록",
            fontSize = 20.sp,
            textAlign = TextAlign.Center,
            modifier = Modifier.padding(bottom = 16.dp)
        )

        // 레시피 입력
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
                .background(Color.LightGray, RoundedCornerShape(8.dp)) // 회색 배경 유지
                .padding(8.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            // 음식 이름 입력
            TextField(
                value = recipeName.value,
                onValueChange = { recipeName.value = it },
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color.White, RoundedCornerShape(8.dp)) // 배경색 유지
                    .padding(4.dp),
                placeholder = { Text("음식 이름을 입력하세요") }
            )

            // 재료와 요리법 입력 (같은 줄)
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f) // Row가 아래로 늘어나도록 설정
                    .padding(4.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                verticalAlignment = Alignment.Top // Stretch 대신 상단 맞춤으로 설정
            ) {
                // 재료 입력
                TextField(
                    value = ingredients.value,
                    onValueChange = { ingredients.value = it },
                    modifier = Modifier
                        .weight(1f)
                        .fillMaxHeight() // Row의 높이에 맞게 텍스트 필드 확장
                        .background(Color.White, RoundedCornerShape(8.dp))
                        .padding(4.dp),
                    placeholder = { Text("재료를 입력하세요") }
                )

                // 요리법 입력
                TextField(
                    value = instructions.value,
                    onValueChange = { instructions.value = it },
                    modifier = Modifier
                        .weight(1f)
                        .fillMaxHeight() // Row의 높이에 맞게 텍스트 필드 확장
                        .background(Color.White, RoundedCornerShape(8.dp))
                        .padding(4.dp),
                    placeholder = { Text("조리법을 입력하세요") }
                )
            }
        }

        // 버튼 영역
        Button(
            onClick = { /* 등록 처리 로직 추가 */ },
            modifier = Modifier
                .fillMaxWidth()
                .padding(0.dp),
            shape = RoundedCornerShape(8.dp) // 모서리 둥근 사각형
        ) {
            Text(text = "레시피 등록하기", fontSize = 16.sp)
        }
    }
}

// 프리뷰를 위한 함수
@Composable
fun RecipeInputPreviewContent() {
    val navController = rememberNavController()
    RegisterRecipeScreen(navController = navController)
}

// 프리뷰
@Preview(showBackground = true, widthDp = 600, heightDp = 400)
@Composable
fun PreviewRecipeInputScreen() {
    RecipeInputPreviewContent()
}



