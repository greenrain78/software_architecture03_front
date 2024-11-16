package com.example.system.data.gpt

data class ImageRequest(val imageUrl: String)

data class ChatRequest(
    val model: String = "gpt-3.5-turbo",
    val messages: List<MessageRequest>,
    val maxTokens: Int
)

data class MessageRequest(
    val role: String = "user",
    val content: String
)

data class Message(
    val content: String,
    val refusal: Any,
    val role: String
)
