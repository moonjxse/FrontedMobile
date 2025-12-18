package com.example.tiendapp.view.screens

import android.widget.Toast
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.text.font.FontWeight
import coil.compose.AsyncImage
import com.example.tiendapp.R
import com.example.tiendapp.model.domain.Product
import com.example.tiendapp.model.domain.Role
import com.example.tiendapp.model.domain.User
import com.example.tiendapp.viewmodel.ProductViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProductListScreen(
    productViewModel: ProductViewModel,
    currentUser: User?,
    onAddProduct: () -> Unit,
    onEditProduct: (Long) -> Unit,
    onViewProduct: (Long) -> Unit,
    onOrderProduct: (Product) -> Unit
) {
    val context = LocalContext.current
    val products by productViewModel.allProducts.collectAsState()

    val role = currentUser?.role
    val canCreateProduct = role == Role.ADMIN
    val canEditProduct = role == Role.ADMIN
    val canOrderProduct = role == Role.CLIENTE

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(title = { Text("ToxcitryPC - Productos", fontWeight = FontWeight.Bold) })
        },
        floatingActionButton = {
            if (canCreateProduct) {
                FloatingActionButton(onClick = onAddProduct) {
                    Icon(Icons.Default.Add, contentDescription = "Agregar")
                }
            }
        }
    ) { padding ->
        if (products.isEmpty()) {
            Box(modifier = Modifier.padding(padding).fillMaxSize(), contentAlignment = Alignment.Center) {
                CircularProgressIndicator()
            }
        } else {
            LazyColumn(modifier = Modifier.padding(padding).fillMaxSize().padding(horizontal = 16.dp)) {
                items(products, key = { it.id }) { product ->
                    ProductItem(
                        product = product,
                        canEdit = canEditProduct,
                        canOrder = canOrderProduct,
                        onClick = { onViewProduct(product.id) },
                        onEdit = { onEditProduct(product.id) },
                        onOrder = {
                            if (product.stock <= 0) {
                                Toast.makeText(context, "Sin stock", Toast.LENGTH_SHORT).show()
                            } else {
                                onOrderProduct(product)
                            }
                        }
                    )
                    Spacer(modifier = Modifier.height(12.dp))
                }
            }
        }
    }
}

@Composable
fun ProductItem(
    product: Product,
    canEdit: Boolean,
    canOrder: Boolean,
    onClick: () -> Unit,
    onEdit: () -> Unit,
    onOrder: () -> Unit
) {
    val context = LocalContext.current
    
    // Mapeo manual y seguro de imágenes de drawable
    val imageRes = remember(product.imagenUrl) {
        when(product.imagenUrl) {
            "tecladomecanicorgb" -> R.drawable.tecladomecanicorgb
            "camara" -> R.drawable.camara
            "relojdeportivo" -> R.drawable.relojdeportivo
            "audifonos" -> R.drawable.audifonos
            else -> context.resources.getIdentifier(product.imagenUrl, "drawable", context.packageName).let {
                if (it == 0) product.imagenUrl else it
            }
        }
    }

    Card(
        modifier = Modifier.fillMaxWidth().clickable { onClick() },
        elevation = CardDefaults.cardElevation(4.dp)
    ) {
        Row(modifier = Modifier.padding(12.dp), verticalAlignment = Alignment.CenterVertically) {
            Surface(
                modifier = Modifier.size(90.dp),
                shape = MaterialTheme.shapes.medium,
                color = MaterialTheme.colorScheme.surfaceVariant
            ) {
                AsyncImage(
                    model = imageRes,
                    contentDescription = product.nombre,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier.fillMaxSize()
                )
            }

            Spacer(modifier = Modifier.width(16.dp))

            Column(modifier = Modifier.weight(1f)) {
                Text(product.nombre, style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold)
                Text("Precio: $${product.precio}", color = MaterialTheme.colorScheme.primary, fontWeight = FontWeight.Bold)
                Text("Stock: ${product.stock}", style = MaterialTheme.typography.bodySmall)
            }

            if (canOrder || canEdit) {
                Column(horizontalAlignment = Alignment.End) {
                    if (canOrder) {
                        Button(onClick = onOrder, modifier = Modifier.height(36.dp)) {
                            Text("Comprar", fontSize = 11.sp)
                        }
                    }
                    if (canEdit) {
                        TextButton(onClick = onEdit) {
                            Text("Editar", fontSize = 11.sp)
                        }
                    }
                }
            }
        }
    }
}
