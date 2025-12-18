package com.example.tiendapp.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "products")
data class ProductEntity(
    @PrimaryKey val id: Long,
    val nombre: String,
    val descripcion: String,
    val precio: Int,
    val stock: Int,
    val imagenUrl: String
)
