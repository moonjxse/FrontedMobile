package com.example.tiendapp.utils

object ValidationUtils {
    fun getNombreErrorMessage(nombre: String): String? {
        if (nombre.isBlank()) {
            return "El nombre no puede estar vacío"
        }
        if (nombre.length < 3) {
            return "El nombre debe tener al menos 3 caracteres"
        }
        return null
    }

    fun getTelefonoErrorMessage(telefono: String): String? {
        if (!telefono.matches(Regex("^\\+569\\d{8}$"))) {
            return "Formato de teléfono inválido (ej: +56912345678)"
        }
        return null
    }

    fun getEmailErrorMessage(email: String): String? {
        if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            return "Formato de correo inválido"
        }
        return null
    }

    fun getRegionErrorMessage(region: String): String? {
        if (region.isBlank()) {
            return "Debe seleccionar una región"
        }
        return null
    }

    fun getMensajeErrorMessage(mensaje: String): String? {
        if (mensaje.length > 200) {
            return "El mensaje no puede exceder los 200 caracteres"
        }
        return null
    }
    fun isValidEmail(email: String): Boolean {
        return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()
    }
}
