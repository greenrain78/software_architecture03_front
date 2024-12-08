package com.example.system.data.api.response.order


import com.google.gson.annotations.SerializedName

class OrderResponse : ArrayList<OrderResponseItem>()

data class OrderResponseItem(
    val createdAt: String,
    val customerName: String,
    val id: Int,
    val price: Int,
    val productName: String,
    val quantity: Int,
    val status: String
)