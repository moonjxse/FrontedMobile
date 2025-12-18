package com.example.tiendapp.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.tiendapp.data.local.AppDatabase
import com.example.tiendapp.data.repository.ContactRepository
import com.example.tiendapp.model.domain.Contact
import com.example.tiendapp.model.domain.Region
import com.example.tiendapp.utils.ValidationUtils
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class ContactViewModel(application: Application) : AndroidViewModel(application) {

    private val repository: ContactRepository

    init {
        val database = AppDatabase.getDatabase(application)
        repository = ContactRepository(database.contactDao())
    }

    /* -------------------- STATE -------------------- */

    private val _nombre = MutableStateFlow("")
    val nombre: StateFlow<String> = _nombre.asStateFlow()

    private val _correo = MutableStateFlow("")
    val correo: StateFlow<String> = _correo.asStateFlow()

    private val _telefono = MutableStateFlow("")
    val telefono: StateFlow<String> = _telefono.asStateFlow()

    private val _regionSeleccionada = MutableStateFlow<Region?>(null)
    val regionSeleccionada: StateFlow<Region?> = _regionSeleccionada.asStateFlow()

    private val _mensaje = MutableStateFlow("")
    val mensaje: StateFlow<String> = _mensaje.asStateFlow()

    // Lista de regiones fija para el ejemplo o cargar de algún lugar
    private val _regiones = MutableStateFlow(
        listOf(
            Region(1, "Metropolitana"),
            Region(2, "Valparaíso"),
            Region(3, "Biobío"),
            Region(4, "Maule"),
            Region(5, "Araucanía")
        )
    )
    val regiones: StateFlow<List<Region>> = _regiones.asStateFlow()

    private val _nombreError = MutableStateFlow<String?>(null)
    val nombreError: StateFlow<String?> = _nombreError.asStateFlow()

    private val _correoError = MutableStateFlow<String?>(null)
    val correoError: StateFlow<String?> = _correoError.asStateFlow()

    private val _telefonoError = MutableStateFlow<String?>(null)
    val telefonoError: StateFlow<String?> = _telefonoError.asStateFlow()

    private val _regionError = MutableStateFlow<String?>(null)
    val regionError: StateFlow<String?> = _regionError.asStateFlow()

    private val _mensajeError = MutableStateFlow<String?>(null)
    val mensajeError: StateFlow<String?> = _mensajeError.asStateFlow()

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()

    private val _guardadoExitoso = MutableStateFlow(false)
    val guardadoExitoso: StateFlow<Boolean> = _guardadoExitoso.asStateFlow()


    fun onNombreChange(value: String) {
        _nombre.value = value
        _nombreError.value = ValidationUtils.getNombreErrorMessage(value)
    }

    fun onCorreoChange(value: String) {
        _correo.value = value
        _correoError.value = ValidationUtils.getEmailErrorMessage(value)
    }

    fun onTelefonoChange(value: String) {
        _telefono.value = value
        _telefonoError.value = ValidationUtils.getTelefonoErrorMessage(value)
    }

    fun onRegionChange(region: Region) {
        _regionSeleccionada.value = region
        _regionError.value = ValidationUtils.getRegionErrorMessage(region.nombre)
    }

    fun onMensajeChange(value: String) {
        if (value.length <= 200) {
            _mensaje.value = value
            _mensajeError.value = ValidationUtils.getMensajeErrorMessage(value)
        }
    }

    fun getMensajeCounter(): String = "${_mensaje.value.length}/200"


    private fun validateForm(): Boolean {
        _nombreError.value = ValidationUtils.getNombreErrorMessage(_nombre.value)
        _correoError.value = ValidationUtils.getEmailErrorMessage(_correo.value)
        _telefonoError.value = ValidationUtils.getTelefonoErrorMessage(_telefono.value)
        _regionError.value = ValidationUtils.getRegionErrorMessage(_regionSeleccionada.value?.nombre ?: "")
        _mensajeError.value = ValidationUtils.getMensajeErrorMessage(_mensaje.value)

        return listOf(
            _nombreError.value,
            _correoError.value,
            _telefonoError.value,
            _regionError.value,
            _mensajeError.value
        ).all { it == null }
    }

    fun saveContact() {
        if (!validateForm()) return

        // Extraer números si el formato es +569...
        val soloNumeros = _telefono.value.filter { it.isDigit() }
        val telefonoInt = soloNumeros.toIntOrNull() ?: 0

        _isLoading.value = true

        viewModelScope.launch {
            try {
                val contact = Contact(
                    id = 0L,
                    nombre = _nombre.value.trim(),
                    correo = _correo.value.trim(),
                    telefono = telefonoInt,
                    region = _regionSeleccionada.value?.nombre ?: "",
                    mensaje = _mensaje.value.trim()
                )

                repository.insertContact(contact)
                _guardadoExitoso.value = true
                clearForm()

            } finally {
                _isLoading.value = false
            }
        }
    }

    private fun clearForm() {
        _nombre.value = ""
        _correo.value = ""
        _telefono.value = ""
        _regionSeleccionada.value = null
        _mensaje.value = ""

        _nombreError.value = null
        _correoError.value = null
        _telefonoError.value = null
        _regionError.value = null
        _mensajeError.value = null
    }

    fun resetGuardadoExitoso() {
        _guardadoExitoso.value = false
    }
}
