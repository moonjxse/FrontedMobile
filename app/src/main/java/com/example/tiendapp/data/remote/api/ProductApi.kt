package com.example.tiendapp.data.remote.api

import com.example.tiendapp.model.dto.ProductDto
import retrofit2.http.*

interface ProductApi {

    @GET("api/products")
    suspend fun getAllProducts(): List<ProductDto>

    @GET("api/products/{id}")
    suspend fun getProductById(
        @Path("id") id: Long
    ): ProductDto

    @POST("api/products")
    suspend fun createProduct(
        @Body product: ProductDto
    ): ProductDto

    @PUT("api/products/{id}")
    suspend fun updateProduct(
        @Path("id") id: Long,
        @Body product: ProductDto
    ): ProductDto

    @DELETE("api/products/{id}")
    suspend fun deleteProduct(
        @Path("id") id: Long
    )
}
