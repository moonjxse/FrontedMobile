package com.example.tiendapp.model.domain
data class User(
    val id: Long,
    val name: String,
    val email: String,
    val password: String,
    val direccion: String,
    val telefono: Int,
    val role : Role
)
