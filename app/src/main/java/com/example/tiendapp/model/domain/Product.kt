package com.example.tiendapp.model.domain

data class Product(
    val id: Long,
    val nombre: String,
    val descripcion: String,
    val precio: Int,
    val stock: Int,
    val imagenUrl: String
)
