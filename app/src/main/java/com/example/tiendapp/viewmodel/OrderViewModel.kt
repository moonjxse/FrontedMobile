package com.example.tiendapp.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.tiendapp.data.local.AppDatabase
import com.example.tiendapp.data.repository.OrderRepository
import com.example.tiendapp.model.domain.Order
import com.example.tiendapp.model.domain.Role
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class OrderViewModel(application: Application) : AndroidViewModel(application) {

    private val repository =
        OrderRepository(AppDatabase.getDatabase(application).orderDao())

    private val _orders = MutableStateFlow<List<Order>>(emptyList())
    val orders: StateFlow<List<Order>> = _orders.asStateFlow()

    val deliveryOrders: StateFlow<List<Order>> = _orders.asStateFlow() // O filtrar por estado si es necesario

    private val _message = MutableStateFlow<String?>(null)
    val message: StateFlow<String?> = _message.asStateFlow()

    /* ===================== LOAD ===================== */

    fun loadAllOrders() {
        viewModelScope.launch {
            repository.getAllOrders().collect {
                _orders.value = it
            }
        }
    }

    fun loadOrdersByUser(userId: Long) {
        viewModelScope.launch {
            repository.getOrdersByUser(userId).collect {
                _orders.value = it
            }
        }
    }

    fun loadOrdersForRole(role: Role, userId: Long?) {
        when (role) {
            Role.ADMIN,
            Role.VENDEDOR,
            Role.REPARTIDOR -> loadAllOrders()

            Role.CLIENTE -> userId?.let { loadOrdersByUser(it) }
        }
    }

    /* ===================== CREATE ===================== */

    fun createOrder(productId: Long, userId: Long) {
        val fechaActual = SimpleDateFormat(
            "dd/MM/yyyy HH:mm",
            Locale.getDefault()
        ).format(Date())

        val order = Order(
            id = 0L,
            productId = productId,
            userId = userId,
            cantidad = 1,
            estado = "PENDIENTE",
            fecha = fechaActual,
            total = 0
        )

        insertOrder(order)
    }

    fun insertOrder(order: Order) {
        viewModelScope.launch {
            repository.insertOrder(order)
            _message.value = "Pedido creado correctamente"
        }
    }

    /* ===================== UPDATE ===================== */

    fun updateOrder(order: Order) {
        viewModelScope.launch {
            repository.updateOrder(order)
            _message.value = "Pedido actualizado"
        }
    }

    fun markAsDelivered(order: Order) {
        updateOrder(order.copy(estado = "ENTREGADO"))
    }

    fun markAsDeliveredById(orderId: Long) {
        val order = _orders.value.find { it.id == orderId }
        order?.let {
            markAsDelivered(it)
        }
    }

    fun changeOrderStatus(order: Order, newStatus: String) {
        updateOrder(order.copy(estado = newStatus))
    }

    /* ===================== DELETE ===================== */

    fun deleteOrder(order: Order) {
        viewModelScope.launch {
            repository.deleteOrder(order)
            _message.value = "Pedido eliminado"
        }
    }

    fun clearAllOrders() {
        viewModelScope.launch {
            repository.clearAllOrders()
            _message.value = "Todos los pedidos eliminados"
        }
    }

    fun clearMessage() {
        _message.value = null
    }
}
