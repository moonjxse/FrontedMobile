package com.example.tiendapp.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "orders")
data class OrderEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0L,
    val userId: Long,
    val productId: Long,
    val cantidad: Int,
    val total: Int,
    val estado: String,
    val fecha: String
)
