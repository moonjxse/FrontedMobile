package com.example.tiendapp.view.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.tiendapp.model.domain.Role
import com.example.tiendapp.auth.PermissionManager
import com.example.tiendapp.viewmodel.OrderViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DeliveryScreen(
    role: Role,
    orderViewModel: OrderViewModel,
    onNavigateBack: () -> Unit
) {
    // Cargar pedidos al iniciar
    LaunchedEffect(Unit) {
        orderViewModel.loadAllOrders()
    }

    if (!PermissionManager.canManageDeliveries(role)) {
        Scaffold(
            topBar = {
                CenterAlignedTopAppBar(
                    title = { Text("Pedidos a Entregar") },
                    navigationIcon = {
                        IconButton(onClick = onNavigateBack) {
                            Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Volver")
                        }
                    }
                )
            }
        ) { padding ->
            Box(
                modifier = Modifier
                    .padding(padding)
                    .fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "No tienes permisos para acceder a esta sección",
                    color = MaterialTheme.colorScheme.error
                )
            }
        }
        return
    }

    val orders by orderViewModel.deliveryOrders.collectAsState()

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text("Pedidos a Entregar") },
                navigationIcon = {
                    IconButton(onClick = onNavigateBack) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Volver")
                    }
                }
            )
        }
    ) { padding ->
        if (orders.isEmpty()) {
            Box(Modifier.fillMaxSize().padding(padding), contentAlignment = Alignment.Center) {
                Text("No hay pedidos pendientes de entrega")
            }
        } else {
            LazyColumn(
                modifier = Modifier
                    .padding(padding)
                    .padding(16.dp)
            ) {
                items(orders, key = { it.id }) { order ->
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 8.dp),
                        elevation = CardDefaults.cardElevation(4.dp)
                    ) {
                        Column(Modifier.padding(16.dp)) {
                            Text("Pedido #${order.id}", style = MaterialTheme.typography.titleMedium)
                            Text("Estado: ${order.estado}")
                            Text("Fecha: ${order.fecha}")

                            if (order.estado != "ENTREGADO") {
                                Spacer(Modifier.height(8.dp))
                                Button(
                                    onClick = {
                                        orderViewModel.markAsDeliveredById(order.id)
                                    },
                                    modifier = Modifier.fillMaxWidth(),
                                    enabled = PermissionManager.canUpdateOrderStatus(role)
                                ) {
                                    Text("Marcar como entregado")
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}
