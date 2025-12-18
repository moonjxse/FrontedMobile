package com.example.tiendapp.viewmodel

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.tiendapp.data.repository.UserRepository

class UserViewModelFactory(
    private val userRepository: UserRepository,
    private val application: Application
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(UserViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return UserViewModel(userRepository, application) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
