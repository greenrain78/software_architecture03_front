package com.example.system.data.remote.api

import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface OpenAIService {
    @POST("chat/completions") // API 엔드포인트에 맞게 설정
    suspend fun generateImageFromUrl(
        @Body request: ChatRequest
    ): Response<GPTResponse>
}