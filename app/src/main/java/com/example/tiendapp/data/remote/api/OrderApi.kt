package com.example.tiendapp.data.remote.api

import com.example.tiendapp.model.dto.OrderDto
import retrofit2.http.*

interface OrderApi {

    @GET("api/orders")
    suspend fun getAllOrders(): List<OrderDto>

    @GET("api/orders/{id}")
    suspend fun getOrderById(
        @Path("id") id: Long
    ): OrderDto

    @GET("api/orders/user/{userId}")
    suspend fun getOrdersByUser(
        @Path("userId") userId: Long
    ): List<OrderDto>

    @POST("api/orders")
    suspend fun createOrder(
        @Body order: OrderDto
    ): OrderDto

    @PUT("api/orders/{id}")
    suspend fun updateOrder(
        @Path("id") id: Long,
        @Body order: OrderDto
    ): OrderDto

    @DELETE("api/orders/{id}")
    suspend fun deleteOrder(
        @Path("id") id: Long
    )
}
