package com.example.tiendapp.data.mapper

import com.example.tiendapp.data.local.entity.OrderEntity
import com.example.tiendapp.model.domain.Order
import com.example.tiendapp.model.dto.OrderDto

// Entity → Domain
fun OrderEntity.toDomain(): Order =
    Order(
        id = id,
        userId = userId,
        productId = productId,
        cantidad = cantidad,
        total = total,
        estado = estado,
        fecha = fecha
    )

// Domain → Entity
fun Order.toEntity(): OrderEntity =
    OrderEntity(
        id = id,
        userId = userId,
        productId = productId,
        cantidad = cantidad,
        total = total,
        estado = estado,
        fecha = fecha
    )

// DTO → Domain
fun OrderDto.toDomain(): Order =
    Order(
        id = id ?: 0L,
        userId = userId,
        productId = productId,
        cantidad = cantidad,
        total = total,
        estado = estado,
        fecha = fecha
    )

// Domain → DTO
fun Order.toDto(): OrderDto =
    OrderDto(
        id = if (id == 0L) null else id,
        userId = userId,
        productId = productId,
        cantidad = cantidad,
        total = total,
        estado = estado,
        fecha = fecha
    )
