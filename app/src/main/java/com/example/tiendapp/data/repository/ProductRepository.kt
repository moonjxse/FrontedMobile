package com.example.tiendapp.data.repository

import com.example.tiendapp.data.local.dao.ProductDao
import com.example.tiendapp.data.mapper.toDomain
import com.example.tiendapp.data.mapper.toEntity
import com.example.tiendapp.model.domain.Product
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class ProductRepository(
    private val productDao: ProductDao
) {

    // READ ALL
    val allProducts: Flow<List<Product>> =
        productDao.getAll().map { list ->
            list.map { it.toDomain() }
        }

    // CREATE
    suspend fun insertProduct(product: Product) {
        productDao.insert(product.toEntity())
    }

    // UPDATE
    suspend fun updateProduct(product: Product) {
        productDao.update(product.toEntity())
    }

    // DELETE
    suspend fun deleteProduct(product: Product) {
        productDao.delete(product.toEntity())
    }

    // READ BY ID
    suspend fun getProductById(id: Long): Product? =
        productDao.getById(id)?.toDomain()

    // DELETE ALL
    suspend fun clearAllProducts() {
        productDao.deleteAll()
    }

    // INSERT LIST
    suspend fun insertAll(products: List<Product>) {
        productDao.insertAll(products.map { it.toEntity() })
    }

    // COUNT
    suspend fun countProducts(): Int =
        productDao.count()
}
