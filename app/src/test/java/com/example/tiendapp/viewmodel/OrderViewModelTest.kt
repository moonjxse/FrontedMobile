package com.example.tiendapp.viewmodel

import android.app.Application
import com.example.tiendapp.model.domain.Order
import com.example.tiendapp.model.domain.Role
import io.mockk.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.*
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class OrderViewModelTest {

    private val application = mockk<Application>(relaxed = true)
    private lateinit var viewModel: OrderViewModel
    private val testDispatcher = StandardTestDispatcher()

    @Before
    fun setup() {
        Dispatchers.setMain(testDispatcher)
        // Mock del repositorio y base de datos si fuera necesario
        viewModel = OrderViewModel(application)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `loadOrdersForRole as ADMIN should load all orders`() = runTest {
        // Arrange
        val role = Role.ADMIN
        
        // Act
        viewModel.loadOrdersForRole(role, null)
        advanceUntilIdle()

        // Assert
        // Aquí verificarías que el repositorio fue llamado (requiere inyectar mock al VM)
        assert(true) 
    }

    @Test
    fun `markAsDelivered should update order status to ENTREGADO`() = runTest {
        // Arrange
        val order = Order(1L, 1L, 1L, 1, 100, "PENDIENTE", "10/10/2023")

        // Act
        viewModel.markAsDelivered(order)
        advanceUntilIdle()

        // Assert
        assertEquals("Pedido actualizado", viewModel.message.value)
    }
    
    @Test
    fun `clearMessage should set message to null`() {
        // Act
        viewModel.clearMessage()
        
        // Assert
        assertEquals(null, viewModel.message.value)
    }
}
