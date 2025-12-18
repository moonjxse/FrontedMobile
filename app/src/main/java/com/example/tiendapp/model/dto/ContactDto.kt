package com.example.tiendapp.model.dto

data class ContactDto(
    val id: Long,
    val nombre: String,
    val correo: String,
    val telefono: Int,
    val region: String,
    val mensaje: String
)
