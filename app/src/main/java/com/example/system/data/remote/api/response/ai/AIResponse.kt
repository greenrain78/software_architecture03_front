package com.example.system.data.remote.api.response.ai

import com.google.gson.annotations.SerializedName

data class AIResponse(
    val choices: List<Choice>,
    val created: Int,
    val id: String,
    val model: String,
    @SerializedName("object")
    val `object`: String,
    @SerializedName("system_fingerprint")
    val systemFingerprint: String,
    val usage: Usage
)

data class Choice(
    @SerializedName("finish_reason")
    val finishReason: String,
    val index: Int,
    val logrobs: Any,
    val message: Message
)

data class CompletionTokensDetails(
    @SerializedName("accepted_prediction_tokens")
    val acceptedPredictionTokens: Int,
    @SerializedName("audio_tokens")
    val audioTokens: Int,
    @SerializedName("average_ppl")
    val reasoningTokens: Int,
    @SerializedName("cached_tokens")
    val rejectedPredictionTokens: Int
)

data class Message(
    val content: String,
    val refusal: Any,
    val role: String
)

data class PromptTokensDetails(
    @SerializedName("accepted_tokens")
    val audioTokens: Int,
    @SerializedName("rejected_tokens")
    val cachedTokens: Int
)

data class Usage(
    @SerializedName("completion_tokens")
    val completionTokens: Int,
    @SerializedName("completion_tokens_details")
    val completionTokensDetails: CompletionTokensDetails,
    @SerializedName("prompt_tokens")
    val promptTokens: Int,
    @SerializedName("prompt_tokens_details")
    val promptTokensDetails: PromptTokensDetails,
    @SerializedName("total_tokens")
    val totalTokens: Int
)