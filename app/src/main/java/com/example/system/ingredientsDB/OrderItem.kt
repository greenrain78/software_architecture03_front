package com.example.system.ingredientsDB

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "OrderItem",
    indices = [Index(value = ["name"], unique = true)]
)
data class OrderItem(
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0,
    @ColumnInfo
    var name: String,
    @ColumnInfo
    var autoOrder: Boolean = false,
    @ColumnInfo
    var orderQuantity: Int = 0
)
