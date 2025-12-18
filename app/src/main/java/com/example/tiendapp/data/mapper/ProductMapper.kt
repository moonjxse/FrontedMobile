package com.example.tiendapp.data.mapper

import com.example.tiendapp.data.local.entity.ProductEntity
import com.example.tiendapp.model.domain.Product
import com.example.tiendapp.model.dto.ProductDto

// Entity → Domain
fun ProductEntity.toDomain(): Product =
    Product(
        id = id,
        nombre = nombre,
        descripcion = descripcion,
        precio = precio,
        stock = stock,
        imagenUrl = ""
    )

// Domain → Entity
fun Product.toEntity(): ProductEntity =
    ProductEntity(
        id = id,
        nombre = nombre,
        descripcion = descripcion,
        precio = precio,
        stock = stock,
        imagenUrl = ""
    )

// DTO → Domain
fun ProductDto.toDomain(): Product =
    Product(
        id = id,
        nombre = nombre,
        descripcion = descripcion,
        precio = precio,
        stock = stock,
        imagenUrl = ""
    )

// Domain → DTO
fun Product.toDto(): ProductDto =
    ProductDto(
        id = id,
        nombre = nombre,
        descripcion = descripcion,
        precio = precio,
        stock = stock,
        imagenUrl = ""
    )
