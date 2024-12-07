package com.example.system

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.example.system.ingredientsDB.IngredientDB
import com.example.system.ingredientsDB.IngredientRepository

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            val ingredientDB = IngredientDB.getDatabase(this)
            val ingredientDao = ingredientDB.IngredientDao()
            val ingredientRepository = IngredientRepository(ingredientDao)
            val ingredientViewModel = IngredientViewModel(ingredientRepository)

            //IngredientScreen(ingredientViewModel)
            //IngredientInsertScreen(ingredientViewModel)
            CaptureImageAndSendToAI()
        }
    }
}