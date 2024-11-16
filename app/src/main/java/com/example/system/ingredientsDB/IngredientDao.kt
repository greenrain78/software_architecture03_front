package com.example.system.ingredientsDB

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow
import java.time.LocalDate

@Dao
interface IngredientDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(ingredient:Ingredient)

    @Update
    suspend fun update(ingredient:Ingredient)

    @Delete
    suspend fun delete(ingredient:Ingredient)

    @Query("SELECT * FROM ingredient")
    fun getAllIngredients() : Flow<List<Ingredient>>

    @Query("SELECT * FROM ingredient WHERE expirationDate + 86400 < :expirationDate")
    fun getExpiredIngredients(expirationDate : LocalDate) : Flow<List<Ingredient>>

    @Query("SELECT * FROM ingredient WHERE expirationDate >= :expirationDate AND expirationDate <= strftime('%s', :expirationDate) + (7 * 86400)")
    fun getWarningIngredients(expirationDate: LocalDate): Flow<List<Ingredient>>
}