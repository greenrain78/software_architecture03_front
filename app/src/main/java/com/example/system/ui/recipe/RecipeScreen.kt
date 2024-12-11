package com.example.system.ui.recipe

import android.util.Log
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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
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
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.system.data.model.Recipe
import com.example.system.ingredientsDB.Ingredient
import com.example.system.ui.component.ForceLandscapeOrientation
import com.example.system.ui.component.LeftScreen
import com.example.system.ui.viewmodel.RecipeViewModel
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

@Composable
fun RecipeScreen(
    navController: NavHostController,
    viewModel: RecipeViewModel = hiltViewModel()
) {
    // 뷰모델에서 레시피 리스트를 가져와서 저장

    LaunchedEffect(Unit) {
        viewModel.getRecipes()
    }

    val recipes = viewModel.recipeUiState.collectAsState().value

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
            recipes = recipes
        )
//        val a = viewModel.recipeList.collectAsState()
//        LazyColumn {
//            items(a.value) { recipe ->
//                Text(text = recipe.name)
//            }
//        }
    }
}

@Composable
fun CenterRecipeScreen(
    modifier: Modifier = Modifier,
    navController: NavHostController,
    recipes: List<Recipe>
) {

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(vertical = 6.dp)
            .padding(horizontal = 6.dp),
        verticalArrangement = Arrangement.SpaceBetween,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Text(
            text = "레시피 조회",
            fontSize = 20.sp,
            textAlign = TextAlign.Center,
            modifier = Modifier.padding(bottom = 16.dp)
        )


        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .weight(1f),
            verticalArrangement = Arrangement.Top
        ) {
            items(recipes) { recipe ->
                RecipeCard(recipe = recipe)
            }
        }

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 6.dp),
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

@Composable
fun RecipeCard(recipe: Recipe) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .background(Color.LightGray, RoundedCornerShape(8.dp))
            .padding(8.dp)
    ) {
        Text(text = recipe.name, fontSize = 18.sp)
        recipe.ingredients.forEach { ingredient ->
            Text(text = "- ${ingredient.name}", fontSize = 14.sp)
        }
        Text(text = recipe.description, fontSize = 14.sp)
    }
}

// 프리뷰
//@Preview(showBackground = true, widthDp = 600, heightDp = 400)
//@Composable
//fun PreviewRecipeScreen() {
//    CenterRecipeScreen(
//        navController = rememberNavController(),
//        recipes = listOf(
//            // 더미 데이터 만들어줘
//            Recipe(
//                name = "김치찌개",
//                ingredients = listOf(
//                    Ingredient(1, "김치"),
//                    Ingredient(2, "물"),
//                    Ingredient(3, "돼지고기", 1),
//                    Ingredient(4, "양파", 1)
//                ),
//                description = "김치 볶기, 물 끓이기, 김치 넣기"
//            ),
//            Recipe(
//                name = "된장찌개",
//                ingredients = listOf(
//                    Ingredient(1, "된장", 1),
//                    Ingredient(2, "감자", 1),
//                    Ingredient(3, "양파", 1)
//                ),
//                description = "된장 풀기, 감자 끓이기, 양파 볶기"
//            )
//        )
//    )
//}
