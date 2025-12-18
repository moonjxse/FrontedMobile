package com.example.tiendapp.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "contacts")
data class ContactEntity(
    @PrimaryKey val id: Long,
    val nombre: String,
    val correo: String,
    val telefono: Int,
    val region: String,
    val mensaje: String
)
