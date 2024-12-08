package com.example.system.data.api.request.order


import com.google.gson.annotations.SerializedName

data class OrderRequest(
    val customerName: String,
    val productName: String,
    val quantity: Int
)