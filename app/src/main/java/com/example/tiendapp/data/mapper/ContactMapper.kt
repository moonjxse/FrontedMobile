package com.example.tiendapp.data.mapper

import com.example.tiendapp.data.local.entity.ContactEntity
import com.example.tiendapp.model.domain.Contact
import com.example.tiendapp.model.dto.ContactDto

// Entity → Domain
fun ContactEntity.toDomain(): Contact =
    Contact(
        id = id,
        nombre = nombre,
        correo = correo,
        telefono = telefono,
        region = region,
        mensaje = mensaje
    )

// Domain → Entity
fun Contact.toEntity(): ContactEntity =
    ContactEntity(
        id = id,
        nombre = nombre,
        correo = correo,
        telefono = telefono,
        region = region,
        mensaje = mensaje
    )

// DTO → Domain
fun ContactDto.toDomain(): Contact =
    Contact(
        id = id,
        nombre = nombre,
        correo = correo,
        telefono = telefono,
        region = region,
        mensaje = mensaje
    )

// Domain → DTO
fun Contact.toDto(): ContactDto =
    ContactDto(
        id = id,
        nombre = nombre,
        correo = correo,
        telefono = telefono,
        region = region,
        mensaje = mensaje
    )
