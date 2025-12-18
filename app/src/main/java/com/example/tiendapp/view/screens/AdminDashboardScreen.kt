package com.example.tiendapp.view.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.tiendapp.model.domain.Role
import com.example.tiendapp.auth.PermissionManager

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AdminDashboardScreen(
    role: Role,
    onUsersClick: () -> Unit,
    onProductsClick: () -> Unit,
    onOrdersClick: () -> Unit,
    onAddProductClick: () -> Unit
) {
    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text("Panel de Gestión") }
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .padding(20.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {

            // SOLO ADMIN
            if (PermissionManager.canViewUsers(role)) {
                Button(
                    onClick = onUsersClick,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("Gestionar Usuarios")
                }
            }

            // ADMIN / VENDEDOR
            if (PermissionManager.canViewProducts(role)) {
                Button(
                    onClick = onProductsClick,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("Gestionar Productos")
                }
            }

            // ADMIN / VENDEDOR
            if (PermissionManager.canViewAllOrders(role)) {
                Button(
                    onClick = onOrdersClick,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("Ver Pedidos")
                }
            }

            // ADMIN / VENDEDOR
            if (PermissionManager.canCreateProduct(role)) {
                Button(
                    onClick = onAddProductClick,
                    modifier = Modifier.fillMaxWidth(),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = MaterialTheme.colorScheme.primary
                    )
                ) {
                    Text("Agregar Producto")
                }
            }
        }
    }
}
