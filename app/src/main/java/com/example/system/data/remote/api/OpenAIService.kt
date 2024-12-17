package com.example.system.data.remote.api

import android.util.Log
import com.example.system.BuildConfig
import com.example.system.data.remote.api.request.ai.AIRequest
import com.example.system.data.remote.api.response.ai.AIResponse
import com.google.gson.Gson
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.POST

interface OpenAIService {

    @POST("v1/chat/completions") // API 엔드포인트에 맞게 설정
    suspend fun recognizeImage(
        @Header ("Authorization") token: String = "Bearer ${BuildConfig.GPT_KEY}",
        @Header ("Content-Type") contentType: String = "application/json",
        @Body request: AIRequest
    ): Response<AIResponse>
}