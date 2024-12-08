package com.example.system.data.remote.network

import com.example.system.data.api.OpenAIService
import com.example.system.data.api.OrderService
import com.example.system.data.api.ReceiptService
import com.example.system.data.api.RegisterService

object ServiceProvider {

    private val receiptServiceInstance: ReceiptService =
        RetrofitObject.receiptRetrofit.create(ReceiptService::class.java)
    private val openAIServiceInstance: OpenAIService =
        RetrofitObject.openAIRetrofit.create(OpenAIService::class.java)
    private val orderServiceInstance: OrderService =
        RetrofitObject.orderRetrofit.create(OrderService::class.java)
    private val registerServiceInstance: RegisterService =
        RetrofitObject.registerRetrofit.create(RegisterService::class.java)

    fun getReceiptServiceInstance(): ReceiptService {
        return receiptServiceInstance
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