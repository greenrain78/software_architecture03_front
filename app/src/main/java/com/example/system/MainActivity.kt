package com.example.system

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import com.example.system.ingredientsDB.Ingredient
import com.example.system.ingredientsDB.IngredientDB
import com.example.system.ingredientsDB.IngredientRepository
import java.time.LocalDate

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            //val ingredientDB = IngredientDB.getDatabase(this)
            //val ingredientDao = ingredientDB.IngredientDao()
            //val ingredientRepository = IngredientRepository(ingredientDao)
            //val ingredientViewModel = IngredientViewModel(ingredientRepository)
        }
    }
}