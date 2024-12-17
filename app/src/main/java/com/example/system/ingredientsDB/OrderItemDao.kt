package com.example.system.ingredientsDB

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update

@Dao
interface OrderItemDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(orderItem: OrderItem)

    @Update
    suspend fun update(orderItem: OrderItem)

    @Delete
    suspend fun delete(orderItem: OrderItem)

    @Query("SELECT * FROM orderitem WHERE autoOrder == 1")
    suspend fun getAutoOrderItems() : List<OrderItem>
}