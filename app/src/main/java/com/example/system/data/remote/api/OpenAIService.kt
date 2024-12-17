package com.example.system.data.remote.api

import com.example.system.data.remote.api.request.ai.AIRequest
import com.example.system.data.remote.api.response.ai.AIResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface OpenAIService {

    @POST("chat/completions") // API 엔드포인트에 맞게 설정
    suspend fun recognizeImage(
        @Body request: AIRequest
    ): Response<AIResponse>
}