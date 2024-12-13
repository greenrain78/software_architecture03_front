package com.example.system.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.system.data.repository.OrderRepository
import com.example.system.ingredientsDB.Ingredient
import com.example.system.data.repository.IngredientRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class OrderViewModel @Inject constructor(
    private val ingredientRepository: IngredientRepository,
    private val orderRepository: OrderRepository
) : ViewModel() {

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
