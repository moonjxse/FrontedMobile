package com.example.tiendapp.viewmodel

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tiendapp.data.repository.UserRepository
import com.example.tiendapp.model.domain.Role
import com.example.tiendapp.model.domain.User
import com.example.tiendapp.utils.ValidationUtils
import com.example.tiendapp.utils.JsonUtils
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class UserViewModel(
    private val userRepository: UserRepository,
    private val application: Application? = null
) : ViewModel() {

    init {
        prepopulateUsers()
    }

    private fun prepopulateUsers() {
        application?.let { app ->
            viewModelScope.launch {
                // Si no hay usuarios en la DB, cargamos los de assets
                // Nota: Asumimos que userRepository tiene un método para contar o similar
                // Por simplicidad, intentamos insertar los de assets (Room ignorará si ya existen por ID si está configurado OnConflict)
                val assetsUsers = JsonUtils.loadUsersFromAssets(app)
                assetsUsers.forEach { user ->
                    val existing = userRepository.getByEmail(user.email)
                    if (existing == null) {
                        userRepository.insert(user)
                    }
                }
            }
        }
    }

    /* =========================
       ESTADO DE USUARIO
       ========================= */

    private val _currentUser = MutableStateFlow<User?>(null)
    val currentUser: StateFlow<User?> = _currentUser.asStateFlow()

    private val _message = MutableStateFlow<String?>(null)
    val message: StateFlow<String?> = _message.asStateFlow()

    /* =========================
       AUTH
       ========================= */

    fun login(email: String, password: String) {
        val cleanEmail = email.trim()
        val cleanPassword = password.trim()

        when {
            !ValidationUtils.isValidEmail(cleanEmail) -> {
                _message.value = "Correo electrónico inválido"
            }

            cleanPassword.isBlank() -> {
                _message.value = "Debe ingresar su contraseña"
            }

            else -> {
                viewModelScope.launch {
                    val user = userRepository.login(cleanEmail, cleanPassword)
                    if (user == null) {
                        _message.value = "Correo o contraseña incorrectos"
                    } else {
                        _currentUser.value = user
                        _message.value = "Inicio de sesión exitoso"
                    }
                }
            }
        }
    }

    fun registerUser(user: User) {
        val name = user.name.trim()
        val email = user.email.trim()
        val password = user.password.trim()

        val nameError = ValidationUtils.getNombreErrorMessage(name)
        val emailError = ValidationUtils.getEmailErrorMessage(email)

        when {
            nameError != null -> {
                _message.value = nameError
            }

            emailError != null -> {
                _message.value = emailError
            }

            password.length < 6 -> {
                _message.value = "La contraseña debe tener al menos 6 caracteres"
            }

            else -> {
                viewModelScope.launch {
                    val existingUser = userRepository.getByEmail(email)
                    if (existingUser != null) {
                        _message.value = "El correo ya está registrado"
                    } else {
                        userRepository.insert(user)
                        _message.value = "Usuario registrado correctamente"
                    }
                }
            }
        }
    }


    fun logout() {
        _currentUser.value = null
        _message.value = null
    }

    fun clearMessage() {
        _message.value = null
    }

    /* =========================
       HELPERS DE SESIÓN
       ========================= */

    fun isLoggedIn(): Boolean =
        _currentUser.value != null

    fun getUserRole(): Role? =
        _currentUser.value?.role

    /* =========================
       PERMISOS POR ROL
       ========================= */

    fun canBuyProducts(): Boolean =
        when (_currentUser.value?.role) {
            Role.CLIENTE -> true
            else -> false
        }

    fun canManageProducts(): Boolean =
        when (_currentUser.value?.role) {
            Role.ADMIN,
            Role.VENDEDOR -> true
            else -> false
        }

    fun canViewAllOrders(): Boolean =
        when (_currentUser.value?.role) {
            Role.ADMIN,
            Role.VENDEDOR,
            Role.REPARTIDOR -> true
            else -> false
        }

    fun canDeliverOrders(): Boolean =
        _currentUser.value?.role == Role.REPARTIDOR

    fun isAdmin(): Boolean =
        _currentUser.value?.role == Role.ADMIN
}
