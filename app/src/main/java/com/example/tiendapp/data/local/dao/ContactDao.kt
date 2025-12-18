package com.example.tiendapp.data.local.dao

import androidx.room.*
import com.example.tiendapp.data.local.entity.ContactEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface ContactDao {

    // CREATE
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(contact: ContactEntity)

    // READ ALL
    @Query("SELECT * FROM contacts ORDER BY id DESC")
    fun getAll(): Flow<List<ContactEntity>>

    // READ BY ID
    @Query("SELECT * FROM contacts WHERE id = :id")
    suspend fun getById(id: Long): ContactEntity?

    // UPDATE
    @Update
    suspend fun update(contact: ContactEntity)

    // DELETE ONE
    @Delete
    suspend fun delete(contact: ContactEntity)

    // DELETE ALL
    @Query("DELETE FROM contacts")
    suspend fun deleteAll()

    // COUNT
    @Query("SELECT COUNT(*) FROM contacts")
    suspend fun count(): Int
}
