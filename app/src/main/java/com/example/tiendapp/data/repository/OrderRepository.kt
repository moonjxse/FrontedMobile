package com.example.tiendapp.data.repository

import com.example.tiendapp.data.local.dao.OrderDao
import com.example.tiendapp.data.mapper.toDomain
import com.example.tiendapp.data.mapper.toEntity
import com.example.tiendapp.model.domain.Order
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class OrderRepository(
    private val orderDao: OrderDao
) {

    // READ ALL
    fun getAllOrders(): Flow<List<Order>> =
        orderDao.getAll().map { list ->
            list.map { it.toDomain() }
        }

    // READ BY USER
    fun getOrdersByUser(userId: Long): Flow<List<Order>> =
        orderDao.getByUser(userId).map { list ->
            list.map { it.toDomain() }
        }

    // CREATE
    suspend fun insertOrder(order: Order) {
        orderDao.insert(order.toEntity())
    }

    // UPDATE
    suspend fun updateOrder(order: Order) {
        orderDao.update(order.toEntity())
    }

    // DELETE
    suspend fun deleteOrder(order: Order) {
        orderDao.delete(order.toEntity())
    }

    // DELETE ALL
    suspend fun clearAllOrders() {
        orderDao.deleteAll()
    }
}
