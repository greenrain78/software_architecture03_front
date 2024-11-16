package com.example.system.data.gpt

import com.google.gson.annotations.SerializedName

data class InterpretationResponse(
    val interpretation: String
)


data class GPTResponse(
    val choices: List<Choice>,
    val created: Int,
    val id: String,
    val model: String,
    val `object`: String,
    val systemFingerprint: Any,
    val usage: Usage
)

data class Choice(
    val finishReason: String,
    val index: Int,
    val logprobs: Any,
    val message: Message
)

data class Usage(
    val completionTokens: Int,
    val completionTokensDetails: CompletionTokensDetails,
    val promptTokens: Int,
    val promptTokensDetails: PromptTokensDetails,
    val totalTokens: Int
)

data class CompletionTokensDetails(
    @SerializedName("reasoning_tokens")
    val reasoningTokens: Int
)

data class PromptTokensDetails(
    @SerializedName("cached_tokens")
    val cachedTokens: Int
)