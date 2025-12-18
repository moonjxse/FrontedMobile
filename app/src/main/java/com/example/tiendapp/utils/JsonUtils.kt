package com.example.tiendapp.utils

import android.content.Context
import com.example.tiendapp.model.domain.Product
import com.example.tiendapp.model.domain.User
import com.example.tiendapp.model.domain.Role
import com.example.tiendapp.model.domain.Region
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

object JsonUtils {
    
    fun loadProductsFromAssets(context: Context): List<Product> {
        return try {
            val jsonString = context.assets.open("products.json")
                .bufferedReader().use { it.readText() }
            val listType = object : TypeToken<List<Product>>() {}.type
            Gson().fromJson(jsonString, listType)
        } catch (e: Exception) {
            emptyList()
        }
    }

    fun loadUsersFromAssets(context: Context): List<User> {
        return try {
            val jsonString = context.assets.open("users.json")
                .bufferedReader().use { it.readText() }
            
            data class UserJson(
                val id: Long, 
                val name: String, 
                val email: String, 
                val password: String, 
                val role: String?
            )
            
            val listType = object : TypeToken<List<UserJson>>() {}.type
            val usersJson: List<UserJson> = Gson().fromJson(jsonString, listType)
            
            usersJson.map {
                User(
                    id = it.id,
                    name = it.name,
                    email = it.email,
                    password = it.password,
                    direccion = "Dirección por defecto",
                    telefono = 0,
                    role = try { Role.valueOf(it.role ?: "CLIENTE") } catch(e: Exception) { Role.CLIENTE }
                )
            }
        } catch (e: Exception) {
            emptyList()
        }
    }

    fun loadRegionsFromAssets(context: Context): List<Region> {
        return try {
            val jsonString = context.assets.open("regiones.json")
                .bufferedReader().use { it.readText() }
            val listType = object : TypeToken<List<Region>>() {}.type
            Gson().fromJson(jsonString, listType)
        } catch (e: Exception) {
            emptyList()
        }
    }
}
