package com.example.tiendapp.data.repository

import com.example.tiendapp.data.local.dao.UserDao
import com.example.tiendapp.data.mapper.toDomain
import com.example.tiendapp.data.mapper.toEntity
import com.example.tiendapp.model.domain.User
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class UserRepository(
    private val userDao: UserDao
) {

    // READ ALL
    val allUsers: Flow<List<User>> =
        userDao.getAll().map { list ->
            list.map { it.toDomain() }
        }

    // CREATE
    suspend fun insert(user: User) {
        userDao.insert(user.toEntity())
    }

    // UPDATE
    suspend fun update(user: User) {
        userDao.update(user.toEntity())
    }

    // DELETE
    suspend fun delete(user: User) {
        userDao.delete(user.toEntity())
    }

    // READ BY ID
    suspend fun getById(id: Long): User? =
        userDao.getById(id)?.toDomain()

    // READ BY EMAIL
    suspend fun getByEmail(email: String): User? =
        userDao.getByEmail(email)?.toDomain()

    // LOGIN
    suspend fun login(email: String, password: String): User? =
        userDao.login(email, password)?.toDomain()

    // DELETE ALL
    suspend fun deleteAll() {
        userDao.deleteAll()
    }
}
