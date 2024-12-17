package com.example.system.data.remote.api.request.ai

import android.net.Uri
import com.google.gson.annotations.SerializedName

data class AIRequest(
    @SerializedName("max_tokens")
    val maxTokens: Int = 100,
    val messages: List<Message> = listOf(
        Message(
            content = listOf(
                Content(
                    imageUrl = ImageUrl(),
                    type = "image_url"
                ),
                Content(
                    text = "say only what it is, like just banana",
                    type = "text"
                )
            ),
            role = "user"
        )
    ),
    val model: String = "gpt-4-turbo"
)

data class Content(
    @SerializedName("image_url")
    val imageUrl: ImageUrl = ImageUrl(),
    val text: String = "",
    val type: String
)

data class ImageUrl(
    var url: Uri = Uri.parse("")
)

data class Message(
    val content: List<Content>,
    val role: String
)