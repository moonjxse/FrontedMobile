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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.tiendapp.model.domain.Order
import com.example.tiendapp.model.domain.Role
import com.example.tiendapp.viewmodel.OrderViewModel
import kotlinx.coroutines.delay

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun OrderListScreen(
    orderViewModel: OrderViewModel,
    userId: Long?,
    userRole: Role,
    onBack: () -> Unit
) {
    val orders by orderViewModel.orders.collectAsState()
    val message by orderViewModel.message.collectAsState()

    LaunchedEffect(userRole, userId) {
        orderViewModel.loadOrdersForRole(
            role = userRole,
            userId = userId
        )
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = when (userRole) {
                            Role.ADMIN -> "Pedidos (Administrador)"
                            Role.VENDEDOR -> "Pedidos (Vendedor)"
                            Role.REPARTIDOR -> "Pedidos a entregar"
                            Role.CLIENTE -> "Mis pedidos"
                        },
                        fontWeight = FontWeight.Bold
                    )
                },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Volver"
                        )
                    }
                }
            )
        }
    ) { padding ->

        when {
            orders.isEmpty() -> {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(padding),
                    contentAlignment = Alignment.Center
                ) {
                    Text("No hay pedidos disponibles")
                }
            }

            else -> {
                LazyColumn(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(padding)
                        .padding(16.dp)
                ) {
                    items(
                        items = orders,
                        key = { it.id }
                    ) { order ->
                        OrderItem(
                            order = order,
                            role = userRole,
                            onDeliver = {
                                orderViewModel.markAsDelivered(order)
                            },
                            onDelete = {
                                orderViewModel.deleteOrder(order)
                            }
                        )
                    }
                }
            }
        }

        message?.let {
            LaunchedEffect(it) {
                delay(2000)
                orderViewModel.clearMessage()
            }
        }
    }
}

@Composable
fun OrderItem(
    order: Order,
    role: Role,
    onDeliver: () -> Unit,
    onDelete: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        elevation = CardDefaults.cardElevation(4.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {

            Text("Pedido #${order.id}", fontWeight = FontWeight.Bold)
            Spacer(modifier = Modifier.height(6.dp))

            Text("Producto ID: ${order.productId}")
            Text("Cantidad: ${order.cantidad}")
            Text("Estado: ${order.estado}")
            Text("Fecha: ${order.fecha}")

            if (role == Role.REPARTIDOR && order.estado != "ENTREGADO") {
                Spacer(modifier = Modifier.height(12.dp))
                Button(
                    onClick = onDeliver,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("Marcar como entregado")
                }
            }

            if (role == Role.ADMIN) {
                Spacer(modifier = Modifier.height(8.dp))
                OutlinedButton(
                    onClick = onDelete,
                    modifier = Modifier.fillMaxWidth(),
                    colors = ButtonDefaults.outlinedButtonColors(
                        contentColor = MaterialTheme.colorScheme.error
                    )
                ) {
                    Text("Eliminar pedido")
                }
            }
        }
    }
}
