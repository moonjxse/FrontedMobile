package com.example.tiendapp.model.dto

data class ProductDto(
    val id: Long,
    val nombre: String,
    val descripcion: String,
    val precio: Int,
    val stock: Int,
    val imagenUrl: String
)
