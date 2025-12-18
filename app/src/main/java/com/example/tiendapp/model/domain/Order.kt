package com.example.tiendapp.model.domain

data class Order(
    val id: Long,
    val userId: Long,
    val productId: Long,
    val cantidad: Int,
    val total: Int,
    val estado: String,
    val fecha: String
)
