package com.example.tiendapp.data.remote.datasource

import com.example.tiendapp.data.remote.api.UserApi
import com.example.tiendapp.model.dto.LoginDto
import com.example.tiendapp.model.dto.UserDto

class UserRemoteDataSource(
    private val api: UserApi
) {

    // LOGIN
    suspend fun login(email: String, password: String): UserDto? {
        return api.login(LoginDto(email, password))
    }

    // READ ALL
    suspend fun getAll(): List<UserDto> =
        api.getAllUsers()

    // READ BY ID
    suspend fun getById(id: Long): UserDto =
        api.getUserById(id)

    // CREATE
    suspend fun create(user: UserDto): UserDto =
        api.createUser(user)

    // UPDATE
    suspend fun update(id: Long, user: UserDto): UserDto =
        api.updateUser(id, user)

    // DELETE
    suspend fun delete(id: Long) =
        api.deleteUser(id)
}
