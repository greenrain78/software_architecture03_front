package com.example.system

import com.example.system.ingredientsDB.Ingredient

object PreviewObject {
    val previewIngredients = listOf(
        Ingredient(
            1,
            "김치",
            //orderQuantity = 400,
            expirationDate = 20250101,
            //autoOrder = true,
            quantity = 1100
        ),
        Ingredient(
            2,
            "물",
            //orderQuantity = 1000,
            expirationDate = 20250303,
            //autoOrder = true,
            quantity = 2100
        ),
        Ingredient(
            3,
            "돼지고기",
            //orderQuantity = 500,
            expirationDate = 20241212,
            //autoOrder = true,
            quantity = 1300
        ),
        Ingredient(
            4,
            "양파",
            //orderQuantity = 300,
            expirationDate = 20241231,
            //autoOrder = true,
            quantity = 1500
        ),
    )
}