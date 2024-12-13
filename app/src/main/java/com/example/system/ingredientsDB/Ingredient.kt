package com.example.system.ingredientsDB

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.time.LocalDate

@Entity(tableName = "Ingredient")
data class Ingredient(
    @PrimaryKey(autoGenerate = true)
    var id : Int = 0,
    @ColumnInfo
    var name : String = "",
    @ColumnInfo
    var quantity : Int = 0,
    @ColumnInfo
    var expirationDate : Long = 0,
)