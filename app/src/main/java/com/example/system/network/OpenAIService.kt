package com.example.system.network

import com.example.system.data.gpt.ChatRequest
import com.example.system.data.gpt.GPTResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface OpenAIService {
    @POST("chat/completions") // API 엔드포인트에 맞게 설정
    suspend fun generateImageFromUrl(
        @Body request: ChatRequest
    ): Response<GPTResponse>
}