package com.example.tiendapp.model.domain
import kotlinx.serialization.Serializable


@Serializable
data class Region(
    val id: Int,
    val nombre: String
)