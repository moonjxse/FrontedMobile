package com.example.tiendapp.model.dto

data class OrderDto(
    val id: Long? = null,
    val userId: Long,
    val productId: Long,
    val cantidad: Int,
    val total: Int,
    val estado: String,
    val fecha: String
)
