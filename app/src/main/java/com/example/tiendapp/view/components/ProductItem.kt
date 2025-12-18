package com.example.tiendapp.view.components

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import com.example.tiendapp.model.domain.Product

@Composable
fun ProductItem(product: Product) {
    Text(text = "${product.nombre} - $${product.precio}")
}
