package com.example.tiendapp.model.domain

data class Contact(
    val id: Long,
    val nombre: String,
    val correo: String,
    val telefono: Int,
    val region: String,
    val mensaje: String
)
