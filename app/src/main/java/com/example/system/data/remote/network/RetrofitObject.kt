package com.example.system.data.remote.network

import com.google.gson.Gson
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitObject {

    private const val RECEIPT_BASE_URL = ""
    private const val OPENAI_BASE_URL = ""
    private const val ORDER_BASE_URL = ""
    private const val REGISTER_BASE_URL = ""

    val receiptRetrofit: Retrofit = Retrofit.Builder()
        .baseUrl(RECEIPT_BASE_URL)
        .addConverterFactory(GsonConverterFactory.create(Gson()))
        .build()

    val openAIRetrofit: Retrofit = Retrofit.Builder()
        .baseUrl(OPENAI_BASE_URL)
        .addConverterFactory(GsonConverterFactory.create(Gson()))
        .build()

    val orderRetrofit: Retrofit = Retrofit.Builder()
        .baseUrl(ORDER_BASE_URL)
        .addConverterFactory(GsonConverterFactory.create(Gson()))
        .build()

    val registerRetrofit: Retrofit = Retrofit.Builder()
        .baseUrl(REGISTER_BASE_URL)
        .addConverterFactory(GsonConverterFactory.create(Gson()))
        .build()
}