package com.example.tiendapp.view.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.tiendapp.auth.PermissionManager
import com.example.tiendapp.model.domain.Product
import com.example.tiendapp.model.domain.User
import com.example.tiendapp.viewmodel.OrderViewModel
import com.example.tiendapp.viewmodel.ProductViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProductDetailScreen(
    productId: Long,
    productViewModel: ProductViewModel,
    orderViewModel: OrderViewModel,
    currentUser: User?,
    onBack: () -> Unit
) {
    val context = LocalContext.current
    val product by productViewModel.selectedProduct.collectAsState()

    LaunchedEffect(productId) {
        productViewModel.loadProductById(productId)
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Detalle del Producto") },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Volver")
                    }
                }
            )
        }
    ) { padding ->
        product?.let { p ->
            // Lógica para resolver si es Drawable local o URL
            val imageModel = remember(p.imagenUrl) {
                val resourceId = context.resources.getIdentifier(
                    p.imagenUrl,
                    "drawable",
                    context.packageName
                )
                if (resourceId != 0) resourceId else p.imagenUrl
            }

            Column(
                modifier = Modifier
                    .padding(padding)
                    .padding(16.dp)
                    .verticalScroll(rememberScrollState())
            ) {
                AsyncImage(
                    model = imageModel,
                    contentDescription = p.nombre,
                    modifier = Modifier.fillMaxWidth().height(250.dp),
                    contentScale = ContentScale.Fit
                )

                Spacer(Modifier.height(16.dp))
                Text(p.nombre, style = MaterialTheme.typography.headlineSmall, fontWeight = FontWeight.Bold)
                Text(p.descripcion, style = MaterialTheme.typography.bodyMedium)
                
                Spacer(Modifier.height(12.dp))
                Text("Precio: $${p.precio}", style = MaterialTheme.typography.titleLarge, color = MaterialTheme.colorScheme.primary)
                Text("Stock disponible: ${p.stock}")

                Spacer(Modifier.height(24.dp))

                /* Botón de compra con permisos */
                if (currentUser != null && PermissionManager.canCreateOrder(currentUser.role) && p.stock > 0) {
                    Button(
                        onClick = { orderViewModel.createOrder(p.id, currentUser.id) },
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text("Comprar")
                    }
                } else if (p.stock <= 0) {
                    Text("Producto sin stock", color = MaterialTheme.colorScheme.error)
                }
            }
        }
    }
}
