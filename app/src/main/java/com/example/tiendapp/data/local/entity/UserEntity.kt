package com.example.tiendapp.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "users")
data class UserEntity(
    @PrimaryKey val id: Long,
    val name: String,
    val email: String,
    val password: String,
    val direccion: String,
    val telefono: Int,
    val role: String
)
