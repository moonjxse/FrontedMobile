package com.example.tiendapp.data.local.dao

import androidx.room.*
import com.example.tiendapp.data.local.entity.ProductEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface ProductDao {

    // CREATE (uno)
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(product: ProductEntity)

    // CREATE (lista)
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(products: List<ProductEntity>)

    // UPDATE
    @Update
    suspend fun update(product: ProductEntity)

    // DELETE uno
    @Delete
    suspend fun delete(product: ProductEntity)

    // READ ALL
    @Query("SELECT * FROM products ORDER BY id DESC")
    fun getAll(): Flow<List<ProductEntity>>

    // READ BY ID
    @Query("SELECT * FROM products WHERE id = :id")
    suspend fun getById(id: Long): ProductEntity?

    // DELETE ALL
    @Query("DELETE FROM products")
    suspend fun deleteAll()

    // COUNT
    @Query("SELECT COUNT(*) FROM products")
    suspend fun count(): Int
}
