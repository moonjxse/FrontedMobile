package com.example.tiendapp.model.dto

data class UserDto(
    val id: Long,
    val name: String,
    val email: String,
    val password: String,
    val direccion: String,
    val telefono: Int,
    val role: String
)
