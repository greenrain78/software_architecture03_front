package com.example.system.ui.home

import androidx.compose.foundation.background
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
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.example.system.data.model.Recipe
import com.example.system.ingredientsDB.Ingredient
import com.example.system.ingredientsDB.toLocalDate
import com.example.system.ui.component.ForceLandscapeOrientation
import com.example.system.ui.component.LeftScreen
import com.example.system.ui.viewmodel.IngredientViewModel
import com.example.system.ui.viewmodel.RecipeViewModel

@Composable
fun HomeScreen(
    navController: NavHostController,
    recipeViewModel: RecipeViewModel = hiltViewModel(),
    ingredientViewModel: IngredientViewModel = hiltViewModel()
) {

    LaunchedEffect(key1 = Unit) {
        ingredientViewModel.getIngredients()
        ingredientViewModel.getExpiredIngredients()
        recipeViewModel.getRecommendedRecipe()
    }

    val ingredients by ingredientViewModel.ingredientList.collectAsState()
    val expiredIngredients by ingredientViewModel.expiredIngredientList.collectAsState()
    val recommendedRecipe = recipeViewModel.recommendUiState.collectAsState().value

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

        CenterScreen(
            modifier = Modifier
                .weight(1f)
                .fillMaxHeight()
                .background(Color.White),
            recommendedRecipe = recommendedRecipe,
            expiredIngredients = expiredIngredients
        )

        RightScreen(
            modifier = Modifier
                .weight(1f)
                .fillMaxHeight()
                .background(Color.White),
            ingredients = ingredients

            )
    }
}


@Composable
fun CenterScreen(
    modifier: Modifier = Modifier,
    recommendedRecipe: Recipe,
    expiredIngredients: List<Ingredient>
) {

    val recipes = remember {
        listOf(
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
            .padding(6.dp),
        verticalArrangement = Arrangement.SpaceBetween,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // 추천 레시피
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
                .background(Color.LightGray, RoundedCornerShape(8.dp))
                .padding(8.dp)
        ) {
            Column(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.Top,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "추천 레시피",
                    fontSize = 20.sp,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.padding(bottom = 8.dp)
                )
                Column(
                    modifier = Modifier.fillMaxSize(),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(Color.White, RoundedCornerShape(8.dp))
                            .padding(8.dp)
                    ) {
                        Text(
                            text = recommendedRecipe.name,
                            modifier = Modifier
                                .align(Alignment.CenterHorizontally),
                        )
                        Spacer(modifier = Modifier.size(20.dp))
                        Text(
                            text = "사용되는 식재료 : " + recommendedRecipe.ingredients.joinToString(", ") { it.name },
                        )
                        Spacer(modifier = Modifier.size(20.dp))
                        Text(
                            text = "조리법 : " + recommendedRecipe.description,
                            maxLines = 3
                        )
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(8.dp))

        // 유통기한 경고
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
                .background(Color.LightGray, RoundedCornerShape(8.dp))
                .padding(8.dp)
        ) {
            Column(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.Top,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "유통기한 경고",
                    fontSize = 20.sp,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.padding(bottom = 8.dp)
                )
                LazyColumn(
                    modifier = Modifier.fillMaxSize(),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    items(expiredIngredients) { ingredient ->
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .background(Color.White),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Text(
                                text = ingredient.name,
                                fontSize = 16.sp,
                                modifier = Modifier.weight(1f),
                                textAlign = TextAlign.Center
                            )
                            Text(
                                text = toLocalDate(ingredient.expirationDate).toString().replace("-", "."),
                                fontSize = 16.sp,
                                modifier = Modifier.weight(1f),
                                textAlign = TextAlign.Center
                            )
                        }
                    }
                }
            }
        }
    }
}


@Composable
fun RightScreen(
    modifier: Modifier = Modifier,
    ingredients: List<Ingredient>
) {
    Box(
        modifier = modifier
            .fillMaxSize()
            .padding(6.dp)
            .background(Color.LightGray, RoundedCornerShape(8.dp))
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(8.dp),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // 상단 제목
            Text(
                text = "전체 식재료 목록",
                fontSize = 20.sp,
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(bottom = 8.dp)
            )

            // 목록 출력
            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(ingredients) { ingredient ->
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(Color.White, RoundedCornerShape(8.dp))
                            .padding(horizontal = 16.dp, vertical = 8.dp), // 좌우 패딩 추가
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(
                            text = ingredient.name,
                            fontSize = 16.sp,
                            modifier = Modifier.weight(1f),
                            textAlign = TextAlign.Center
                        )
                        Text(
                            text = ingredient.quantity.toString(),
                            fontSize = 16.sp,
                            modifier = Modifier.weight(1f),
                            textAlign = TextAlign.Center
                        )
                    }
                }
            }
        }
    }
}

/*
@Preview(showBackground = true, widthDp = 600, heightDp = 400)
@Composable
fun HomeScreenPreview() {
    CenterScreen(
        recommendedRecipe = Recipe(
            "레시피이름",
            listOf(
                Ingredient(name = "재료이름"),
            ),
            description = "요리법"
        )
    )
}

@Preview(showBackground = true, widthDp = 600, heightDp = 400)
@Composable
fun HomeScreenPreview2() {
    RightScreen(
    )
}
 */