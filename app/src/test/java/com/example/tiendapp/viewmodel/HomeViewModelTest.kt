package com.example.tiendapp.viewmodel

import com.example.tiendapp.model.domain.Role
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.*
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class HomeViewModelTest {

    private lateinit var viewModel: HomeViewModel
    private val testDispatcher = StandardTestDispatcher()

    @Before
    fun setup() {
        Dispatchers.setMain(testDispatcher)
        viewModel = HomeViewModel()
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `nextImage should increment index and wrap around`() = runTest {
        val initialIndex = viewModel.currentImageIndex.value
        viewModel.nextImage()
        assertEquals((initialIndex + 1) % viewModel.carouselImages.size, viewModel.currentImageIndex.value)
        
        // Wrap around
        repeat(viewModel.carouselImages.size) { viewModel.nextImage() }
        assertEquals(0, viewModel.currentImageIndex.value)
    }

    @Test
    fun `previousImage should decrement index and wrap around`() = runTest {
        viewModel.goToImage(0)
        viewModel.previousImage()
        assertEquals(viewModel.carouselImages.size - 1, viewModel.currentImageIndex.value)
    }

    @Test
    fun `goToImage should set valid index`() = runTest {
        viewModel.goToImage(1)
        assertEquals(1, viewModel.currentImageIndex.value)
    }

    @Test
    fun `goToImage should ignore invalid index`() = runTest {
        val initialIndex = viewModel.currentImageIndex.value
        viewModel.goToImage(100)
        assertEquals(initialIndex, viewModel.currentImageIndex.value)
    }

    @Test
    fun `updatePermissions for ADMIN should set all permissions to true`() = runTest {
        viewModel.updatePermissions(Role.ADMIN)
        assertTrue(viewModel.canViewAllOrders.value)
        assertTrue(viewModel.canViewMyOrders.value)
        assertTrue(viewModel.canManageProducts.value)
        assertTrue(viewModel.canUseCamera.value)
    }

    @Test
    fun `updatePermissions for CLIENTE should set specific permissions`() = runTest {
        viewModel.updatePermissions(Role.CLIENTE)
        assertEquals(false, viewModel.canViewAllOrders.value)
        assertEquals(true, viewModel.canViewMyOrders.value)
        assertEquals(false, viewModel.canManageProducts.value)
        assertEquals(true, viewModel.canUseCamera.value)
    }

    @Test
    fun `fetchWeather should update weather state`() = runTest {
        viewModel.fetchWeather()
        advanceUntilIdle()
        assertEquals("22°C", viewModel.weather.value.temperature)
        assertEquals("Despejado en Santiago", viewModel.weather.value.description)
    }
}
