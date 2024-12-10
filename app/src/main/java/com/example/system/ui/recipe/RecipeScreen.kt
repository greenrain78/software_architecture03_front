package com.example.system.ui.recipe

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
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.system.ui.component.ForceLandscapeOrientation
import com.example.system.ui.component.LeftScreen
import com.example.system.ui.viewmodel.RecipeViewModel

@Composable
fun RecipeScreen(
    navController: NavHostController,
    viewModel: RecipeViewModel = hiltViewModel()
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

        CenterRecipeScreen(
            modifier = Modifier
                .weight(1f)
                .fillMaxHeight()
                .background(Color.White),
            navController = navController,
        )
    }
}

@Composable
fun CenterRecipeScreen(
    modifier: Modifier = Modifier,
    navController: NavHostController,
) {
    val recipes = remember {
        mutableStateListOf(
            Triple(
                "김치찌개",
                listOf("김치", "물", "돼지고기", "양파"),
                listOf("김치 볶기", "물 끓이기", "김치 넣기")
            ),
            Triple(
                "된장찌개",
                listOf("된장", "감자", "양파"),
                listOf("된장 풀기", "감자 끓이기", "양파 볶기")
            )
        )
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(vertical = 6.dp)
            .padding(horizontal = 6.dp),
        verticalArrangement = Arrangement.SpaceBetween,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // 화면 상단 제목
        Text(
            text = "레시피 조회",
            fontSize = 20.sp,
            textAlign = TextAlign.Center,
            modifier = Modifier.padding(bottom = 16.dp)
        )

        // 레시피 목록
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
        ) {
            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.Top
            ) {
                items(recipes) { (name, ingredients, instructions) ->
                    // 박스 높이 동기화를 위한 계산
                    val maxLines = maxOf(ingredients.size, instructions.size)

                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 8.dp)
                            .background(Color.LightGray, RoundedCornerShape(8.dp))
                            .padding(8.dp),
                        verticalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        // 음식 이름
                        Text(
                            text = name,
                            fontSize = 18.sp,
                            textAlign = TextAlign.Center,
                            modifier = Modifier
                                .fillMaxWidth()
                                .background(Color.White, RoundedCornerShape(8.dp))
                                .padding(bottom = 4.dp)
                        )
                        // 재료와 요리법 박스
                        Row(
                            modifier = Modifier
                                .fillMaxWidth(),
                            horizontalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            // 재료 박스
                            Box(
                                modifier = Modifier
                                    .weight(1f)
                                    .background(Color.White, RoundedCornerShape(8.dp))
                                    .padding(8.dp)
                            ) {
                                Column {
                                    ingredients.forEach { ingredient ->
                                        Text(
                                            text = ingredient,
                                            fontSize = 14.sp,
                                            textAlign = TextAlign.Start
                                        )
                                    }
                                    // 빈 줄 추가로 높이 맞춤
                                    repeat(maxLines - ingredients.size) {
                                        Text(
                                            text = "",
                                            fontSize = 14.sp,
                                            textAlign = TextAlign.Start
                                        )
                                    }
                                }
                            }
                            // 요리법 박스
                            Box(
                                modifier = Modifier
                                    .weight(1f)
                                    .background(Color.White, RoundedCornerShape(8.dp))
                                    .padding(8.dp)
                            ) {
                                Column {
                                    instructions.forEach { instruction ->
                                        Text(
                                            text = instruction,
                                            fontSize = 14.sp,
                                            textAlign = TextAlign.Start
                                        )
                                    }
                                    // 빈 줄 추가로 높이 맞춤
                                    repeat(maxLines - instructions.size) {
                                        Text(
                                            text = "",
                                            fontSize = 14.sp,
                                            textAlign = TextAlign.Start
                                        )
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }

        // 버튼 영역
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 6.dp)
                .padding(bottom = 6.dp),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            Button(
                onClick = { navController.navigate("registerRecipe") },
                modifier = Modifier.weight(1f),
                shape = RoundedCornerShape(8.dp)
            ) {
                Text(text = "레시피 추가", fontSize = 16.sp)
            }
            Spacer(modifier = Modifier.width(8.dp))
            Button(
                onClick = { navController.navigate("editRecipe") },
                modifier = Modifier.weight(1f),
                shape = RoundedCornerShape(8.dp)
            ) {
                Text(text = "레시피 수정", fontSize = 16.sp)
            }
        }
    }
}

// 프리뷰
@Preview(showBackground = true, widthDp = 600, heightDp = 400)
@Composable
fun PreviewRecipeScreen() {
    CenterRecipeScreen(navController = rememberNavController())
}
