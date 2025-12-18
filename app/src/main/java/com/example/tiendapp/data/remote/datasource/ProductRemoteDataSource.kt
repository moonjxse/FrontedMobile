package com.example.tiendapp.data.remote.datasource

import com.example.tiendapp.data.remote.api.ProductApi
import com.example.tiendapp.model.dto.ProductDto

class ProductRemoteDataSource(
    private val api: ProductApi
) {

    suspend fun getAll(): List<ProductDto> =
        api.getAllProducts()

    suspend fun getById(id: Long): ProductDto =
        api.getProductById(id)

    suspend fun create(product: ProductDto): ProductDto =
        api.createProduct(product)

    suspend fun update(id: Long, product: ProductDto): ProductDto =
        api.updateProduct(id, product)

    suspend fun delete(id: Long) =
        api.deleteProduct(id)
}
