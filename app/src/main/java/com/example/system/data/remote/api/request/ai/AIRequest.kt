package com.example.system.data.remote.api.request.ai

import android.net.Uri
import com.google.gson.annotations.SerializedName

data class AIRequest(
    @SerializedName("max_tokens")
    val maxTokens: Int = 300,
    val messages: List<Message> = listOf(
        Message(
            content = listOf(
                Content(
                    imageUrl = ImageUrl(""),
                    type = "image_url"
                ),
                Content(
                    text = "Tell me the name of the food in this picture. Not a sentence. Just one word",
                    type = "text"
                )
            ),
            role = "user"
        )
    ),
    val model: String = "gpt-4-turbo"
)

data class Content(
    @SerializedName("type")
    val type: String,

    @SerializedName("image_url")
    var imageUrl: ImageUrl? = null, // 이미지 URL이 있을 때만 사용

    @SerializedName("text")
    val text: String? = null // 텍스트가 있을 때만 사용
)

data class ImageUrl(
    val url: String
)

data class Message(
    val content: List<Content>,
    val role: String
)