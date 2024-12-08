package com.example.system

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.example.system.ingredientsDB.IngredientDB
import com.example.system.ingredientsDB.IngredientRepository
import com.example.system.ui.viewmodel.IngredientViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            val ingredientDB = IngredientDB.getDatabase(this)
            val ingredientDao = ingredientDB.IngredientDao()
            val ingredientRepository = IngredientRepository(applicationContext)
            val ingredientViewModel = IngredientViewModel(applicationContext)

            //IngredientScreen(ingredientViewModel)
            //IngredientInsertScreen(ingredientViewModel)
            CaptureImageAndSendToAI()
        }
    }
}