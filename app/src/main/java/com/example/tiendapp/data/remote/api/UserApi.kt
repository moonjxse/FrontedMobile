package com.example.tiendapp.data.remote.api

import com.example.tiendapp.model.dto.*
import retrofit2.http.*

interface UserApi {

    @GET("api/users")
    suspend fun getAllUsers(): List<UserDto>

    @GET("api/users/{id}")
    suspend fun getUserById(
        @Path("id") id: Long
    ): UserDto

    @POST("api/users")
    suspend fun createUser(
        @Body user: UserDto
    ): UserDto

    @PUT("api/users/{id}")
    suspend fun updateUser(
        @Path("id") id: Long,
        @Body user: UserDto
    ): UserDto

    @DELETE("api/users/{id}")
    suspend fun deleteUser(
        @Path("id") id: Long
    )

    // LOGIN
    @POST("api/users/login")
    suspend fun login(
        @Body loginDto: LoginDto
    ): UserDto?

}
