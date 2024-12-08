package com.example.system.data.model

import com.example.system.ingredientsDB.Ingredient

data class Recipe(
    val name: String,
    val ingredients: List<Ingredient>,
    val description: String = ""
)
