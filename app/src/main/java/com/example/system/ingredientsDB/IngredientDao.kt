package com.example.system.ingredientsDB

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update

@Dao
interface IngredientDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(ingredient:Ingredient)

    @Update
    suspend fun update(ingredient:Ingredient)

    @Delete
    suspend fun delete(ingredient:Ingredient)

    @Query("SELECT * FROM ingredient")
    suspend fun getAll() : List<Ingredient>

    @Query("SELECT * FROM ingredient WHERE expirationDate < :expirationDate")
    suspend fun getExpiredIngredients(expirationDate : Long) : List<Ingredient>



    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAutoOrderItem(orderItem: OrderItem)

    @Update
    suspend fun updateAutoOrderItem(orderItem: OrderItem)

    @Delete
    suspend fun deleteAutoOrderItem(orderItem: OrderItem)

    @Query("SELECT * FROM orderitem WHERE autoOrder == 1")
    suspend fun getAutoOrderItems() : List<OrderItem>



    //테스트용
    // 사용 x
    @Query("DELETE FROM ingredient")
    suspend fun deleteAllIngredients()
}