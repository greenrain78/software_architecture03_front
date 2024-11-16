package com.example.system.network

import com.google.gson.FieldNamingPolicy
import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

object NetworkModule {

    const val BASE_URL = "https://api.openai.com/v1/"
    const val GPT_KEY =
        "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"

    private val client: OkHttpClient = OkHttpClient.Builder()
        .addInterceptor { chain ->
            val request = chain.request().newBuilder()
                .addHeader("Authorization", "Bearer $GPT_KEY")
                .build()
            chain.proceed(request)
        }.build()

    private val gson = GsonBuilder()
        .setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
        .create()

    private var retrofit: Retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .client(client)
        .addConverterFactory(GsonConverterFactory.create(gson))
        .build()

    var openAIService = retrofit.create(OpenAIService::class.java)


}