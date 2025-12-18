package com.example.tiendapp.viewmodel

import android.app.Application
import com.example.tiendapp.model.domain.Region
import io.mockk.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.*
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class ContactViewModelTest {

    private val application = mockk<Application>(relaxed = true)
    private lateinit var viewModel: ContactViewModel
    private val testDispatcher = UnconfinedTestDispatcher()

    @Before
    fun setup() {
        Dispatchers.setMain(testDispatcher)
        // Necesitamos mockear AppDatabase.getDatabase ya que se usa en el init del ViewModel
        mockkStatic("com.example.tiendapp.data.local.AppDatabase")
        val mockDb = mockk<com.example.tiendapp.data.local.AppDatabase>(relaxed = true)
        every { com.example.tiendapp.data.local.AppDatabase.getDatabase(any()) } returns mockDb
        
        viewModel = ContactViewModel(application)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
        unmockkAll()
    }

    @Test
    fun `onNombreChange with short name should set error`() = runTest {
        viewModel.onNombreChange("Ab")
        assertEquals("El nombre debe tener al menos 3 caracteres", viewModel.nombreError.value)
    }

    @Test
    fun `onRegionChange should update regionSeleccionada`() {
        val region = Region(1, "Metropolitana")
        viewModel.onRegionChange(region)
        assertEquals(region, viewModel.regionSeleccionada.value)
        assertEquals(null, viewModel.regionError.value)
    }
}
