package com.example.system

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.platform.LocalContext
import com.example.system.ingredientsDB.IngredientDB
import com.example.system.ingredientsDB.IngredientRepository
import com.example.system.ui.viewmodel.IngredientViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            val ingredientDB = IngredientDB.getDatabase(this)
            val ingredientDao = ingredientDB.IngredientDao()
            val ingredientRepository = IngredientRepository(ingredientDao)
            val ingredientViewModel = IngredientViewModel(ingredientRepository)
            CameraScreen(ingredientViewModel)
        }
    }
}