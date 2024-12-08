package com.example.system.ui.viewmodel

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.system.data.repository.OrderRepository
import com.example.system.ingredientsDB.Ingredient
import com.example.system.ingredientsDB.IngredientRepository
import kotlinx.coroutines.launch

class OrderViewModel(context: Context) : ViewModel() {

    private val ingredientRepository = IngredientRepository(context)
    private val orderRepository = OrderRepository()

    fun registerAutomaticOrder(ingredient: Ingredient) {
        viewModelScope.launch {
            orderRepository.register(ingredient)
        }
    }

    fun order(ingredient: Ingredient) {
        viewModelScope.launch {
            orderRepository.order(ingredient)
        }
    }
}
