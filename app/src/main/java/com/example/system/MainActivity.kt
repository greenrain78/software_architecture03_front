package com.example.system

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.system.ingredientsDB.fromLocalDate
import com.example.system.ui.home.HomeScreen
import com.example.system.ui.ingredient.AddIngredientScreen
import com.example.system.ui.ingredient.IngredientExpirationDateScreen
import com.example.system.ui.ingredient.NewIngredientScreen
import com.example.system.ui.ingredient.TakeOutIngredientScreen
import com.example.system.ui.order.AutoOrderRegisterScreen
import com.example.system.ui.order.AutoOrderScreen
import com.example.system.ui.recipe.EditRecipeScreen
import com.example.system.ui.recipe.RecipeScreen
import com.example.system.ui.recipe.RegisterRecipeScreen
import com.example.system.ui.theme.SystemTheme
import com.example.system.ui.viewmodel.IngredientViewModel
import dagger.hilt.android.AndroidEntryPoint
import java.time.LocalDate

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            SystemTheme {
                val navController = rememberNavController()
                AppNavHost(navController)

                
                
                //더미 데이터 용
                val ingredientViewModel: IngredientViewModel = hiltViewModel()

                LaunchedEffect(key1 = Unit) {
                    ingredientViewModel.deleteAll()

                    fromLocalDate(LocalDate.of(2024, 12, 31))?.let {
                        ingredientViewModel.updateIngredientAddUiState(
                            name = "물", quantity = 200,
                            expirationDate = it
                        )
                    }
                    ingredientViewModel.addIngredient()
                    fromLocalDate(LocalDate.of(2025, 1, 22))?.let {
                        ingredientViewModel.updateIngredientAddUiState(
                            name = "김치", quantity = 500,
                            expirationDate = it
                        )
                    }
                    ingredientViewModel.addIngredient()
                    fromLocalDate(LocalDate.of(2025, 1, 22))?.let {
                        ingredientViewModel.updateIngredientAddUiState(
                            name = "양파", quantity = 500,
                            expirationDate = it
                        )
                    }
                    ingredientViewModel.addIngredient()
                    fromLocalDate(LocalDate.of(2024, 12, 18))?.let {
                        ingredientViewModel.updateIngredientAddUiState(
                            name = "돼지고기", quantity = 300,
                            expirationDate = it
                        )
                    }
                    ingredientViewModel.addIngredient()
                    fromLocalDate(LocalDate.now())?.let {
                        ingredientViewModel.updateIngredientAddUiState(
                            name = "", quantity = 0,
                            expirationDate = it
                        )
                    }
                }
            }
        }
    }
}

// NavHost 설정
@Composable
fun AppNavHost(navController: NavHostController) {
    NavHost(
        navController = navController,
        startDestination = "home"
    ) {
        composable("home") { HomeScreen(navController) }

        composable("ingredient") { NewIngredientScreen(navController) }
        composable("addIngredient") { AddIngredientScreen(navController) }
        composable("takeOutIngredient") { TakeOutIngredientScreen(navController) }
        composable("ingredientExpirationDate") { IngredientExpirationDateScreen(navController) }

        composable("recipe") { RecipeScreen(navController) }
        composable("registerRecipe") { RegisterRecipeScreen(navController) }
        composable("editRecipe") { EditRecipeScreen(navController) }

        composable("order") { AutoOrderScreen(navController) }
        composable("autoOrder") { AutoOrderRegisterScreen(navController) }

    }

}