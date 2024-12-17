package com.example.system.data.remote.network

import com.example.system.data.remote.api.OpenAIService
import com.example.system.data.remote.api.OrderService
import com.example.system.data.remote.api.RecipeService
import com.example.system.data.remote.api.RegisterService

object ServiceProvider {

    private val recipeServiceInstance: RecipeService =
        RetrofitObject.receiptRetrofit.create(RecipeService::class.java)
    private val openAIServiceInstance: OpenAIService =
        RetrofitObject.openAIRetrofit.create(OpenAIService::class.java)
    private val orderServiceInstance: OrderService =
        RetrofitObject.orderRetrofit.create(OrderService::class.java)
    private val registerServiceInstance: RegisterService =
        RetrofitObject.registerRetrofit.create(RegisterService::class.java)

    fun getRecipeServiceInstance(): RecipeService {
        return recipeServiceInstance
    }

    fun getOpenAIServiceInstance(): OpenAIService {
        return openAIServiceInstance
    }

    fun getOrderServiceInstance(): OrderService {
        return orderServiceInstance
    }

    fun getRegisterServiceInstance(): RegisterService {
        return registerServiceInstance
    }
}