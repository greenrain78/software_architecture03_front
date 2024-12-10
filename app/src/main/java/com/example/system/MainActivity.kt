package com.example.system

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.system.ui.theme.SystemTheme

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

//        composable("ingredient") { IngredientScreen(navController) }
//        composable("ingredientinput") { IngredientInputScreen(navController) }
//        composable("camera") { CameraScreen() }
//        composable("ingredientoutput") { IngredientOutputScreen(navController) }
//        composable("ingredientdate") { IngredientDateScreen(navController) }
//
//        composable("recipe") { RecipeScreen(navController) }
//        composable("recipeinput") { RecipeInputScreen(navController) }
//        composable("recipeedit") { RecipeEditScreen(navController) }
//
//        composable("market") { MarketScreen(navController) }
//        composable("marketorder") { MarketOrderScreen(navController) }
//        composable("marketauto") { MarketAutoScreen(navController) }

    }

}

