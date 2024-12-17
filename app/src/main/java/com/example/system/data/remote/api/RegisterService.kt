package com.example.system.data.remote.api

import retrofit2.http.Body
import retrofit2.http.POST

interface RegisterService {
    @POST("register")
    suspend fun registerUser(
        @Body id: String = "id"
    )
}