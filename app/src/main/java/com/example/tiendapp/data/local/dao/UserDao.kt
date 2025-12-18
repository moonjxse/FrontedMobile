package com.example.tiendapp.data.local.dao
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.tiendapp.data.local.entity.UserEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface UserDao {


    // CREATE
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(user: UserEntity): Long

    // UPDATE
    @Update
    suspend fun update(user: UserEntity)

    // DELETE
    @Delete
    suspend fun delete(user: UserEntity)

    // READ BY ID
    @Query("SELECT * FROM users WHERE id = :id")
    suspend fun getById(id: Long): UserEntity?

    // READ ALL
    @Query("SELECT * FROM users ORDER BY id DESC")
    fun getAll(): Flow<List<UserEntity>>

    // FIND BY EMAIL
    @Query("SELECT * FROM users WHERE email = :email LIMIT 1")
    suspend fun getByEmail(email: String): UserEntity?

    // LOGIN
    @Query("""
        SELECT * FROM users 
        WHERE email = :email AND password = :password 
        LIMIT 1
    """)
    suspend fun login(email: String, password: String): UserEntity?

    // DELETE ALL
    @Query("DELETE FROM users")
    suspend fun deleteAll()
}
