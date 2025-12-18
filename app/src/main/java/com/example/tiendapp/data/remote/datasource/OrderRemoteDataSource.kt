package com.example.tiendapp.data.remote.datasource

import com.example.tiendapp.data.remote.api.OrderApi
import com.example.tiendapp.model.dto.OrderDto

class OrderRemoteDataSource(
    private val api: OrderApi
) {

    suspend fun getAll(): List<OrderDto> =
        api.getAllOrders()

    suspend fun getByUser(userId: Long): List<OrderDto> =
        api.getOrdersByUser(userId)

    suspend fun create(order: OrderDto): OrderDto =
        api.createOrder(order)

    suspend fun update(id: Long, order: OrderDto): OrderDto =
        api.updateOrder(id, order)

    suspend fun delete(id: Long) =
        api.deleteOrder(id)
}
