package com.example.tiendapp.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.tiendapp.data.local.AppDatabase
import com.example.tiendapp.data.repository.ProductRepository
import com.example.tiendapp.model.domain.Product
import com.example.tiendapp.utils.JsonUtils
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class ProductViewModel(application: Application) : AndroidViewModel(application) {

    private val repository =
        ProductRepository(AppDatabase.getDatabase(application).productDao())

    val allProducts: StateFlow<List<Product>> =
        repository.allProducts
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(5_000),
                initialValue = emptyList()
            )

    init {
        forceRefreshFromAssets()
    }

    private fun forceRefreshFromAssets() {
        viewModelScope.launch {
            // Borramos todo para asegurar que cargue las nuevas imágenes del JSON
            repository.clearAllProducts()
            val assetsProducts = JsonUtils.loadProductsFromAssets(getApplication())
            if (assetsProducts.isNotEmpty()) {
                repository.insertAll(assetsProducts)
            }
        }
    }

    private val _selectedProduct = MutableStateFlow<Product?>(null)
    val selectedProduct: StateFlow<Product?> = _selectedProduct.asStateFlow()

    fun loadProductById(productId: Long) {
        viewModelScope.launch {
            _selectedProduct.value = repository.getProductById(productId)
        }
    }

    fun clearSelectedProduct() {
        _selectedProduct.value = null
    }

    fun insertProduct(product: Product) {
        viewModelScope.launch {
            repository.insertProduct(product)
        }
    }

    fun updateProduct(product: Product) {
        viewModelScope.launch {
            repository.updateProduct(product)
        }
    }

    fun deleteProduct(product: Product) {
        viewModelScope.launch {
            repository.deleteProduct(product)
        }
    }
}
