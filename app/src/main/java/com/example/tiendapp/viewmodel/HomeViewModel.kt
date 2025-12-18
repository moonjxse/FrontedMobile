package com.example.tiendapp.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tiendapp.R
import com.example.tiendapp.model.domain.Role
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.delay

data class WeatherState(
    val temperature: String = "--",
    val description: String = "Cargando clima...",
    val icon: String = ""
)

class HomeViewModel : ViewModel() {

    /* -------------------- CARRUSEL -------------------- */
    val carouselImages = listOf(
        R.drawable.logo,
        R.drawable.tecno,
        R.drawable.tecno3
    )

    private val _currentImageIndex = MutableStateFlow(0)
    val currentImageIndex: StateFlow<Int> = _currentImageIndex.asStateFlow()

    fun nextImage() {
        _currentImageIndex.value = (_currentImageIndex.value + 1) % carouselImages.size
    }

    fun previousImage() {
        _currentImageIndex.value = if (_currentImageIndex.value - 1 < 0)
            carouselImages.size - 1
        else
            _currentImageIndex.value - 1
    }

    fun goToImage(index: Int) {
        if (index in carouselImages.indices) {
            _currentImageIndex.value = index
        }
    }

    /* -------------------- CLIMA (API EXTERNA) -------------------- */
    private val _weather = MutableStateFlow(WeatherState())
    val weather: StateFlow<WeatherState> = _weather.asStateFlow()

    init {
        fetchWeather()
    }

    fun fetchWeather() {
        viewModelScope.launch {
            // Simulación de llamada a API (Retrofit)
            delay(1000)
            _weather.value = WeatherState(
                temperature = "22°C",
                description = "Despejado en Santiago",
                icon = "☀️"
            )
        }
    }

    /* -------------------- ROL / PERMISOS -------------------- */
    private val _userRole = MutableStateFlow<Role?>(null)
    val userRole: StateFlow<Role?> = _userRole.asStateFlow()

    private val _canViewAllOrders = MutableStateFlow(false)
    val canViewAllOrders: StateFlow<Boolean> = _canViewAllOrders.asStateFlow()

    private val _canViewMyOrders = MutableStateFlow(false)
    val canViewMyOrders: StateFlow<Boolean> = _canViewMyOrders.asStateFlow()

    private val _canManageProducts = MutableStateFlow(false)
    val canManageProducts: StateFlow<Boolean> = _canManageProducts.asStateFlow()

    private val _canUseCamera = MutableStateFlow(false)
    val canUseCamera: StateFlow<Boolean> = _canUseCamera.asStateFlow()

    fun updatePermissions(role: Role) {
        _userRole.value = role
        when (role) {
            Role.ADMIN -> {
                _canViewAllOrders.value = true
                _canViewMyOrders.value = true
                _canManageProducts.value = true
                _canUseCamera.value = true
            }
            Role.VENDEDOR -> {
                _canViewAllOrders.value = true
                _canViewMyOrders.value = false
                _canManageProducts.value = true
                _canUseCamera.value = false
            }
            Role.REPARTIDOR -> {
                _canViewAllOrders.value = true
                _canViewMyOrders.value = false
                _canManageProducts.value = false
                _canUseCamera.value = true
            }
            Role.CLIENTE -> {
                _canViewAllOrders.value = false
                _canViewMyOrders.value = true
                _canManageProducts.value = false
                _canUseCamera.value = true
            }
        }
    }
}
