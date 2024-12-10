package com.example.system

import android.media.MediaDrm.OnSessionLostStateListener
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.system.ui.home.HomeScreen
import com.example.system.ui.ingredient.AddIngredientScreen
import com.example.system.ui.ingredient.IngredientExpirationDateScreen
import com.example.system.ui.ingredient.NewIngredientScreen
import com.example.system.ui.ingredient.TakeOutIngredientScreen
import com.example.system.ui.order.AutoOrderScreen
import com.example.system.ui.order.OrderIngredientScreen
import com.example.system.ui.order.OrderScreen
import com.example.system.ui.recipe.EditRecipeScreen
import com.example.system.ui.recipe.RecipeScreen
import com.example.system.ui.recipe.RegisterRecipeScreen
import com.example.system.ui.theme.SystemTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            SystemTheme {
                val navController = rememberNavController()
                AppNavHost(navController)
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
//        composable("camera") { CameraScreen() }
        composable("takeOutIngredient") { TakeOutIngredientScreen(navController) }
        composable("ingredientExpirationDate") { IngredientExpirationDateScreen(navController) }

        composable("recipe") { RecipeScreen(navController) }
        composable("registerRecipe") { RegisterRecipeScreen(navController) }
        composable("editRecipe") { EditRecipeScreen(navController) }

        composable("order") { OrderScreen(navController) }
        composable("orderIngredient") { OrderIngredientScreen(navController) }
        composable("autoOrder") { AutoOrderScreen(navController) }

    }

}

