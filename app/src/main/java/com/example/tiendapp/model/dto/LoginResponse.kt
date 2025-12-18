package com.example.tiendapp.model.dto

data class LoginResponse(
    val id: Long,
    val email: String,
    val role: String,
    val token: String
)
