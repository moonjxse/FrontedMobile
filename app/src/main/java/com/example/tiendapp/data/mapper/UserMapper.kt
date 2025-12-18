package com.example.tiendapp.data.mapper

import com.example.tiendapp.data.local.entity.UserEntity
import com.example.tiendapp.model.domain.Role
import com.example.tiendapp.model.domain.User
import com.example.tiendapp.model.dto.UserDto

fun UserEntity.toDomain(): User =
    User(
        id = id,
        name = name,
        email = email,
        password = password,
        direccion = direccion,
        telefono = telefono,
        role = role.toRole()
    )

fun User.toEntity(): UserEntity =
    UserEntity(
        id = id,
        name = name,
        email = email,
        password = password,
        direccion = direccion,
        telefono = telefono,
        role = role.name
    )


fun UserDto.toDomain(): User =
    User(
        id = id,
        name = name,
        email = email,
        password = password,
        direccion = direccion,
        telefono = telefono,
        role = role.toRole()
    )

fun User.toDto(): UserDto =
    UserDto(
        id = id,
        name = name,
        email = email,
        password = password,
        direccion = direccion,
        telefono = telefono,
        role = role.name
    )

private fun String?.toRole(): Role =
    try {
        Role.valueOf(this?.uppercase() ?: Role.CLIENTE.name)
    } catch (e: IllegalArgumentException) {
        Role.CLIENTE
    }
