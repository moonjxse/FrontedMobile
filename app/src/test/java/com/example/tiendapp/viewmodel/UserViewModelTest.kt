package com.example.tiendapp.viewmodel

import com.example.tiendapp.data.repository.UserRepository
import com.example.tiendapp.model.domain.Role
import com.example.tiendapp.model.domain.User
import io.mockk.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.*
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class UserViewModelTest {

    private val repository = mockk<UserRepository>()
    private lateinit var viewModel: UserViewModel
    private val testDispatcher = StandardTestDispatcher()

    @Before
    fun setup() {
        Dispatchers.setMain(testDispatcher)
        viewModel = UserViewModel(repository)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `login with empty password should show error message`() = runTest {
        viewModel.login("test@test.com", " ")
        assertEquals("Debe ingresar su contraseña", viewModel.message.value)
    }

    @Test
    fun `login with valid credentials should update currentUser`() = runTest {
        val mockUser = User(1L, "Victor", "victor@test.com", "123456", "Calle 1", 123, Role.ADMIN)
        coEvery { repository.login(any(), any()) } returns mockUser

        viewModel.login("victor@test.com", "123456")
        advanceUntilIdle()

        assertEquals(mockUser, viewModel.currentUser.value)
        assertEquals("Inicio de sesión exitoso", viewModel.message.value)
    }

    @Test
    fun `logout should clear currentUser`() = runTest {
        viewModel.logout()
        assertEquals(null, viewModel.currentUser.value)
        assertEquals(null, viewModel.message.value)
    }

    @Test
    fun `isAdmin should return true for ADMIN role`() = runTest {
        val mockUser = User(1L, "Admin", "a@a.com", "123", "Dir", 1, Role.ADMIN)
        coEvery { repository.login(any(), any()) } returns mockUser
        
        viewModel.login("a@a.com", "123")
        advanceUntilIdle()
        
        assertEquals(true, viewModel.isAdmin())
    }
}
