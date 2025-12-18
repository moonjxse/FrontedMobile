package com.example.tiendapp.data.local.dao

import androidx.room.*
import com.example.tiendapp.data.local.entity.OrderEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface OrderDao {

    // CREATE
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(order: OrderEntity)

    // READ ALL
    @Query("SELECT * FROM orders ORDER BY fecha DESC")
    fun getAll(): Flow<List<OrderEntity>>

    // READ BY USER
    @Query("SELECT * FROM orders WHERE userId = :userId ORDER BY fecha DESC")
    fun getByUser(userId: Long): Flow<List<OrderEntity>>

    // UPDATE
    @Update
    suspend fun update(order: OrderEntity)

    // DELETE ONE
    @Delete
    suspend fun delete(order: OrderEntity)

    // DELETE ALL
    @Query("DELETE FROM orders")
    suspend fun deleteAll()

    // COUNT
    @Query("SELECT COUNT(*) FROM orders")
    suspend fun count(): Int
}
